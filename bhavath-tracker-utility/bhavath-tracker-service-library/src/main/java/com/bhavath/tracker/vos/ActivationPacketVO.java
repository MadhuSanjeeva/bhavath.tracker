package com.bhavath.tracker.vos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivationPacketVO implements Serializable
{
	private static final long serialVersionUID = 1L;
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
   	private String rawData;
}
