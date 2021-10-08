package com.bhavath.tracker.enums;

public enum LMPacketAttributes 
{
	  	START_OF_PACKET(1, "ASCII"),
	  	SEPARATOR(1,"ASCII"),
	  	HEADER(3,"ASCII"),
	  
		VENDOR_ID(4,"ASCII"),
		FIRMWARE_VERSION(6,"ASCII"),
		PACKET_TYPE(2,"ASCII"),
		PACKET_STATUS(1,"ASCII"),
		IMEI(15,"ASCII"),
		VEHICLE_REG_NO(10,"ASCII"),
		GPS_FIX(1,"ASCII"),
		DATE(8,"ASCII"),
		TIME(6,"ASCII"),
		LATITUDE(12,"ASCII"),
		LATITUDE_DIRECTION(1,"ASCII"),
		LONGITUDE(12,"ASCII"),
		LONGITUDE_DIRECTION(1,"ASCII"),
		SPEED(6,"ASCII"),
		HEADING(10,"ASCII"),
		NO_OF_SATELLITES(1,"ASCII"),
		ALTITUDE(12,"ASCII"),
		PDOP(1,"ASCII"),
		HDOP(1,"ASCII"),
		NETWORK_OPERATOR_NAME(20,"ASCII"),
		IGNITION(1,"ASCII"),
		MAIN_POWER_STATUS(1,"ASCII"),
		MAIN_INPUT_VOLTAGE(4,"ASCII"),
		INTERNAL_BATTERY_VOLTAGE(4,"ASCII"),
		EMERGENCY_STATUS(1,"ASCII"),
		TAMPER_ALERT(1,"ASCII"),
		GSM_SIGNAL_STRENGTH(1,"ASCII"),
		MCC(1,"ASCII"),
		MNC(1,"ASCII"),
		LAC(1,"ASCII"),
		CELL_ID(4,"ASCII"),
		NMR(60,"ASCII"),
		DIGITAL_INPUT_STATUS(4,"ASCII"),
		DIGITAL_OUTPUT_STATUS(2,"ASCII"),
		FRAME_NUMBER(6,"ASCII"),
		CHECKSUM(4,"ASCII"),
		END_OF_PACKET(1,"ASCII");
	
		  public Integer noOfChars;
		  public String dataType;
	
		  LMPacketAttributes(Integer noOfChars, String dataType)
		  {
		    this.noOfChars = noOfChars;
		    this.dataType = dataType;
		  }
		  
		  public Integer getNoOfChars() {
				return noOfChars;
		  }
			
		  public String getDataType() {
				return dataType;
		  }
	
		  @Override
		  public String toString() {
				return this.getNoOfChars() + this.getDataType();
		  }
}
