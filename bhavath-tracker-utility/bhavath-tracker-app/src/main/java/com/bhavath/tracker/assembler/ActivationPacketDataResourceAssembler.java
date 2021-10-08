package com.bhavath.tracker.assembler;

import java.io.IOException;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.bhavath.tracker.query.controller.ActivationPacketDataQueryController;
import com.bhavath.tracker.resource.ActivationPacketDataResource;
import com.bhavath.tracker.vos.ActivationPacketVO;

@Component
public class ActivationPacketDataResourceAssembler extends ResourceAssemblerSupport<ActivationPacketVO, ActivationPacketDataResource> 
{
	public ActivationPacketDataResourceAssembler() 
	{
		super(ActivationPacketDataQueryController.class, ActivationPacketDataResource.class);
	}

	@Override
	public ActivationPacketDataResource toResource(ActivationPacketVO entity) 
	{
		return ActivationPacketDataResource.builder()
			.activationPacketDataId(entity.getId())
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
			.rawData(entity.getRawData())
			.build();
	}

	public ActivationPacketVO fromResource(ActivationPacketDataResource resource) throws IOException 
	{
		return ActivationPacketVO.builder()
				.id(resource.getActivationPacketDataId())
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
				.rawData(resource.getRawData())
			.build();
	}
}