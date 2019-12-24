package Protocols;

import java.io.Serializable;
import FileHandling.FileTransmitor;

public class RequestWithFile extends Request implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FileTransmitor ftr;
	
	public FileTransmitor getFtr() {
		return ftr;
	}

	public void setFtr(FileTransmitor ftr) {
		this.ftr = ftr;
	}

	public RequestWithFile(RequestType requestType, Object data, FileTransmitor ftr)
	{
		super(requestType, data);
		this.ftr = ftr;
	}
}
