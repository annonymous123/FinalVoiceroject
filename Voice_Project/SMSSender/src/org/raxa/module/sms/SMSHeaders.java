package org.raxa.module.sms;

import java.util.List;

import org.apache.http.NameValuePair;

public abstract class  SMSHeaders {
	
		public static String baseURL;
	
		public abstract List<NameValuePair> getHeader();
		
		public abstract void addNewValuePairs(String value1,String value2); 
		
		public abstract SMSResponse parseResponse(String result); 
			
}
