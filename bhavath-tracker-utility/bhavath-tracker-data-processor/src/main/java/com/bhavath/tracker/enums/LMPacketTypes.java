package com.bhavath.tracker.enums;

public enum LMPacketTypes {

	NR("Normal"), 
	EA("Emergency Alert On"), 
	TA("Tamper Alert"), 
	HP("Health Packet"), 
	IN("Ignition On"), 
	IF("Ignition Off"),
	BD("Vehicle Battery Disconnect"), 
	BR("Vehicle Battery Reconnect"), 
	BL("Internal Battery Low"),
	OS("Over the SMS"),
	SP("Stored Packet"),
	HB("Harsh Braking"),
	HA("Harsh Acceleration"),
	RT("Rash Turning"),
	OI("Over the IP");
	
	public String type;

	LMPacketTypes(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}


}
