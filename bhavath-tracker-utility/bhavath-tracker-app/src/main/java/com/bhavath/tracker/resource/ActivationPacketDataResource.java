package com.bhavath.tracker.resource;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivationPacketDataResource extends ResourceSupport 
{
	private Long activationPacketDataId;
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
