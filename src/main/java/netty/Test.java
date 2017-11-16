package netty;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class Test {

	
	public static void main(String[] args) throws UnknownHostException{
		String columnValue="2017-02-23";
				
		boolean flag=columnValue.matches("[0-9]{4}-[0-9]{2}");
		System.out.println(flag);
		
		
		
//	    Runnable  task=new Runnable(){
//
//			public void run() {
//				
//				System.out.println("task");
//				System.out.println(System.currentTimeMillis()/1000);
//			}
//	
//	};
//	ScheduledFuture sf=Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(task,0,60, TimeUnit.SECONDS);
}
}