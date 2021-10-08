package com.bhavath.tracker.enums;

public enum VehicleModeEnum {
	
	RUNNING_MODE("00"),
	IDLE_MODE("01"),
	SLEEP_MODE("02"),
	PARKING_MODE("03");

	public String vehicleModeType;

	VehicleModeEnum(String vehicleModeType) {
		this.vehicleModeType = vehicleModeType;
	}

	public String getVehicleModeType() {
		return vehicleModeType;
	}

	@Override
	public String toString() {
		return this.getVehicleModeType();
	}
}
