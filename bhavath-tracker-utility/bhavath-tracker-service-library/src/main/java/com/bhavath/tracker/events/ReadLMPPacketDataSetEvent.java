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
public class ReadLMPPacketDataSetEvent extends ReadPageEvent<ReadLMPPacketDataSetEvent>
{
	private Long id;
    private Timestamp vendorId;
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
    private String checksum;
    private String endOfPacket;
    private String searchValue;
    private String startDate;
    private String endDate;
}