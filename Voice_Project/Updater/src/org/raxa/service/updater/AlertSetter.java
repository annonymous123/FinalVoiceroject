package org.raxa.service.updater;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.raxa.database.HibernateUtil;
import org.raxa.database.VariableSetter;


public class AlertSetter implements Runnable,VariableSetter {
	
	private Date today;
	Logger logger = Logger.getLogger(this.getClass());
	
	public AlertSetter(Date today){
		this.today=today;
	}
	
	public void run(){
		if(!isSameDay()){
			resetDatabase();
			today=new Date();
		}
		logger.info(today);
	
	}
	
	public void resetDatabase(){
		  
		  ReminderUpdate r=new ReminderUpdate();
		  Session session = HibernateUtil.getSessionFactory().openSession();
		  session.beginTransaction();
		  String hql="select p.pname,p.preferLanguage,pa.pid,pa.alertType from Patient p,PAlert pa where p.pid=pa.pid";
		  Query query=session.createQuery(hql);
		  Iterator results=query.list().iterator();
		  session.getTransaction().commit();
		  session.close();
		  try{
				Object[] row=(Object[]) results.next();
				
				while(true){
					try{
						  String pname=(String) row[0];
						  String preferLanguage=(String) row[1];
						  String pid=(String) row[2];
						  int alertType=(Integer) row[3];
						  r.resetReminder(pid, pname, preferLanguage, alertType);
						  System.out.println(pid+"\n"+pname+"\n"+preferLanguage+"\n"+alertType);
						  if(results.hasNext())
							  row=(Object[]) results.next();
						  else break;
					}
					catch(Exception ex){
						ex.printStackTrace();
						logger.error("Some error while trying to reset Reminder of a patient");
					}
				}
			}
			catch(Exception ex){
				logger.error("IMPORTANT:unable to get patientList on ");
				
			}
		  
	}
	
	public boolean isSameDay(){
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(today);
		cal2.setTime(new Date());
		boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
		                  cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)&&
		                  cal1.get(Calendar.DAY_OF_MONTH)==cal2.get(Calendar.DAY_OF_MONTH);
		return sameDay;
	}
	
}
