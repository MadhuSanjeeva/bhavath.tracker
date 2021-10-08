package com.bhavath.tracker.resource;

import java.sql.Timestamp;

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
public class EBPPacketDataResource extends ResourceSupport 
{
	private Long ebpPacketDataId;
	private String createdDate;
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
    private String replyNumber;
    private String endCharacter;
    private String checksum;
    private String networkDate;
	private String networkTime;
}
