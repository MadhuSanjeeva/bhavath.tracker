package com.bhavath.tracker.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class ReadHealthPacketDataSetEvent extends ReadPageEvent<ReadHealthPacketDataSetEvent>
{
	private Long id;
	private String header;
	private String randomCode;
   	private String vendorId;
   	private String firmwareVersion;
   	private String imeiNumber;
   	private String alertId;
   	private String latitude;
   	private String latitudeDirection;
   	private String longnitude;
   	private String longnitudeDirection;
   	private String gpsFix;
   	private String date;
   	private String time;
   	private String heading;
   	private String speed;
   	private String gsmStrength;
   	private String countryCode;
   	private String networkCode;
   	private String lac;
   	private String mainPower;
   	private String ignStatus;
   	private String batteryVoltage;
   	private String frameNumber;
   	private String vehicleMode;
   	private String searchDate;
}