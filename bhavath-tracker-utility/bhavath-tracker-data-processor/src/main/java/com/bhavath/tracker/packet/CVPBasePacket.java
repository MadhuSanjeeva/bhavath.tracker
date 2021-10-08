package com.bhavath.tracker.packet;

import java.io.Serializable;

import com.bhavath.tracker.enums.CVPPacketType;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CVPBasePacket  implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private CVPPacketType packetType;
	private String identifier;
	private String networkTime;
	private String networkDate;
	private String simNumber;
	private String ipAddress;
	private String imsi;
	private String imeiNumber;
	private String vehicleRegNo;
	private String networkOperatorName;
	private String packetStatus;
	private String pcktType;
	private String vehicleModeId;
}
