package com.bhavath.tracker.assembler;

import java.io.IOException;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.query.controller.HMPPacketDataQueryController;
import com.bhavath.tracker.resource.HMPPacketDataResource;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.HMPPacketDataVO;

@Component
public class HMPPacketDataResourceAssembler extends ResourceAssemblerSupport<HMPPacketDataVO, HMPPacketDataResource> 
{
	public HMPPacketDataResourceAssembler() 
	{
		super(HMPPacketDataQueryController.class, HMPPacketDataResource.class);
	}

	@Override
	public HMPPacketDataResource toResource(HMPPacketDataVO entity) 
	{
		return HMPPacketDataResource.builder()
			.hmpPacketDataId(entity.getId())
			.createdDate(!StringUtils.isEmpty(entity.getCreatedDate()) ? DateUitls.getStringFromTimestamp(entity.getCreatedDate()) : null)
			.vendorId(entity.getVendorId())
			.firmwareVersion(entity.getFirmwareVersion())
			.imeiNumber(entity.getImeiNumber())
			.batteryPercentage(entity.getBatteryPercentage())
			.lowBatteryThrValue(entity.getLowBatteryThrValue())
			.memoryPercentage(entity.getMemoryPercentage())
			.dataUpdateON(entity.getDataUpdateON())
			.dataUpdateOFF(entity.getDataUpdateOFF())
			.digitalIOStatus(entity.getDigitalIOStatus())
			.analogIOStatus(entity.getAnalogIOStatus())
			.endOfPacket(entity.getEndOfPacket())
			.networkDate(entity.getNetworkDate())
			.networkTime(entity.getNetworkTime())
			.build();
	}

	public HMPPacketDataVO fromResource(HMPPacketDataResource resource) throws IOException 
	{
		return HMPPacketDataVO.builder()
			.id(resource.getHmpPacketDataId())
			.createdDate(!StringUtils.isEmpty(resource.getCreatedDate()) ? DateUtils.getTimeStampDateFromString(resource.getCreatedDate()) : null)
			.vendorId(resource.getVendorId())
			.firmwareVersion(resource.getFirmwareVersion())
			.imeiNumber(resource.getImeiNumber())
			.batteryPercentage(resource.getBatteryPercentage())
			.lowBatteryThrValue(resource.getLowBatteryThrValue())
			.memoryPercentage(resource.getMemoryPercentage())
			.dataUpdateON(resource.getDataUpdateON())
			.dataUpdateOFF(resource.getDataUpdateOFF())
			.digitalIOStatus(resource.getDigitalIOStatus())
			.analogIOStatus(resource.getAnalogIOStatus())
			.endOfPacket(resource.getEndOfPacket())
			.networkDate(resource.getNetworkDate())
			.networkTime(resource.getNetworkTime())
			.build();
	}
}