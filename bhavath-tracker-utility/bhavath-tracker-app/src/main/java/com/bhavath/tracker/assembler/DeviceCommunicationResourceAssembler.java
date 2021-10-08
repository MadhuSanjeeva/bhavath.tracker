package com.bhavath.tracker.assembler;

import java.io.IOException;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.query.controller.DeviceCommunicationQueryController;
import com.bhavath.tracker.resource.DeviceCommunicationResource;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.DeviceCommunicationVO;

@Component
public class DeviceCommunicationResourceAssembler extends ResourceAssemblerSupport<DeviceCommunicationVO, DeviceCommunicationResource> 
{

	public DeviceCommunicationResourceAssembler()
	{
		super(DeviceCommunicationQueryController.class, DeviceCommunicationResource.class);
	}

	@Override
	public DeviceCommunicationResource toResource(DeviceCommunicationVO entity) 
	{
		return DeviceCommunicationResource.builder()
			.deviceCommunicationId(entity.getId())
			.createdDate(!StringUtils.isEmpty(entity.getCreatedDate()) ? DateUitls.getStringFromTimestamp(entity.getCreatedDate()) : null)
			.networkDate(entity.getNetworkDate())
			.networkTime(entity.getNetworkTime())
			.latitude(entity.getLatitude())
			.langitude(entity.getLangitude())
			.location(entity.getLocation())
			.prevLangitude(entity.getPrevLangitude())
			.prevLatitude(entity.getPrevLatitude())
			.prevNetworkDate(entity.getPrevNetworkDate())
			.prevNetworkTime(entity.getPrevNetworkTime())
			.imeiNumber(entity.getImeiNumber())
			.status(entity.getStatus())
			.ignitionStatus(entity.getIgnitionStatus())
			.vehicleRegNo(entity.getVehicleRegNo())
			.speed(entity.getSpeed())
			.emergencyStatus(entity.getEmergencyStatus())
			.mainPowerStatus(entity.getMainPowerStatus())
			.internalBatteryVoltage(entity.getInternalBatteryVoltage())
			.gsmSignalStrength(entity.getGsmSignalStrength())
			.tamperAlert(entity.getTamperAlert())
			.batteryPercentage(entity.getBatteryPercentage())
			.lowBatteryThreshold(entity.getLowBatteryThreshold())
			.memoryPercentage(entity.getMemoryPercentage())
			.vehicleMode(entity.getVehicleMode())
			.vehicleModeId(entity.getVehicleModeId())
			.isDeviceOverSpeed(entity.getIsDeviceOverSpeed())
			.updatedDate(!StringUtils.isEmpty(entity.getUpdatedDate()) ? DateUitls.getStringFromTimestamp(entity.getUpdatedDate()) : null)
			.engineStatus(entity.getEngineStatus())
			.build();
	}
	
	public DeviceCommunicationVO fromResource(DeviceCommunicationResource resource) throws IOException 
	{
		return DeviceCommunicationVO.builder()
			.id(resource.getDeviceCommunicationId())
			.createdDate(DateUtils.getCurrentSystemTimestamp())
			.createdDate(!StringUtils.isEmpty(resource.getCreatedDate()) ? DateUtils.getTimeStampDateFromString(resource.getCreatedDate()) : null)
			.networkDate(resource.getNetworkDate())
			.networkTime(resource.getNetworkTime())
			.latitude(resource.getLatitude())
			.langitude(resource.getLangitude())
			.location(resource.getLocation())
			.prevLangitude(resource.getPrevLangitude())
			.prevLatitude(resource.getPrevLatitude())
			.prevNetworkDate(resource.getPrevNetworkDate())
			.prevNetworkTime(resource.getPrevNetworkTime())
			.imeiNumber(resource.getImeiNumber())
			.status(resource.getStatus())
			.ignitionStatus(resource.getIgnitionStatus())
			.vehicleRegNo(resource.getVehicleRegNo())
			.speed(resource.getSpeed())
			.emergencyStatus(resource.getEmergencyStatus())
			.mainPowerStatus(resource.getMainPowerStatus())
			.internalBatteryVoltage(resource.getInternalBatteryVoltage())
			.gsmSignalStrength(resource.getGsmSignalStrength())
			.tamperAlert(resource.getTamperAlert())
			.batteryPercentage(resource.getBatteryPercentage())
			.lowBatteryThreshold(resource.getLowBatteryThreshold())
			.memoryPercentage(resource.getMemoryPercentage())
			.vehicleMode(resource.getVehicleMode())
			.vehicleModeId(resource.getVehicleModeId())
			.isDeviceOverSpeed(resource.getIsDeviceOverSpeed())
			.updatedDate(!StringUtils.isEmpty(resource.getUpdatedDate()) ? DateUtils.getTimeStampDateFromString(resource.getUpdatedDate()) : null)
			.engineStatus(resource.getEngineStatus())
			.build();
	}
}