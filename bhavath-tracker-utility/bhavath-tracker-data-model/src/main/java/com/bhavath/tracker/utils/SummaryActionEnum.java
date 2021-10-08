package com.bhavath.tracker.utils;


public enum SummaryActionEnum {
	
	IGNITION("ignition","ignition_status"," = '0', = '1'"," Off , On"), 
	EMERGENCY("emergency","emergency_status"," = '0', = '1'"," Off , On"),
	MAIN_POWER_STATUS("mainPowerStatus","main_power_status"," = '0', = '1'"," Off , On"), 
	TAMPER_ALERT("tamperAlert","tamper_alert"," = 'C', = 'O'"," Off , On"),
	COMMUNICATING("communicating","status"," = 'Not Communicating', = 'Communicating'"," Off , On"),
	BATTERY_PERCENTAGE("batteryPercentage","battery_percentage"," < '40', > '40'"," Off , On"),
	MEMORY_PERCENTAGE("memoryPercentage","memory_percentage"," < '40', > '40'"," Off , On");
	

	public String type;
	public String columnName;
	private String values;
	private String status;
	
	SummaryActionEnum(String dataType) {
		this.type = dataType;
	}
	 
	SummaryActionEnum(String dataType,String columnName) {
		this.type = dataType;
		this.columnName = columnName;
	}
	
	SummaryActionEnum(String dataType,String columnName,String values) {
		this.type = dataType;
		this.columnName = columnName;
		this.values = values;
	}

	SummaryActionEnum(String dataType,String columnName,String values,String status) {
		this.type = dataType;
		this.columnName = columnName;
		this.values = values;
		this.status = status;
	}

	public String getType() {
		return type;
	}
	 
	public String getColumnName() {
		return columnName;
	}
	
	public String getValues() {
		return values;
	}
	
	public String getStatus() {
		return status;
	}
	
	@Override
	public String toString() {
		return this.getType();
	}
}
