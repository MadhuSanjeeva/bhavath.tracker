package com.bhavath.tracker.vos;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LMPPacketDataVO implements Serializable
{
	private static final long serialVersionUID = 1L;
    private Long id;
    private Timestamp createdDate;
    private String vendorId;
    private String firmwareVersion;
    private String packetType;
    private String packetStatus;
    private String imei;
    private String vehicleRegNo;
    private String gpsFix;
    private String date;
    private String time;
    private String latitude;
    private String latitudeDirection;
    private String longitude;
    private String longitudeDirection;
    private String speed;
    private String heading;
    private String noOfSatellites;
    private String altitude;
    private String pdop;
    private String hdop;
    private String networkOperatorName;
    private String ignition;
    private String mainPowerStatus;
    private String mainInputVoltage;
    private String internalBatteryVoltage;
    private String emergencyStatus;
    private String tamperAlert;
    private String gsmSignalStrength;
    private String mcc;
    private String lac;
    private String cellId;
    private String nmr;
    private String digitalInputStatus;
    private String digitalOutputStatus;
    private String frameNumber;
    private String networkDate;
    private String networkTime;
	private String vehicleMode;
	private String vehicleModeId;
	private String engineStatus;
	private String overNetworkIp;
	private String overSmsNumber;
	private String analogZero;
	private String analogOne;
	private String overTheAirParameter;
}
