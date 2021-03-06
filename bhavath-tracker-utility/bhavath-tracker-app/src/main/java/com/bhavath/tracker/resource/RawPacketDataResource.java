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
public class RawPacketDataResource extends ResourceSupport
{
	private Long rawDataId;
	private String createdDate;
	private String serialNumber;
	private String rawData;
	private String networkTime;
	private String networkDate;
	private String imeiNumber;
	private String vehicleRegNo;
	private String cvpType;
	private String inBoundChannel;
	private String packetStatus;
	private String packetType;
	private String vehicleModeId;
}
