package com.bhavath.tracker.enums;

public enum EPBPacketAttributes 
{
		  	START_OF_PACKET(1, "ASCII"),
		  	SEPARATOR(1,"ASCII"),
		  	HEADER(3,"ASCII"),
		  	VENDOR_ID(4,"ASCII"),
			FIRMWARE_VERSION(6,"ASCII"),
			IMEI(15,"ASCII"),
			BATTERY_PERCENTAGE(4,"ASCII"),
			LOW_BATTERY_THRESHOLD_VALUE(4,"ASCII"),
			MEMORY_PERCENTAGE(4,"ASCII"),
			DATA_UPDATE_RATE_WHEN_IGNITION_ON(6,"ASCII"),
			DATA_UPDATE_RATE_WHEN_IGNITION_OFF(6,"ASCII"),
			DIGITAL_IO_STATUS(8,"ASCII"),
			ANALOG_IO_STATUS(2,"ASCII"),
			END_OF_PACKET(1,"ASCII");
	
		  public Integer noOfBytes;
		  public String dataType;
	
		  EPBPacketAttributes(Integer noOfBytes, String dataType)
		  {
		    this.noOfBytes = noOfBytes;
		    this.dataType = dataType;
		  }
		  
		  public Integer getNoOfBytes() {
				return noOfBytes*2;
		  }
			
		  public String getDataType() {
				return dataType;
		  }
	
		  @Override
		  public String toString() {
				return this.getNoOfBytes() + this.getDataType();
		  }
}
