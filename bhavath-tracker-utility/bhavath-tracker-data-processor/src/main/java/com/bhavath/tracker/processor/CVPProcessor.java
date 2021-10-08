package com.bhavath.tracker.processor;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.data.model.DeviceCommunication;
import com.bhavath.tracker.data.repos.DeviceCommunicationRepository;
import com.bhavath.tracker.data.repos.TripTrackingRepository;
import com.bhavath.tracker.events.CreateActivationPacketDataEvent;
import com.bhavath.tracker.events.CreateEBPPacketDataEvent;
import com.bhavath.tracker.events.CreateHMPPacketDataEvent;
import com.bhavath.tracker.events.CreateHealthPacketDataEvent;
import com.bhavath.tracker.events.CreateLMPPacketDataEvent;
import com.bhavath.tracker.events.CreateNRPPacketDataEvent;
import com.bhavath.tracker.packet.ActivationPacket;
import com.bhavath.tracker.packet.CVPBasePacket;
import com.bhavath.tracker.packet.EBPPacket;
import com.bhavath.tracker.packet.HMPPacket;
import com.bhavath.tracker.packet.HealthPacket;
import com.bhavath.tracker.packet.LMPPacket;
import com.bhavath.tracker.packet.NRPPacket;
import com.bhavath.tracker.services.ActivationPacketDataService;
import com.bhavath.tracker.services.DeviceCommunicationService;
import com.bhavath.tracker.services.DeviceDetailsService;
import com.bhavath.tracker.services.EBPPacketDataService;
import com.bhavath.tracker.services.HMPPacketDataService;
import com.bhavath.tracker.services.HealthPacketDataService;
import com.bhavath.tracker.services.LMPPacketDataService;
import com.bhavath.tracker.services.NRPPacketDataService;
import com.bhavath.tracker.services.RawDataPacketService;
import com.bhavath.tracker.services.SimDetailsService;
import com.bhavath.tracker.services.TripTrackingService;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.util.Geocoder;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.ActivationPacketVO;
import com.bhavath.tracker.vos.DeviceCommunicationHistoryVO;
import com.bhavath.tracker.vos.DeviceCommunicationVO;
import com.bhavath.tracker.vos.DeviceDetailsVO;
import com.bhavath.tracker.vos.EBPPacketDataVO;
import com.bhavath.tracker.vos.HMPPacketDataVO;
import com.bhavath.tracker.vos.HealthPacketVO;
import com.bhavath.tracker.vos.LMPPacketDataVO;
import com.bhavath.tracker.vos.NRPPacketDataVO;
import com.bhavath.tracker.vos.RawDataPacketVO;
import com.bhavath.tracker.vos.SIMDetailsVO;
import com.bhavath.tracker.vos.TripTrackingVO;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

public interface CVPProcessor {
	public void parse(String inputData) throws JsonProcessingException;

	@Service
	@Slf4j
	public class Impl implements CVPProcessor {
		@Autowired
		private RawDataPacketService rawDataPacketService;
		@Autowired
		private DeviceDetailsService deviceDetailsService;
		@Autowired
		private SimDetailsService simDetailsService;
		@Autowired
		private LMPPacketDataService lmpPacketDataService;
		@Autowired
		private HMPPacketDataService hmpPacketDataService;
		@Autowired
		private EBPPacketDataService ebpPacketDataService;
		
		@Autowired
		private NRPPacketDataService nrpPacketDataService;
		
		@Autowired
		private HealthPacketDataService healthPacketDataService;
		@Autowired
		private ActivationPacketDataService activationPacketDataService;
		@Autowired
		private DataIngestionProcessorHelper processorHelper;
		@Autowired
		private DeviceCommunicationService deviceCommunicationService;
		@Autowired
		private DeviceCommunicationRepository deviceCommunicationRepository;
		@Autowired
		private TripTrackingRepository tripTrackingRepository;
		@Autowired
		private TripTrackingService tripTrackingService;

		public void parse(String inputData) throws JsonProcessingException {
			log.info("CVPProcessor :: parse :: start() ");
			CVPBasePacket cvpBasePacket = processorHelper.buildPackets(inputData);
			if (cvpBasePacket != null) {
				// peristRawData(inputData,cvpBasePacket);
				// persistDeviceDetails(cvpBasePacket);
				persistActivationDetails(cvpBasePacket);
				persistHealthDetails(cvpBasePacket);
				log.info("CVPProcessor :: parse :: cvpBasePacket " + cvpBasePacket);
				log.info("CVPProcessor :: parse :: inputData " + inputData);
				persistLMPPacket(cvpBasePacket, inputData);
				persistHMPPacket(cvpBasePacket, inputData);
				persistEBPPacket(cvpBasePacket, inputData);
				persistNRPPacket(cvpBasePacket, inputData);
			}
			log.info("CVPProcessor :: parse :: end() ");
		}

		private void persistHealthDetails(CVPBasePacket cvpBasePacket) {
			if (cvpBasePacket instanceof HealthPacket) {
				HealthPacket healthPacket = ((HealthPacket) cvpBasePacket);
				HealthPacketVO healthPacketVO = healthPacket.getHealthPacketVO();
				CreateHealthPacketDataEvent createHealthPacketDataEvent = new CreateHealthPacketDataEvent();
				createHealthPacketDataEvent.setHealthPacketVO(healthPacketVO);
				healthPacketDataService.save(createHealthPacketDataEvent);
			}
		}

		private void persistActivationDetails(CVPBasePacket cvpBasePacket) {
			if (cvpBasePacket instanceof ActivationPacket) {
				ActivationPacket activationPacket = ((ActivationPacket) cvpBasePacket);
				ActivationPacketVO activationPacketVO = activationPacket.getActivationPacketVO();
				CreateActivationPacketDataEvent createActivationPacketDataEvent = new CreateActivationPacketDataEvent();
				createActivationPacketDataEvent.setActivationPacketVO(activationPacketVO);
				activationPacketDataService.save(createActivationPacketDataEvent);
			}
		}

		// Need to add some more fields in ModemDetails
		private void persistDeviceDetails(CVPBasePacket cvpBasePacket) {
			log.info("CVPProcessor :: persistDeviceDetails :: start() ");
			DeviceDetailsVO modemDetailsVO = DeviceDetailsVO.builder().imeiNumber(cvpBasePacket.getImeiNumber())
					.simNumber(cvpBasePacket.getSimNumber()).ipAddress(cvpBasePacket.getIpAddress())
					.networkDate(cvpBasePacket.getNetworkDate()).networkTime(cvpBasePacket.getNetworkTime()).build();
			deviceDetailsService.save(modemDetailsVO);

			persistSIMDetails(cvpBasePacket);
			log.info("CVPProcessor :: persistDeviceDetails :: end() ");
		}

		private void persistSIMDetails(CVPBasePacket cvpBasePacket) {
			log.info("CVPProcessor :: persistSIMDetails :: start() ");
			SIMDetailsVO simDetailsVO = SIMDetailsVO.builder().telecomProvider(cvpBasePacket.getNetworkOperatorName())
					.status("Active").imsiNumber(cvpBasePacket.getImsi()).imeiNumber(cvpBasePacket.getImeiNumber())
					.build();
			simDetailsService.save(simDetailsVO);
			log.info("CVPProcessor :: persistSIMDetails :: end() ");
		}

		private void peristRawData(String inputData, CVPBasePacket cvpBasePacket) {
			log.info("CVPProcessor :: peristRawData :: start() ");
			log.info("CVPProcessor :: peristRawData :: inputData  " + inputData);
			log.info("CVPProcessor :: peristRawData :: cvpBasePacket  " + cvpBasePacket);

			RawDataPacketVO dataPacketVO = RawDataPacketVO.builder().rawData(inputData)
					.networkDate(cvpBasePacket.getNetworkDate()).networkTime(cvpBasePacket.getNetworkTime())
					.imeiNumber(cvpBasePacket.getImeiNumber()).vehicleRegNo(cvpBasePacket.getVehicleRegNo())
					.cvpType(cvpBasePacket.getPacketType().name()).createdDate(DateUitls.getCurrentSystemTimestamp())
					.inBoundChannel("TCP").packetStatus(cvpBasePacket.getPacketStatus())
					.packetType(cvpBasePacket.getPcktType()).vehicleModeId(cvpBasePacket.getVehicleModeId()).build();
			log.info("CVPProcessor :: peristRawData :: RawDataPacketVO  " + dataPacketVO);
			rawDataPacketService.save(dataPacketVO);

			log.info("CVPProcessor :: peristRawData :: END() ");

		}

		private void persistLMPPacket(CVPBasePacket cvpBasePacket, String inputData) {
			log.info("CVPProcessor :: persistLMPPacket :: start() ");
			log.info("CVPProcessor :: persistLMPPacket :: cvpBasePacket() " + cvpBasePacket);
			if (cvpBasePacket instanceof LMPPacket) {
				LMPPacket lmpPacket = ((LMPPacket) cvpBasePacket);
				LMPPacketDataVO lmpPacketDataVO = lmpPacket.getLmpPacketDataVO();
				lmpPacketDataVO.setNetworkDate(cvpBasePacket.getNetworkDate());
				lmpPacketDataVO.setNetworkTime(cvpBasePacket.getNetworkTime());
				CreateLMPPacketDataEvent createLMPPacketDataEvent = new CreateLMPPacketDataEvent();
				createLMPPacketDataEvent.setLMPPacketDataVO(lmpPacketDataVO);
				cvpBasePacket.setImeiNumber(lmpPacketDataVO.getImei());
				cvpBasePacket.setVehicleRegNo(lmpPacketDataVO.getVehicleRegNo());
				cvpBasePacket.setNetworkOperatorName(lmpPacketDataVO.getNetworkOperatorName());
				cvpBasePacket.setPacketStatus(lmpPacketDataVO.getPacketStatus());
				cvpBasePacket.setPcktType(lmpPacketDataVO.getPacketType());
				cvpBasePacket.setVehicleModeId(lmpPacketDataVO.getVehicleModeId());
				log.info("CVPProcessor :: persistLMPPacket :: cvpBasePacket() " + cvpBasePacket);
				peristRawData(inputData, cvpBasePacket);
				persistDeviceDetails(cvpBasePacket);
				lmpPacketDataService.save(createLMPPacketDataEvent);
				persistDeviceCommunication(lmpPacketDataVO);
				persistTripTracking(lmpPacketDataVO);
			}
			log.info("CVPProcessor :: persistLMPPacket :: end() ");
		}

		private void persistHMPPacket(CVPBasePacket cvpBasePacket, String inputData) {
			log.info("CVPProcessor :: persistHMPPacket :: start() ");
			if (cvpBasePacket instanceof HMPPacket) {
				HMPPacket hmpPacket = ((HMPPacket) cvpBasePacket);
				HMPPacketDataVO hmpPacketDataVO = hmpPacket.getHmpPacketDataVO();
				hmpPacketDataVO.setNetworkDate(cvpBasePacket.getNetworkDate());
				hmpPacketDataVO.setNetworkTime(cvpBasePacket.getNetworkTime());
				CreateHMPPacketDataEvent createHMPPacketDataEvent = new CreateHMPPacketDataEvent();
				cvpBasePacket.setImeiNumber(hmpPacketDataVO.getImeiNumber());
				// HMP Packet - PacketStaus is not available setting to empty
				cvpBasePacket.setPacketStatus("");
				peristRawData(inputData, cvpBasePacket);
				createHMPPacketDataEvent.setHMPPacketDataVO(hmpPacketDataVO);
				hmpPacketDataService.save(createHMPPacketDataEvent);
				persistDeviceCommunicationHmpPacket(hmpPacketDataVO);
			}
			log.info("CVPProcessor :: persistHMPPacket :: end () ");
		}

		private void persistEBPPacket(CVPBasePacket cvpBasePacket, String inputData) {

			if (cvpBasePacket instanceof EBPPacket) {
				EBPPacket ebpPacket = ((EBPPacket) cvpBasePacket);
				EBPPacketDataVO ebpPacketDataVO = ebpPacket.getEbpPacketDataVO();
				ebpPacketDataVO.setNetworkDate(cvpBasePacket.getNetworkDate());
				ebpPacketDataVO.setNetworkTime(cvpBasePacket.getNetworkTime());
				cvpBasePacket.setImeiNumber(ebpPacketDataVO.getImeiNumber());
				cvpBasePacket.setVehicleRegNo(ebpPacketDataVO.getVehicleRegNo());
				cvpBasePacket.setPacketStatus(ebpPacketDataVO.getPacketStaus());
				peristRawData(inputData, cvpBasePacket);
				CreateEBPPacketDataEvent createEBPPacketDataEvent = new CreateEBPPacketDataEvent();
				createEBPPacketDataEvent.setEBPPacketDataVO(ebpPacketDataVO);
				ebpPacketDataService.save(createEBPPacketDataEvent);
			}
		}
		
		
		private void persistNRPPacket(CVPBasePacket cvpBasePacket, String inputData) {

			if (cvpBasePacket instanceof NRPPacket) {
				NRPPacket nrpPacket = ((NRPPacket) cvpBasePacket);
				NRPPacketDataVO nrpPacketDataVO = nrpPacket.getNrpPacketDataVO();
				nrpPacketDataVO.setNetworkDate(cvpBasePacket.getNetworkDate());
				nrpPacketDataVO.setNetworkTime(cvpBasePacket.getNetworkTime());
				cvpBasePacket.setImeiNumber(nrpPacketDataVO.getImeiNumber());
				cvpBasePacket.setVehicleRegNo(nrpPacketDataVO.getVehicleRegNo());
				cvpBasePacket.setPacketStatus(nrpPacketDataVO.getPacketStaus());
				peristRawData(inputData, cvpBasePacket);
				CreateNRPPacketDataEvent createNRPPacketDataEvent = new CreateNRPPacketDataEvent();
				createNRPPacketDataEvent.setNrpPacketDataVO(nrpPacketDataVO);
				nrpPacketDataService.save(createNRPPacketDataEvent);
			}
		}

		private void persistDeviceCommunication(LMPPacketDataVO lmpPacketDataVO) {
			String latitude = convertLatitude(lmpPacketDataVO.getLatitude());
			String langitude = convertLangitude(lmpPacketDataVO.getLongitude());

			String location = Geocoder.getFormattedAddress(latitude + "," + langitude);

			DeviceCommunication deviceCommunctionDetails = deviceCommunicationRepository
					.getByImeiNumber(lmpPacketDataVO.getImei());
			
			if (deviceCommunctionDetails != null) {
				DeviceCommunicationHistoryVO deviceCommunicationHistoryVO = DeviceCommunicationHistoryVO.builder()
						.createdDate(DateUtils.getCurrentSystemTimestamp())
						.networkDate(deviceCommunctionDetails.getNetworkDate())
						.networkTime(deviceCommunctionDetails.getNetworkTime())
						.latitude(deviceCommunctionDetails.getLatitude())
						.langitude(deviceCommunctionDetails.getLangitude())
						.location(deviceCommunctionDetails.getLocation())
						.prevLangitude(deviceCommunctionDetails.getPrevLangitude())
						.prevLatitude(deviceCommunctionDetails.getPrevLatitude())
						.prevNetworkDate(deviceCommunctionDetails.getPrevNetworkDate())
						.prevNetworkTime(deviceCommunctionDetails.getPrevNetworkTime())
						.imeiNumber(deviceCommunctionDetails.getImeiNumber())
						.status(deviceCommunctionDetails.getStatus())
						.ignitionStatus(deviceCommunctionDetails.getIgnitionStatus())
						.vehicleRegNo(deviceCommunctionDetails.getVehicleRegNo())
						.speed(deviceCommunctionDetails.getSpeed())
						.emergencyStatus(deviceCommunctionDetails.getEmergencyStatus())
						.mainPowerStatus(deviceCommunctionDetails.getMainPowerStatus())
						.internalBatteryVoltage(deviceCommunctionDetails.getInternalBatteryVoltage())
						.gsmSignalStrength(deviceCommunctionDetails.getGsmSignalStrength())
						.tamperAlert(deviceCommunctionDetails.getTamperAlert())
						.batteryPercentage(deviceCommunctionDetails.getBatteryPercentage())
						.lowBatteryThreshold(deviceCommunctionDetails.getLowBatteryThreshold())
						.memoryPercentage(deviceCommunctionDetails.getMemoryPercentage())
						.vehicleMode(deviceCommunctionDetails.getVehicleMode())
						.vehicleModeId(deviceCommunctionDetails.getVehicleModeId())
						.engineStatus(deviceCommunctionDetails.getEngineStatus())
						.build();
				deviceCommunicationService.saveHistory(deviceCommunicationHistoryVO);
			}

			DeviceCommunicationVO deviceCommunicationVO = new DeviceCommunicationVO();
			deviceCommunicationVO.setNetworkDate(lmpPacketDataVO.getNetworkDate());
			deviceCommunicationVO.setNetworkTime(lmpPacketDataVO.getNetworkTime());
			deviceCommunicationVO.setLatitude(latitude);
			deviceCommunicationVO.setLangitude(langitude);
			deviceCommunicationVO.setLocation(location);
			deviceCommunicationVO.setImeiNumber(lmpPacketDataVO.getImei());
			deviceCommunicationVO.setIgnitionStatus(lmpPacketDataVO.getIgnition());
			deviceCommunicationVO.setTamperAlert(lmpPacketDataVO.getTamperAlert());
			deviceCommunicationVO.setVehicleRegNo(lmpPacketDataVO.getVehicleRegNo());
			deviceCommunicationVO.setSpeed(lmpPacketDataVO.getSpeed());
			deviceCommunicationVO.setEmergencyStatus(lmpPacketDataVO.getEmergencyStatus());
			deviceCommunicationVO.setMainPowerStatus(lmpPacketDataVO.getMainPowerStatus());
			deviceCommunicationVO.setInternalBatteryVoltage(lmpPacketDataVO.getInternalBatteryVoltage());
			deviceCommunicationVO.setGsmSignalStrength(lmpPacketDataVO.getGsmSignalStrength());
			deviceCommunicationVO.setTamperAlert(lmpPacketDataVO.getTamperAlert());
			deviceCommunicationVO.setVehicleMode(lmpPacketDataVO.getVehicleMode());
			deviceCommunicationVO.setVehicleModeId(lmpPacketDataVO.getVehicleModeId());
			deviceCommunicationVO.setEngineStatus(lmpPacketDataVO.getIgnition());  //Saving engineStatus as ignitionsStatus
			// For the newly added device we are setting the default values.
			if (ObjectUtils.isEmpty(deviceCommunctionDetails)) {
				deviceCommunicationVO.setBatteryPercentage(0.00);
				deviceCommunicationVO.setMemoryPercentage(0.00);
			}
			if (deviceCommunctionDetails != null) {
				deviceCommunicationVO.setBatteryPercentage(deviceCommunctionDetails.getBatteryPercentage());
				deviceCommunicationVO.setMemoryPercentage(deviceCommunctionDetails.getMemoryPercentage());

			}
			deviceCommunicationService.save(deviceCommunicationVO);
		}

		private void persistDeviceCommunicationHmpPacket(HMPPacketDataVO hmpPacketDataVO) {
			DeviceCommunicationVO deviceCommunicationVO = DeviceCommunicationVO.builder()
					.imeiNumber(hmpPacketDataVO.getImeiNumber())
					.batteryPercentage(!StringUtils.isEmpty(hmpPacketDataVO.getBatteryPercentage())
							? Double.parseDouble(hmpPacketDataVO.getBatteryPercentage())
							: 0.0)
					.memoryPercentage(!StringUtils.isEmpty(hmpPacketDataVO.getMemoryPercentage())
							? Double.parseDouble(hmpPacketDataVO.getMemoryPercentage())
							: 0.0)
					.build();
			deviceCommunicationService.saveHMPPacketData(deviceCommunicationVO);
		}

		private void persistTripTracking(LMPPacketDataVO lmpPacketDataVO) {
			String latitude = convertLatitude(lmpPacketDataVO.getLatitude());
			String longitude = convertLangitude(lmpPacketDataVO.getLongitude());
			String location = Geocoder.getFormattedAddress(latitude + "," + longitude);

			TripTrackingVO tripTrackingVO = TripTrackingVO.builder().createdDate(DateUtils.getCurrentSystemTimestamp())
					.imeiNumber(lmpPacketDataVO.getImei()).latitude(latitude).longitude(longitude).location(location)
					.latitudeDirection(lmpPacketDataVO.getLatitudeDirection())
					.longitudeDirection(lmpPacketDataVO.getLongitudeDirection())
					.networkDate(lmpPacketDataVO.getNetworkDate()).networkTime(lmpPacketDataVO.getNetworkTime())
					.build();
			tripTrackingService.save(tripTrackingVO);
		}

		private String convertLatitude(String latitude) {
			DecimalFormat numberFormat = new DecimalFormat("##.000000");
			latitude = latitude.replaceAll("([A-Z a-z])", "");
			return numberFormat.format(
					(Double.parseDouble(latitude.substring(0, 2)) + (Double.parseDouble(latitude.substring(2)) / 60)));
		}

		private String convertLangitude(String langitude) {
			DecimalFormat numberFormat = new DecimalFormat("##.000000");
			langitude = langitude.replaceAll("([A-Z a-z])", "");
			if (langitude.startsWith("0")) {
				langitude = langitude.replaceFirst("0", "");
			}
			return numberFormat.format((Double.parseDouble(langitude.substring(0, 2))
					+ (Double.parseDouble(langitude.substring(2)) / 60)));
		}
	}
}
