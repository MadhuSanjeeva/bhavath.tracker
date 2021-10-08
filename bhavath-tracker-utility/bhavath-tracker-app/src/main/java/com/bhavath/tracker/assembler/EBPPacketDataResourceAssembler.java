package com.bhavath.tracker.assembler;

import java.io.IOException;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.query.controller.EBPPacketDataQueryController;
import com.bhavath.tracker.resource.EBPPacketDataResource;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.EBPPacketDataVO;

@Component
public class EBPPacketDataResourceAssembler extends ResourceAssemblerSupport<EBPPacketDataVO, EBPPacketDataResource> 
{
	public EBPPacketDataResourceAssembler() 
	{
		super(EBPPacketDataQueryController.class, EBPPacketDataResource.class);
	}

	@Override
	public EBPPacketDataResource toResource(EBPPacketDataVO entity) 
	{
		return EBPPacketDataResource.builder()
			.ebpPacketDataId(entity.getId())
			.createdDate(!StringUtils.isEmpty(entity.getCreatedDate()) ? DateUitls.getStringFromTimestamp(entity.getCreatedDate()) : null)
			.packetType(entity.getPacketType())
			.imeiNumber(entity.getImeiNumber())
			.packetStaus(entity.getPacketStaus())
			.timestamp(entity.getTimestamp())
			.gpsValidity(entity.getGpsValidity())
			.latitude(entity.getLatitude())
			.latitudeDirection(entity.getLatitudeDirection())
			.longitude(entity.getLongitude())
			.longitudeDirection(entity.getLongitudeDirection())
			.altitude(entity.getAltitude())
			.speed(entity.getSpeed())
			.distance(entity.getDistance())
			.provider(entity.getProvider())
			.vehicleRegNo(entity.getVehicleRegNo())
			.replyNumber(entity.getReplyNumber())
			.endCharacter(entity.getEndCharacter())
			.checksum(entity.getChecksum())
			.networkDate(entity.getNetworkDate())
			.networkTime(entity.getNetworkTime())
			.build();
	}

	public EBPPacketDataVO fromResource(EBPPacketDataResource resource) throws IOException 
	{
		return EBPPacketDataVO.builder()
			.id(resource.getEbpPacketDataId())
			.createdDate(!StringUtils.isEmpty(resource.getCreatedDate()) ? DateUtils.getTimeStampDateFromString(resource.getCreatedDate()) : null)
			.packetType(resource.getPacketType())
			.imeiNumber(resource.getImeiNumber())
			.packetStaus(resource.getPacketStaus())
			.timestamp(resource.getTimestamp())
			.gpsValidity(resource.getGpsValidity())
			.latitude(resource.getLatitude())
			.latitudeDirection(resource.getLatitudeDirection())
			.longitude(resource.getLongitude())
			.longitudeDirection(resource.getLongitudeDirection())
			.altitude(resource.getAltitude())
			.speed(resource.getSpeed())
			.distance(resource.getDistance())
			.provider(resource.getProvider())
			.vehicleRegNo(resource.getVehicleRegNo())
			.replyNumber(resource.getReplyNumber())
			.endCharacter(resource.getEndCharacter())
			.checksum(resource.getChecksum())
			.networkDate(resource.getNetworkDate())
			.networkTime(resource.getNetworkTime())
			.build();
	}
}