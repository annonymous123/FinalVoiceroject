package org.raxa.service.updater;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

public class Updater{

	private Logger logger = Logger.getLogger(Updater.class);
	
	 public void start(){
		
		int threadPool=2;
		int pollingHours=4;
	
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(threadPool);
		Runnable alertSetter = new AlertSetter(new Date());
		try{
			executor.scheduleWithFixedDelay(alertSetter,0,pollingHours,TimeUnit.HOURS);
		}
		catch(Exception e){
		   logger.error("IMPORTANT:Unable to execute Scheduler at time:"+new Date());
		   
		 }
		
	} 
	 
	 
}
