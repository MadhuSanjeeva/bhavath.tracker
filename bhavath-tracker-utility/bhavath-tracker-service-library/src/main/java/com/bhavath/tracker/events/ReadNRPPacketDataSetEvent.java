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
public class ReadNRPPacketDataSetEvent extends ReadPageEvent<ReadNRPPacketDataSetEvent>
{
	private Long id;
	private Timestamp createdDate;
    private Timestamp packetType;
    private String imeiNumber;
    private String packetStaus;
    private String timestamp;
    private String gpsValidity;
    private String latitude;
    private String latitudeDirection;
    private String longitude;
    private String longitudeDirection;
    private String altitude;
    private String speed;
    private String distance;
    private String provider;
    private String vehicleRegNo;
    private String replyNumber;
    private String endCharacter;
    private String checksum;
    private String searchValue;
    private String startDate;
    private String endDate;
}