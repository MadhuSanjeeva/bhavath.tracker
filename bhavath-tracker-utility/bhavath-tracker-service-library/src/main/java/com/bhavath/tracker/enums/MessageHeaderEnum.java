package com.bhavath.tracker.enums;

public enum MessageHeaderEnum {
	
	ACTIVATION("ACTV"), 
	HEALTH("HCHK"),
	SET("SET"),
	GET("GET"),
	CLR("CLR"),
	MOBILE_STATUS("No mobile numbers are mapped"),
	SIM_STATUS("No Sim Details are available"),
	ACTIVATION_RESPONSE_STATUS("Received"),
	HEALTH_RESPONSE_STATUS("Received");
	
	public String type;

	MessageHeaderEnum(String dataType) {
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
