package com.bhavath.tracker.vos;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceCommunicationVO implements Serializable {
    private static final long serialVersionUID = 1L;
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
	private Timestamp updatedDate;
	private String engineStatus;
}
