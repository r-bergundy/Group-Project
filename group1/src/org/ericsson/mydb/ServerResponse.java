package org.ericsson.mydb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServerResponse {
	
	private Object resultData;
	private boolean isValid;
	private String errorMessage;
	
	
	public Object getResultData() {
		return resultData;
	}
	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
