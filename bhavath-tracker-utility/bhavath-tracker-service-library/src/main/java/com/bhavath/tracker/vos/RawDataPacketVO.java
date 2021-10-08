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

@Setter
@Getter
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RawDataPacketVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Timestamp createdDate;
	private String rawData;
	private String networkDate;
	private String networkTime;
	private String imeiNumber;
	private String vehicleRegNo;
	private String cvpType;
	private String inBoundChannel;
	private Long id;
	private String packetStatus;
	private String packetType;
	private String vehicleModeId;
}
