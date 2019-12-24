package Protocols;

import java.io.Serializable;

public class Request implements Serializable 
{
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	private RequestType requestType;
	private Object data;
	
	public Request(RequestType requestType, Object data) {
		this.data = data;
		this.requestType = requestType;
	}
	
	public RequestType getRequestType() {
		return requestType;
	}
	
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "Request@"+this.requestType;
	}
}
