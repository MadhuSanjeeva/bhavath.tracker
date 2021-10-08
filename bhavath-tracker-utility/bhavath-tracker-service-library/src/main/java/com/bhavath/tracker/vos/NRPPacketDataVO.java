package com.bhavath.tracker.vos;

import java.io.Serializable;
import java.sql.Timestamp;

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
public class NRPPacketDataVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Timestamp createdDate;
    private String packetType;
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
    private String firmwareVersion;
    private String endCharacter;
    private String checksum;
    private String networkDate;
    private String networkTime;
}
