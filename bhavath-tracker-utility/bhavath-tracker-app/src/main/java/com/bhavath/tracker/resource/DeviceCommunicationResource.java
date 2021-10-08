package com.bhavath.tracker.resource;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@JsonInclude(Include.ALWAYS)
public class DeviceCommunicationResource extends ResourceSupport
{
	private Long deviceCommunicationId;
	private String createdDate;
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
	private String status;
	private String ignitionStatus;
    private String vehicleRegNo;
	private String speed;
	private String emergencyStatus;
	private String mainPowerStatus;
	private String internalBatteryVoltage;
	private String gsmSignalStrength;
	private String tamperAlert;
	private Double batteryPercentage;
	private Double lowBatteryThreshold;
	private Double memoryPercentage;
	private String vehicleMode;
	private String vehicleModeId;
	private Boolean isDeviceOverSpeed;
	private String updatedDate;
	private String engineStatus;
}
