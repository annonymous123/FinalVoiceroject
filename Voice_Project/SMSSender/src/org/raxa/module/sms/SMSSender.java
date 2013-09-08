package org.raxa.module.sms;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.log4j.Logger;



public class SMSSender {
	
	private boolean isAuthorised=false;
	private SMSHeaders headers;

	/**
	 * 
	 * @param message
	 * @param pnumber
	 * @param senderID
	 * @param preferLanguage
	 * @return
	 */
	public SMSResponse sendSMSThroughGateway(String message,String pnumber,String senderID,String preferLanguage){
		
		
		Logger logger=Logger.getLogger(this.getClass());SMSResponse smsresponse;
		if(!isAuthorised)
			return null;
		try{
			logger.info("Sending \n message:"+message+"\n Phone Number:"+pnumber);
			String result=HTTPRequest.getRequestPost(getHeadersAndSetBaseUrl(message,pnumber,senderID));
			if(result==null)
				throw new Exception();
			smsresponse=parseResult(result);
			logger.debug(smsresponse.toString());
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.info("Some Error occured while sending SMS to:"+pnumber+"from senderId:"+senderID);
			smsresponse=null;
		}
		return smsresponse;
	}
	
	private List <NameValuePair> getHeadersAndSetBaseUrl(String message,String pnumber,String senderId){
		headers=new mvayooSMSHeader2(message,pnumber,senderId);
		
		HTTPRequest.setURLBase(headers.baseURL);
		
		return headers.getHeader();
	}
	
	private SMSResponse parseResult(String result){
		return headers.parseResponse(result);
	}
	/**
	 * Right now it authorises anybody.later we can keep a file or maintain a database to check for username and password.
	 * As we cannot share the gateway SMS username and password it needs a way to block random access.
	 * This login credentials will be given to hospital Authority.
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean login(String username,String password){
		//do something with username and password.
		isAuthorised=true;
		return isAuthorised;
	}
	
	
}

//http://api.mVaayoo.com/mvaayooapi/MessageCompose?user=johnstoecker@gmail.com:fleebyteup&senderID=TEST SMS&receipientno=9818139890&dcs=0&msgtxt=This is Test message&state=4 
