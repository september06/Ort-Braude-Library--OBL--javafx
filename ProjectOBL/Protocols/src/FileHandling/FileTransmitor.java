package FileHandling;

import java.io.Serializable;

public class FileTransmitor implements Serializable {
	
	private String Description=null;
	private String fileName=null;	
	private int size=0;
	public  byte[] mybytearray;
	private String ID;
	
	
	public void initArray(int size)
	{
		mybytearray = new byte [size];	
	}
	
	public FileTransmitor(String fileName, String ID) {
		this.fileName = fileName;
		this.ID = ID;
	}
	
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getMybytearray() {
		return mybytearray;
	}
	
	public byte getMybytearray(int i) {
		return mybytearray[i];
	}

	public void setMybytearray(byte[] mybytearray) {
		
		for(int i=0;i<mybytearray.length;i++)
		this.mybytearray[i] = mybytearray[i];
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getID() {
		return this.ID;
	}	
}