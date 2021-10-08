package com.bhavath.tracker.assembler;

import java.io.IOException;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.bhavath.tracker.query.controller.HealthPacketDataQueryController;
import com.bhavath.tracker.resource.HealthPacketDataResource;
import com.bhavath.tracker.vos.HealthPacketVO;

@Component
public class HealthPacketDataResourceAssembler extends ResourceAssemblerSupport<HealthPacketVO, HealthPacketDataResource> 
{
	public HealthPacketDataResourceAssembler() 
	{
		super(HealthPacketDataQueryController.class, HealthPacketDataResource.class);
	}

	@Override
	public HealthPacketDataResource toResource(HealthPacketVO entity) 
	{
		return HealthPacketDataResource.builder()
			.healthPacketDataId(entity.getId())
			.header(entity.getHeader())
			.randomCode(entity.getRandomCode())
			.vendorId(entity.getVendorId())
			.firmwareVersion(entity.getFirmwareVersion())
			.imeiNumber(entity.getImeiNumber())
			.alertId(entity.getAlertId())
			.latitude(entity.getLatitude())
			.latitudeDirection(entity.getLatitudeDirection())
			.longnitude(entity.getLongnitude())
			.longnitudeDirection(entity.getLongnitudeDirection())
			.gpsFix(entity.getGpsFix())
			.date(entity.getDate())
			.time(entity.getTime())
			.heading(entity.getHeading())
			.speed(entity.getSpeed())
			.gsmStrength(entity.getGsmStrength())
			.countryCode(entity.getCountryCode())
			.networkCode(entity.getNetworkCode())
			.lac(entity.getLac())
			.mainPower(entity.getMainPower())
			.ignStatus(entity.getIgnStatus())
			.batteryVoltage(entity.getBatteryVoltage())
			.frameNumber(entity.getFrameNumber())
			.vehicleMode(entity.getVehicleMode())
			.networkDate(entity.getNetworkDate())
			.networkTime(entity.getNetworkTime())
			.rawData(entity.getRawData())
			.build();
	}

	public HealthPacketVO fromResource(HealthPacketDataResource resource) throws IOException 
	{
		return HealthPacketVO.builder()
				.id(resource.getHealthPacketDataId())
				.header(resource.getHeader())
				.randomCode(resource.getRandomCode())
				.vendorId(resource.getVendorId())
				.firmwareVersion(resource.getFirmwareVersion())
				.imeiNumber(resource.getImeiNumber())
				.alertId(resource.getAlertId())
				.latitude(resource.getLatitude())
				.latitudeDirection(resource.getLatitudeDirection())
				.longnitude(resource.getLongnitude())
				.longnitudeDirection(resource.getLongnitudeDirection())
				.gpsFix(resource.getGpsFix())
				.date(resource.getDate())
				.time(resource.getTime())
				.heading(resource.getHeading())
				.speed(resource.getSpeed())
				.gsmStrength(resource.getGsmStrength())
				.countryCode(resource.getCountryCode())
				.networkCode(resource.getNetworkCode())
				.lac(resource.getLac())
				.mainPower(resource.getMainPower())
				.ignStatus(resource.getIgnStatus())
				.batteryVoltage(resource.getBatteryVoltage())
				.frameNumber(resource.getFrameNumber())
				.vehicleMode(resource.getVehicleMode())
				.networkDate(resource.getNetworkDate())
				.networkTime(resource.getNetworkTime())
				.rawData(resource.getRawData())
			.build();
	}
}