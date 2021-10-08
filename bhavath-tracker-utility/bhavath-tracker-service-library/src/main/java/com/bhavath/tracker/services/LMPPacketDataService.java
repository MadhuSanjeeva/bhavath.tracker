package com.bhavath.tracker.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.bhavath.tracker.data.model.LMPPacketData;
import com.bhavath.tracker.data.model.SystemProperties;
import com.bhavath.tracker.data.model.VehicleDetails;
import com.bhavath.tracker.data.repos.LMPPacketDataRepository;
import com.bhavath.tracker.data.repos.LMPPacketDataSpecifications;
import com.bhavath.tracker.data.repos.SystemPropertiesRepository;
import com.bhavath.tracker.data.repos.VehicleDetailsRepository;
import com.bhavath.tracker.enums.SystemPropertiesEnum;
import com.bhavath.tracker.enums.VehicleModeEnum;
import com.bhavath.tracker.events.CreateLMPPacketDataEvent;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadLMPPacketDataSetEvent;
import com.bhavath.tracker.util.CommonUtility;
import com.bhavath.tracker.util.ObjectMapperUtils;
import com.bhavath.tracker.util.SMSConfiguration;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.LMPPacketDataVO;

import lombok.extern.slf4j.Slf4j;

public interface LMPPacketDataService {
	
	public void save(CreateLMPPacketDataEvent createLMPPacketDataEvent);
	public PageReadEvent<LMPPacketDataVO> readData(ReadLMPPacketDataSetEvent request);
	
	@Service
	@Slf4j
	public class impl implements LMPPacketDataService{
		
		@Autowired private LMPPacketDataRepository lmpPacketDataRepository;
		@Autowired private SystemPropertiesRepository systemPropertiesRepository;
		@Autowired private SMSConfiguration smsConfig;
		@Autowired private VehicleDetailsRepository vehicleDetailsRepository;
		
		@Override
		public void save(CreateLMPPacketDataEvent createLMPPacketDataEvent) {

			LMPPacketDataVO lmpPacketDataVO = createLMPPacketDataEvent.getLMPPacketDataVO();
			
			String latitude = CommonUtility.convertLatitude(lmpPacketDataVO.getLatitude());
			String longitude = CommonUtility.convertLangitude(lmpPacketDataVO.getLongitude());

			if (VehicleModeEnum.RUNNING_MODE.getVehicleModeType().equalsIgnoreCase(lmpPacketDataVO.getVehicleModeId())) {
				lmpPacketDataVO.setVehicleMode("RUNNING MODE");
			}
			if (VehicleModeEnum.IDLE_MODE.getVehicleModeType().equalsIgnoreCase(lmpPacketDataVO.getVehicleModeId())) {
				lmpPacketDataVO.setVehicleMode("IDLE MODE");
			}
			if (VehicleModeEnum.SLEEP_MODE.getVehicleModeType().equalsIgnoreCase(lmpPacketDataVO.getVehicleModeId())) {
				lmpPacketDataVO.setVehicleMode("SLEEP MODE");
			}
			if (VehicleModeEnum.PARKING_MODE.getVehicleModeType().equalsIgnoreCase(lmpPacketDataVO.getVehicleModeId())) {
				lmpPacketDataVO.setVehicleMode("PARKING MODE");
			}
			
			LMPPacketData lmpPacketData = LMPPacketData.builder()
					.vendorId(lmpPacketDataVO.getVendorId())
					.createdDate(DateUtils.getCurrentSystemTimestamp())
					.firmwareVersion(lmpPacketDataVO.getFirmwareVersion())
					.packetType(lmpPacketDataVO.getPacketType())
					.packetStatus(lmpPacketDataVO.getPacketStatus())
					.imei(lmpPacketDataVO.getImei())
					.vehicleRegNo(lmpPacketDataVO.getVehicleRegNo())
					.gpsFix(lmpPacketDataVO.getGpsFix())
					.date(lmpPacketDataVO.getDate())
					.time(lmpPacketDataVO.getTime())
					.latitude(latitude)
					.latitudeDirection(lmpPacketDataVO.getLatitudeDirection())
					.longitude(longitude)
					.longitudeDirection(lmpPacketDataVO.getLongitudeDirection())
					.speed(lmpPacketDataVO.getSpeed())
					.heading(lmpPacketDataVO.getHeading())
					.noOfSatellites(lmpPacketDataVO.getNoOfSatellites())
					.altitude(lmpPacketDataVO.getAltitude())
					.pdop(lmpPacketDataVO.getPdop())
					.hdop(lmpPacketDataVO.getHdop())
					.networkOperatorName(lmpPacketDataVO.getNetworkOperatorName())
					.ignition(lmpPacketDataVO.getIgnition())
					.mainPowerStatus(lmpPacketDataVO.getMainPowerStatus())
					.mainInputVoltage(lmpPacketDataVO.getMainInputVoltage())
					.internalBatteryVoltage(lmpPacketDataVO.getInternalBatteryVoltage())
					.emergencyStatus(lmpPacketDataVO.getEmergencyStatus())
					.tamperAlert(lmpPacketDataVO.getTamperAlert())
					.gsmSignalStrength(lmpPacketDataVO.getGsmSignalStrength())
					.mcc(lmpPacketDataVO.getMcc())
					.lac(lmpPacketDataVO.getLac())
					.cellId(lmpPacketDataVO.getCellId())
					.nmr(lmpPacketDataVO.getNmr())
					.digitalInputStatus(lmpPacketDataVO.getDigitalInputStatus())
					.digitalOutputStatus(lmpPacketDataVO.getDigitalOutputStatus())
					.frameNumber(lmpPacketDataVO.getFrameNumber())
					.networkDate(lmpPacketDataVO.getNetworkDate())
					.networkTime(lmpPacketDataVO.getNetworkTime())
					.vehicleMode(lmpPacketDataVO.getVehicleMode())
					.vehicleModeId(lmpPacketDataVO.getVehicleModeId())
					.engineStatus(lmpPacketDataVO.getIgnition())  //lmpPacketData - Saving engineStatus as ignitionsStatus.
					.overNetworkIp(lmpPacketDataVO.getOverNetworkIp())
					.overSmsNumber(lmpPacketDataVO.getOverSmsNumber())
					.analogOne(lmpPacketDataVO.getAnalogOne())
					.analogZero(lmpPacketDataVO.getAnalogZero())
					.overTheAirParameter(lmpPacketDataVO.getOverTheAirParameter())
					.build();
			log.info("LMPPacketDataService  :::  save "+lmpPacketData);
			lmpPacketDataRepository.save(lmpPacketData);
			lmpPacketDataRepository.flush();
			SystemProperties systemProperty = systemPropertiesRepository.findByPropertyName(SystemPropertiesEnum.SPEED.getType());
			if(Double.parseDouble(systemProperty.getPropertyValue()) < Double.parseDouble(lmpPacketDataVO.getSpeed())) {
				VehicleDetails vehicleDetails = vehicleDetailsRepository.findByVehicleRegNumber(lmpPacketDataVO.getVehicleRegNo());
				smsConfig.sendSms(vehicleDetails.getMobileNumber(), "$,Over Speed Alert,"+vehicleDetails.getImeiNumber()+","+vehicleDetails.getVehicleRegNumber()+","+DateUtils.getCurrentTimestamp()+","+lmpPacketDataVO.getSpeed()+",* ");
				log.info("Over Speed Alert Sent....");
			}
		}
		
		@Override
		public PageReadEvent<LMPPacketDataVO> readData(ReadLMPPacketDataSetEvent request) 
		{
			Page<LMPPacketData> dbContent = lmpPacketDataRepository.findAll(new LMPPacketDataSpecifications(request.getImei(),request.getVehicleRegNo(),request.getSearchValue(),request.getStartDate(),request.getEndDate()),
					LMPPacketDataSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize()));
			List<LMPPacketDataVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), LMPPacketDataVO.class);
			Page<LMPPacketDataVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}
		
	}
	
}
