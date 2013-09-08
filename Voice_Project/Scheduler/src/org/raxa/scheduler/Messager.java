/*
 * Call SMSModule and Provide Info which will then send message to patient
 */
package org.raxa.scheduler;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.raxa.alertmessage.MessageTemplate;
import org.raxa.database.Alert;
import org.raxa.database.HibernateUtil;
import org.raxa.database.VariableSetter;



public class Messager implements Runnable,VariableSetter {

	private AlertInfo patient;
	
	public Messager(AlertInfo patient){
		this.patient=patient;
	}
	
	public void run(){
		Logger logger=Logger.getLogger(this.getClass());
		String message=getMessageContent(patient.getMsgId());
		updateAlertCount();
		logger.debug("Sending \n message:"+message+"\n Phone Number:"+patient.getPhoneNumber());
		new SMSManager(patient).sendSMS(message);
	}
	
	public String getMessageContent(int msgId){
		return new MessageTemplate().getTexttoSMS(getContentFromDB(msgId));
	}
	
	private List getContentFromDB(int msgId){
		Logger logger=getLog();
		logger.debug("Getting content for medicine Reminder haveing msgId"+msgId);
		try{
			String hql="select content from SmsMsg where smsId=:msgId order by itemNumber";
			Session session = HibernateUtil.getSessionFactory().openSession();
	    	session.beginTransaction();
	    	Query query=session.createQuery(hql);
	    	query.setInteger("msgId", msgId);
	    	List content=query.list();
	    	session.getTransaction().commit();
	    	session.close();
	    	logger.debug("Successfully retreived msg content from database with msgId"+msgId);
	    	return content;
		}
		catch(Exception ex){
			logger.info("Some error occured while fetching content from msgId:"+msgId);
			
		}
		return null;
	}
	
	private Logger getLog(){
		Logger logger = Logger.getLogger(this.getClass());
		return logger;
	}
	
	
	
	public void updateAlertCount(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		String hql="from Alert where aid=:aid and alertType=:alertType";
		Query query=session.createQuery(hql);
		query.setString("aid", patient.getAlertId());
		query.setInteger("alertType", SMS_TYPE);
		Alert alert = (Alert) query.list().get(0);
		int retryCount=alert.getretryCount()+1;
		alert.setretryCount(retryCount);
		session.update(alert);
		session.getTransaction().commit();
		session.close();
	}
}


