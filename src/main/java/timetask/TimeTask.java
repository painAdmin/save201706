package timetask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务 停止，一般是定时任务中出现异常跳出
 * @author pain
 *
 */
public class TimeTask {

	public static void main(String[] args){
		Runnable task=new Runnable(){

			public void run() {
				//     List<Map<String,Object>> list=SysGetPrivateKey.serverPortList;
				System.out.println("定时任务开始。。。。。");
				Date date=new Date();
				date.setTime(System.currentTimeMillis());
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println(sdf.format(date));
			}
			
		};
		
		ScheduledFuture sf=Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(task,0,30, TimeUnit.SECONDS);
		
	}   
	
   
}

