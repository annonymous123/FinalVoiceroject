package org.raxa.module.sms;

/**
 * http://api.mVaayoo.com/mvaayooapi/MessageCompose?
 * user=&senderID=TEST SMS&receipientno=9818139890&dcs=0&msgtxt=This is Test message&state=4
 * @author atul
 *nvps.add(new BasicNameValuePair("username", "vip"));
 */

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.List;
import java.util.ArrayList;

public class mvayooSMSHeader2 extends SMSHeaders {
	
	private final String user="foo@bar.com.com:pass";

	private String receipientno;
	
	private  String dcs;
	
	private  String msgtxt;
	
	private  String senderID;
	
	private  String state;
	
	private  List<NameValuePair> headers;
	
	
	public mvayooSMSHeader2(String message,String pnumber,String senderID){
		baseURL="http://api.mVaayoo.com/mvaayooapi/MessageCompose";
		headers=new ArrayList<NameValuePair>();
		dcs="0";
		msgtxt=message;
		state="4";
		this.senderID=senderID;
		receipientno=pnumber;
	}
	
	public List<NameValuePair> getHeader(){
		addAll();
		return headers;
	}
	
	public void addAll(){
		headers.add(new BasicNameValuePair("user",user));
		headers.add(new BasicNameValuePair("senderID",senderID));
		headers.add(new BasicNameValuePair("receipientno",receipientno));
		headers.add(new BasicNameValuePair("dcs",dcs));
		headers.add(new BasicNameValuePair("msgtxt",msgtxt));
		headers.add(new BasicNameValuePair("state",state));
		
		
	}
	
	public void addNewValuePairs(String value1,String value2){
		headers.add(new BasicNameValuePair(value1,value2));
	}
	
	/**
	 * Type of response
	 * Status=0,dlksalad
	 */
	public SMSResponse parseResponse(String result){
		SMSResponse response=new SMSResponse();
		try{
			int status;String transactionId;
			String[] split=result.trim().split(",");
			status=Integer.parseInt(split[0].split("=")[1]);
			transactionId=split[1];
			response.setStatus(String.valueOf(status));
			if(status==0)
				response.setIsSuccess(true);
			else
				response.setIsSuccess(false);
			response.setTransId(transactionId);
		}
		catch(Exception ex){
			response=null;
		}
		return response;
	}

}
