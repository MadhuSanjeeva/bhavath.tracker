package com.bhavath.tracker.enums;

public enum TripDetailsEnum {
	
	TRIP("TRIP"), 
	GEOFENCING("GEOFENCING");

	public String type;

	TripDetailsEnum(String dataType) {
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
