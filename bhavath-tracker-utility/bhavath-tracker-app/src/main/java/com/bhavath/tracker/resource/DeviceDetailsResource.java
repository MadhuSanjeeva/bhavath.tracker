package com.bhavath.tracker.resource;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.bhavath.tracker.vos.SIMDetailsVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@NoArgsConstructor
@Builder
@AllArgsConstructor
@Accessors(chain = true)
@Setter
@Getter
@JsonInclude(Include.ALWAYS)
public class DeviceDetailsResource extends ResourceSupport 
{
	private Long deviceDetailsId;
	private String imeiNumber;
	private String simNumber;
	private String imsiNumber;
	private String ipAddress;
	private String signalStrength;
	private String version;
	private String serialNumber;
	private String createdDate;
	private String mobileNumber;
	private String updatedDate;
	private String networkDate;
	private String networkTime;
    private String vehicleRegNo;
	//for sim details save
	private String telecomProvider;
	private List<SIMDetailsVO> simDetailsVOS;
}
