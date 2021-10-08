package com.bhavath.tracker.events;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class ReadDeviceCommunicationEvent extends ReadPageEvent<ReadDeviceCommunicationEvent>
{
	private Long id;
	private Timestamp createdDate;
	private String networkTime;
	private String networkDate;
	private String latitude;
	private String langitude;
	private String location;
	private String prevNetworkTime;
	private String prevNetworkDate;
	private String prevLatitude;
	private String prevLangitude;
	private String imeiNumber;
	private String ignitionStatus;
    private String vehicleRegNo;
	private String speed;
	private String emergencyStatus;
	private String mainPowerStatus;
	private String internalBatteryVoltage;
	private String gsmSignalStrength;
	private String tamperAlert;
	private String batteryPercentage;
	private String lowBatteryThreshold;
	private String memoryPercentage;
	private String searchDate;
	private String status;
	private String vehicleMode;
}