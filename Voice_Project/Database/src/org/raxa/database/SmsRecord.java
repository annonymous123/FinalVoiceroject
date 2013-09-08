package org.raxa.database;

import java.sql.Timestamp;

public class SmsRecord {
	private long id;
	private String pnumber;
	private Timestamp inTime;
	private String msg;
	private String reply;
	private Timestamp outTime;
	private boolean isExecuted;
	private String transactionId;
	
	public SmsRecord(String pnumber,Timestamp inTime,String msg,String reply,Timestamp outTime,boolean isExecuted,String transactionId){
		
		this.pnumber=pnumber;
		this.inTime=inTime;
		this.msg=msg;
		this.reply=reply;
		this.outTime=outTime;
		this.isExecuted=isExecuted;
		this.transactionId=transactionId;
	}
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id=id;
	}
	
	public String getPatientPhoneNumber(){
		return pnumber;
	}
	
	public void setPatientPhoneNumber(String number){
		pnumber=number;
	}
	
	public String getIncomingtMessage(){
		return msg;
	}
	
	public void setIncomingMessage(String message){
		msg=message;
	}
	
	public String getReply(){
		return reply;
	}
	
	public void setReply(String s){
		reply=s;
	}
	public String geTransactionId(){
		return transactionId;
	}
	
	public void setTransactionId(String s){
		transactionId=s;
	}
	
	public boolean getIsExecuted(){
		return isExecuted;
	}
	
	public void setIsExecuted(boolean b){
		isExecuted=b;
	}
	
	public Timestamp getInTime(){
		return inTime;
	}
	
	public void setInTime(Timestamp t){
		inTime=t;
	}
	
	public Timestamp getoutTime(){
		return outTime;
	}
	
	public void setOutTime(Timestamp t){
		outTime=t;
	}

}
