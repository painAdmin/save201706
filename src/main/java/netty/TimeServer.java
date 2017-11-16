package netty;

public class TimeServer {

	public static void main(String[] args){
		int port=8077;
		MultiplexerTimeServer timeServer=new MultiplexerTimeServer(port);
		new Thread(timeServer,"NIO-MultiplexerTimeServer-001").start();
		
	}
}
