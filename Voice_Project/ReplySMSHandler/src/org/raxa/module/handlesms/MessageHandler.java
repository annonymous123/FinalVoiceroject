package org.raxa.module.handlesms;
import java.util.Date;
import org.raxa.module.sms.SMSResponse;
import org.raxa.module.sms.SMSSender;

public class MessageHandler {
	
	String message;
	
	public static int getMessageOption(String message) {
		String[] split=message.split(" ");
		int option=0;
		try{
			option=Integer.parseInt(split[1]);
		}
		catch(Exception ex){}
		return option;
	}
	
	public MessageHandler(String message){
		this.message=message;
	}
	
	public MessageHandler(){}
	
	public IMessage getMessageFormat(){
		return getMessageFormat(message);
	}
	
	public IMessage getMessageFormat(String message){
		if(message==null)
			return IMessage.NULL;
		String[] split=message.split(" ");
		if(!(split.length>=2))
			return IMessage.WRONGFORMAT;
		String firstWord=split[0].toUpperCase();
		if(firstWord.equals(IncomingSMS.hello))
			return IMessage.GET;
		if((firstWord.length()==3 && isAlpha(firstWord)))
			return IMessage.ID;
		return IMessage.UNKNOWN;
	}
	
	
	public boolean isAlpha(String firstword) {
	    return firstword.matches("[a-zA-Z]+");
	}
	
	
	//Langauge may come as null.Take care
	public void sendSMS(String message,Date inDate,String pnumber,String reply, Language language) {
		String senderId="TEST SMS"; Date outDate=new Date();
	//	IncomingSMS.out.println("Sending SMS:"+reply+"\n \nReceiver PhoneNumber:"+pnumber+" in Language:"+language.getLanguage());
		SMSSender a=new SMSSender();
		a.login("user","pass");
		SMSResponse response=a.sendSMSThroughGateway(reply, pnumber, senderId,language.getLanguage());
		updateDatabase(inDate,outDate,pnumber,message,reply.replace("\n", " "),response.getIsSuccess(),response.getTransID());  // TO BE IMPLEMENTED
	}

	private void updateDatabase(Date inDate, Date outDate, String pnumber,String message,
			String reply, boolean isSuccess, String transID) {
		DatabaseService.updateSMSResponse(pnumber,inDate,message,reply,outDate,isSuccess,transID);
		
	}

	public String getErrorMessage(RMessage message) {
		
		return message.getMessage();
	}

	public String getErrorMessage(){
		return "Sorry Invalid Input.Try Again.Type GET MENU to get menu option";
	}
	
	public String getErrorMessage(IMessage messageType) {
		
		return "Sorry Invalid Input.Try Again.Type GET MENU to get menu option";
	}
	
	
}
