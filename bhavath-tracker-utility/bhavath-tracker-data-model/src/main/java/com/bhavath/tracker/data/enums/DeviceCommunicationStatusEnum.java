package com.bhavath.tracker.data.enums;

public enum DeviceCommunicationStatusEnum {
	
	GOOD("40.00"), 
	CRITICAL("40.00"),
	ON("1"),
	OFF("0"),
	TAMPERON("O"),
	TAMPEROFF("C"),
	RUNNINGMODE("RUNNING MODE"),
	SLEEPMODE("SLEEP MODE"),
	COMMUNICATING("Communicating"),
	NOTCOMMUNICATING("Not Communicating");
	

	public String type;

	DeviceCommunicationStatusEnum(String dataType) {
		this.type = dataType;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return this.getType();
	}
}
