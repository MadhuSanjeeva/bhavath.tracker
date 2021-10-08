package com.bhavath.tracker.assembler;

import java.io.IOException;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.query.controller.LMPPacketDataQueryController;
import com.bhavath.tracker.resource.LMPPacketDataResource;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.LMPPacketDataVO;

@Component
public class LMPPacketDataResourceAssembler extends ResourceAssemblerSupport<LMPPacketDataVO, LMPPacketDataResource> 
{
	public LMPPacketDataResourceAssembler() 
	{
		super(LMPPacketDataQueryController.class, LMPPacketDataResource.class);
	}

	@Override
	public LMPPacketDataResource toResource(LMPPacketDataVO entity) 
	{
		return LMPPacketDataResource.builder()
			.lmpPacketDataId(entity.getId())
			.createdDate(!StringUtils.isEmpty(entity.getCreatedDate()) ? DateUitls.getStringFromTimestamp(entity.getCreatedDate()) : null)
			.vendorId(entity.getVendorId())
			.firmwareVersion(entity.getFirmwareVersion())
			.packetType(entity.getPacketType())
			.packetStatus(entity.getPacketStatus())
			.imei(entity.getImei())
			.vehicleRegNo(entity.getVehicleRegNo())
			.gpsFix(entity.getGpsFix())
			.date(entity.getDate())
			.time(entity.getTime())
			.latitude(entity.getLatitude())
			.latitudeDirection(entity.getLatitudeDirection())
			.longitude(entity.getLongitude())
			.longitudeDirection(entity.getLongitudeDirection())
			.speed(entity.getSpeed())
			.heading(entity.getHeading())
			.noOfSatellites(entity.getNoOfSatellites())
			.altitude(entity.getAltitude())
			.pdop(entity.getPdop())
			.hdop(entity.getHdop())
			.networkOperatorName(entity.getNetworkOperatorName())
			.ignition(entity.getIgnition())
			.mainPowerStatus(entity.getMainPowerStatus())
			.mainInputVoltage(entity.getMainInputVoltage())
			.internalBatteryVoltage(entity.getInternalBatteryVoltage())
			.emergencyStatus(entity.getEmergencyStatus())
			.tamperAlert(entity.getTamperAlert())
			.gsmSignalStrength(entity.getGsmSignalStrength())
			.mcc(entity.getMcc())
			.lac(entity.getLac())
			.cellId(entity.getCellId())
			.nmr(entity.getNmr())
			.digitalInputStatus(entity.getDigitalInputStatus())
			.digitalOutputStatus(entity.getDigitalOutputStatus())
			.frameNumber(entity.getFrameNumber())
			.networkDate(entity.getNetworkDate())
			.networkTime(entity.getNetworkTime())
			.vehicleMode(entity.getVehicleMode())
			.vehicleModeId(entity.getVehicleModeId())
			.engineStatus(entity.getEngineStatus())
			.overNetworkIp(entity.getOverNetworkIp())
			.overSmsNumber(entity.getOverSmsNumber())
			.analogZero(entity.getAnalogZero())
			.analogOne(entity.getAnalogOne())
			.overTheAirParameter(entity.getOverTheAirParameter())
			.build();
	}

	public LMPPacketDataVO fromResource(LMPPacketDataResource resource) throws IOException 
	{
		return LMPPacketDataVO.builder()
			.id(resource.getLmpPacketDataId())
			.createdDate(!StringUtils.isEmpty(resource.getCreatedDate()) ? DateUtils.getTimeStampDateFromString(resource.getCreatedDate()) : null)
			.vendorId(resource.getVendorId())
			.firmwareVersion(resource.getFirmwareVersion())
			.packetType(resource.getPacketType())
			.packetStatus(resource.getPacketStatus())
			.imei(resource.getImei())
			.vehicleRegNo(resource.getVehicleRegNo())
			.gpsFix(resource.getGpsFix())
			.date(resource.getDate())
			.time(resource.getTime())
			.latitude(resource.getLatitude())
			.latitudeDirection(resource.getLatitudeDirection())
			.longitude(resource.getLongitude())
			.longitudeDirection(resource.getLongitudeDirection())
			.speed(resource.getSpeed())
			.heading(resource.getHeading())
			.noOfSatellites(resource.getNoOfSatellites())
			.altitude(resource.getAltitude())
			.pdop(resource.getPdop())
			.hdop(resource.getHdop())
			.networkOperatorName(resource.getNetworkOperatorName())
			.ignition(resource.getIgnition())
			.mainPowerStatus(resource.getMainPowerStatus())
			.mainInputVoltage(resource.getMainInputVoltage())
			.internalBatteryVoltage(resource.getInternalBatteryVoltage())
			.emergencyStatus(resource.getEmergencyStatus())
			.tamperAlert(resource.getTamperAlert())
			.gsmSignalStrength(resource.getGsmSignalStrength())
			.mcc(resource.getMcc())
			.lac(resource.getLac())
			.cellId(resource.getCellId())
			.nmr(resource.getNmr())
			.digitalInputStatus(resource.getDigitalInputStatus())
			.digitalOutputStatus(resource.getDigitalOutputStatus())
			.frameNumber(resource.getFrameNumber())
			.networkDate(resource.getNetworkDate())
			.networkTime(resource.getNetworkTime())
			.vehicleMode(resource.getVehicleMode())
			.vehicleModeId(resource.getVehicleModeId())
			.engineStatus(resource.getEngineStatus())
			.overNetworkIp(resource.getOverNetworkIp())
			.overSmsNumber(resource.getOverSmsNumber())
			.analogZero(resource.getAnalogZero())
			.analogOne(resource.getAnalogOne())
			.overTheAirParameter(resource.getOverTheAirParameter())
			.build();
	}
}