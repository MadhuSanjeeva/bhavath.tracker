package com.bhavath.tracker.enums;

public enum SystemPropertiesEnum {
	
	SPEED("SPEED");

	public String type;

	SystemPropertiesEnum(String dataType) {
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
