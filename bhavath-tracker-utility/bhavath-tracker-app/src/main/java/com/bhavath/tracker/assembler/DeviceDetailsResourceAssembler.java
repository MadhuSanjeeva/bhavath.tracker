package com.bhavath.tracker.assembler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.bhavath.tracker.command.controller.DeviceDetailsCommandController;
import com.bhavath.tracker.resource.DeviceDetailsResource;
import com.bhavath.tracker.vos.DeviceDetailsVO;

@Component
public class DeviceDetailsResourceAssembler extends ResourceAssemblerSupport<DeviceDetailsVO, DeviceDetailsResource> 
{

	public DeviceDetailsResourceAssembler()
	{
		super(DeviceDetailsCommandController.class, DeviceDetailsResource.class);
	}

	@Override
	public DeviceDetailsResource toResource(DeviceDetailsVO record) 
	{
		DeviceDetailsResource resource = DeviceDetailsResource.builder()
			.deviceDetailsId(record.getId())
			.imeiNumber(record.getImeiNumber())
			.ipAddress(record.getIpAddress())
			.signalStrength(record.getSignalStrength())
			.simNumber(record.getSimNumber())
			.version(record.getVersion())
			.createdDate(record.getCreatedDate())
			.updatedDate(record.getUpdatedDate())
			.networkDate(record.getNetworkDate())
			.networkTime(record.getNetworkTime())
			.telecomProvider(record.getTelecomProvider())
			.mobileNumber(record.getMobileNumber())
			.imsiNumber(record.getImsiNumber())
			.serialNumber(record.getSerialNumber())
			.vehicleRegNo(record.getVehicleRegNo())
			.simDetailsVOS(record.getSimDetails())
			.build();
		return resource;
	}
	
	public DeviceDetailsVO fromResource(DeviceDetailsResource resource) throws IOException 
	{
		return DeviceDetailsVO.builder()
			.id(resource.getDeviceDetailsId())
			.imeiNumber(resource.getImeiNumber())
			.ipAddress(resource.getIpAddress())
			.signalStrength(resource.getSignalStrength())
			.simNumber(resource.getSimNumber())
			.version(resource.getVersion())
			.createdDate(resource.getCreatedDate())
			.updatedDate(resource.getUpdatedDate())
			.networkDate(resource.getNetworkDate())
			.networkTime(resource.getNetworkTime())
			.telecomProvider(resource.getTelecomProvider())
			.mobileNumber(resource.getMobileNumber())
			.imsiNumber(resource.getImsiNumber())
			.serialNumber(resource.getSerialNumber())
			.vehicleRegNo(resource.getVehicleRegNo())
			.simDetails(resource.getSimDetailsVOS())
			.build();
	}
	
	public List<DeviceDetailsVO> fromResource(List<DeviceDetailsResource> resources) 
	{
		List<DeviceDetailsVO> deviceDetailsResources = new ArrayList<>();
		for (DeviceDetailsResource resource : resources)
		{
			DeviceDetailsVO deviceDetailsVO = 	DeviceDetailsVO.builder()
					.id(resource.getDeviceDetailsId())
					.imeiNumber(resource.getImeiNumber())
					.ipAddress(resource.getIpAddress())
					.signalStrength(resource.getSignalStrength())
					.simNumber(resource.getSimNumber())
					.version(resource.getVersion())
					.createdDate(resource.getCreatedDate())
					.updatedDate(resource.getUpdatedDate())
					.networkDate(resource.getNetworkDate())
					.networkTime(resource.getNetworkTime())
					.serialNumber(resource.getSerialNumber())
					.vehicleRegNo(resource.getVehicleRegNo())
					.simDetails(resource.getSimDetailsVOS())
					.build();
			deviceDetailsResources.add(deviceDetailsVO);
		}
		return deviceDetailsResources;
	}
}
