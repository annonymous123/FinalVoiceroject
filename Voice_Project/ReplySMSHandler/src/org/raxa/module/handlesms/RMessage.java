package org.raxa.module.handlesms;

public enum RMessage {
	KEYWORDNOTFOUND("You have entered an invalid Keyword.Use GET MENU to fetch Menu"),
	NUMBERNOTRECOGNISED("Sorry your Phone number is not recognised by the system.You may want to register yourself first"),
	NOTHINGTOREPLY("Sorry The system has no answer to your query.Start Again or try later"),
	INVALIDSESSION("Sorry your session has expired or not created yet.Use GET MENU to fetch Menu"),
	INVALIDOPTION("You have entered an invalid Input.Try Again"),
	DIFFERNETSESSIONID("You have entered a sesion ID not belong to you.Please check the previous message again and retry");
	
	
	
	private String message;
	RMessage(String s){
		message=s;
	}
	
	public String getMessage(){
		return message;
	}
	
}
