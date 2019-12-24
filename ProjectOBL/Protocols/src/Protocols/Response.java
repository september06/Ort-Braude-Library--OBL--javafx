package Protocols;

import java.io.Serializable;

public class Response /*extends Request*/ implements Serializable
{
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	private boolean succeeded;
	private String message;
	private RequestType requestType;
	private Object data;
	
	public String getMessage() {
		return message;
	}

	public boolean isSucceeded() {
		return succeeded;
	}

	public Response(RequestType requestType, boolean succeeded, Object data, String message) {
		this.data = data;
		this.requestType = requestType;
		this.succeeded = succeeded;
		this.message = new String(message);
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
		return "Response@"+this.requestType;
	}
}