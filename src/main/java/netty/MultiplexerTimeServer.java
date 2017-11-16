package netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable{

	private Selector selector;
	private ServerSocketChannel servChannel;
	private boolean stop;
	public MultiplexerTimeServer(int port){
		try{
			selector=Selector.open();
		    servChannel=ServerSocketChannel.open();
		    servChannel.configureBlocking(false);
		    servChannel.socket().bind(new InetSocketAddress(port),1024);
		    servChannel.register(selector, SelectionKey.OP_ACCEPT);
		    System.out.println("Time 服务器已经启动，端口为："+port);
		    
		}catch(IOException e){
			e.printStackTrace();	
		}
		
	}
	public void stop(){
		stop=true;
	}
	public void run() {
		while(!stop){
			try {
				selector.select(1000);
				Set<SelectionKey> selectedKeys=selector.selectedKeys();
				Iterator<SelectionKey>  iterator=selectedKeys.iterator();
				while(iterator.hasNext()){
					SelectionKey key=iterator.next();
					iterator.remove();
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
					
				
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		if(selector!=null){
			try {
				selector.close();
			} catch (Exception e) {
			  e.printStackTrace();
			}
			
		}
		
	}

	public void handleInput(SelectionKey key) throws IOException{
		if(key.isValid()){
			if(key.isAcceptable()){
				ServerSocketChannel ssc=(ServerSocketChannel)key.channel();
				SocketChannel sc=ssc.accept();
				sc.configureBlocking(false);
				sc.register(selector, SelectionKey.OP_READ);
			}
			if(key.isReadable()){
				SocketChannel sc=(SocketChannel)key.channel();
				ByteBuffer readBuffer=ByteBuffer.allocate(10*1024);
				int readBytes=sc.read(readBuffer);
				if(readBytes>0){
					readBuffer.flip();
					byte[] bytes=new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body=new String(bytes,"UTF-8");
					System.out.println(body);
					String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body) ?new Date(System.currentTimeMillis()).toString():"BAO ORDER";
					doWrite(sc,currentTime);
				}else if(readBytes<=0){
					key.cancel();
					sc.close();
				}
			}
		}
	}
	public void doWrite(SocketChannel sc,String response) throws IOException{
		if(response!=null &&response.trim().length()>0){
			byte[] bytes=response.getBytes();
			ByteBuffer writeBuffer=ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			sc.write(writeBuffer);
			
		}
		
	}
	
}





























