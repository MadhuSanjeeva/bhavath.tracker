package com.bhavath.tracker.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.query.controller.RawPacketDataQueryController;
import com.bhavath.tracker.resource.RawPacketDataResource;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.RawDataPacketVO;

@Component
public class RawPacketDataResourceAssembler extends ResourceAssemblerSupport<RawDataPacketVO, RawPacketDataResource> 
{

	public RawPacketDataResourceAssembler()
	{
		super(RawPacketDataQueryController.class, RawPacketDataResource.class);
	}

	@Override
	public RawPacketDataResource toResource(RawDataPacketVO entity) 
	{
		return RawPacketDataResource.builder()
			.rawDataId(entity.getId())
			.imeiNumber(entity.getImeiNumber())
			.createdDate(!StringUtils.isEmpty(entity.getCreatedDate()) ? DateUitls.getStringFromTimestamp(entity.getCreatedDate()) : null)
			.networkDate(entity.getNetworkDate())
			.networkTime(entity.getNetworkTime())
			.rawData(entity.getRawData())
			.vehicleRegNo(entity.getVehicleRegNo())
			.cvpType(entity.getCvpType())
			.inBoundChannel(entity.getInBoundChannel())
			.packetStatus(entity.getPacketStatus())
			.packetType(entity.getPacketType())
			.vehicleModeId(entity.getVehicleModeId())
			.build();
	}
	
	public RawDataPacketVO fromResource(RawPacketDataResource resource)
	{
		return RawDataPacketVO.builder()
			.id(resource.getRawDataId())
			.imeiNumber(resource.getImeiNumber())
			.createdDate(!StringUtils.isEmpty(resource.getCreatedDate()) ? DateUtils.getTimeStampDateFromString(resource.getCreatedDate()) : null)
			.networkDate(resource.getNetworkDate())
			.networkTime(resource.getNetworkTime())
			.rawData(resource.getRawData())
			.vehicleRegNo(resource.getVehicleRegNo())
			.cvpType(resource.getCvpType())
			.packetStatus(resource.getPacketStatus())
			.packetType(resource.getPacketType())
			.vehicleModeId(resource.getVehicleModeId())
			.build();
	}
}