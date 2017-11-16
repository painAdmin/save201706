package netty;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TimeClientHandle implements Runnable {

	private String host;
	private int port;
	private Selector selector;
	private SocketChannel socketChannel;
	private  boolean stop;
	public TimeClientHandle(String host,int port){
		this.host=host==null?"127.0.0.1":host;
		this.port=port;
		try{
			selector=Selector.open();
			socketChannel=SocketChannel.open();
			socketChannel.configureBlocking(false);
			
		}catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	public void run() {
		try{
			doConnect();
		}catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}
		while(!stop){
			try {
				selector.select(1000);
				Set<SelectionKey> selectedKeys=selector.selectedKeys();
				Iterator<SelectionKey> it=selectedKeys.iterator();
				while(it.hasNext()){
					SelectionKey key=it.next();
					it.remove();
					try{
						handleInput(key);
					}catch(Exception e){
						e.printStackTrace();
						if(key!=null){
							key.cancel();
							if(key.channel()!=null){
								key.channel().close();
							}
						}
					}
					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}	
		}
		if(selector!=null){
			try{
				selector.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public void handleInput(SelectionKey key) throws IOException{
		SocketChannel socketChannel=(SocketChannel)key.channel();
		if(key.isConnectable()){
			if(socketChannel.finishConnect()){
				socketChannel.register(selector, SelectionKey.OP_READ);
				doWrite(socketChannel);
			}else{
				System.exit(1);
			}
		}
		if(key.isReadable()){
			ByteBuffer readBuffer=ByteBuffer.allocate(1024);
			int readBytes=socketChannel.read(readBuffer);
			if(readBytes>0){
				readBuffer.flip();
				byte[] bytes=new byte[readBuffer.remaining()];
				readBuffer.get(bytes);
				String body=new String(bytes,"UTF-8");
				System.out.println("Now is:"+body);
				this.stop=true;
			}else if(readBytes<0){
				key.cancel();
				key.channel().close();
			}else{
				
			}
			
		}
	}
	public void doConnect() throws IOException{
		if(socketChannel.connect(new InetSocketAddress(host,port))){
			socketChannel.register(selector, SelectionKey.OP_READ);
			doWrite(socketChannel);
		}else{
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
			
	}
	public void doWrite(SocketChannel socketChannel) throws IOException{
		byte[] req="QUERY TIME ORDER".getBytes();
		ByteBuffer byteBuffer=ByteBuffer.allocate(10*1024);
		byteBuffer.put(req);
		byteBuffer.flip();
		socketChannel.write(byteBuffer);
		if(!byteBuffer.hasRemaining()){
			System.out.println("Send Order 2 server succeed.");
		}
	}
}










