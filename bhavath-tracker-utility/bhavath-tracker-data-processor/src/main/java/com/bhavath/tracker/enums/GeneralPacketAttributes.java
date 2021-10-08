package com.bhavath.tracker.enums;

public enum GeneralPacketAttributes 
{
	  START_OF_PACKET(1, "ASCII"),
	  SEPARATOR(1,"ASCII"),
	  HEADER(3,"ASCII");
	
	  public Integer noOfChars;
	  public String dataType;
	 

	  GeneralPacketAttributes(Integer noOfChars, String dataType)
	  {
	    this.noOfChars = noOfChars;
	    this.dataType = dataType;
	  }
	  
	  public String getDataType() {
			return dataType;
	  }
	  
	  public Integer getNoOfChars() {
			return noOfChars;
	  }

	  @Override
	  public String toString() {
			return this.getNoOfChars() + this.getDataType();
	  }
}
