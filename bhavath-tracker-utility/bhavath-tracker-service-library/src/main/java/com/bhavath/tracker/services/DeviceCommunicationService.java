package com.bhavath.tracker.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.data.model.DeviceCommunication;
import com.bhavath.tracker.data.model.DeviceCommunicationHistory;
import com.bhavath.tracker.data.model.SystemProperties;
import com.bhavath.tracker.data.repos.DeviceCommunicationHistoryRepository;
import com.bhavath.tracker.data.repos.DeviceCommunicationHistorySpecifications;
import com.bhavath.tracker.data.repos.DeviceCommunicationRepository;
import com.bhavath.tracker.data.repos.DeviceCommunicationSpecifications;
import com.bhavath.tracker.data.repos.DeviceCommunicationSummarySQLRepository;
import com.bhavath.tracker.data.repos.SystemPropertiesRepository;
import com.bhavath.tracker.enums.SystemPropertiesEnum;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadDeviceCommunicationEvent;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.util.ObjectMapperUtils;
import com.bhavath.tracker.utils.DeviceCommunicationSummaryVO;
import com.bhavath.tracker.utils.DeviceCommunicationVehicleModeSummaryVO;
import com.bhavath.tracker.vos.DeviceCommunicationHistoryVO;
import com.bhavath.tracker.vos.DeviceCommunicationVO;

import lombok.extern.slf4j.Slf4j;

public interface DeviceCommunicationService {
    public void save(DeviceCommunicationVO deviceCommunication);
    public void saveHistory(DeviceCommunicationHistoryVO deviceCommunicationHistoryVO);
    public PageReadEvent<DeviceCommunicationVO> readData(ReadDeviceCommunicationEvent request);
    public PageReadEvent<DeviceCommunicationVO> readDeviceCommHistoryData(ReadDeviceCommunicationEvent request);
    public List<DeviceCommunicationSummaryVO> readSummary(String[] actions);
    public List<DeviceCommunicationVehicleModeSummaryVO> readVehicleModeSummary();
    public void saveHMPPacketData(DeviceCommunicationVO deviceCommunicationVO);

    @Service
    @Slf4j
    public class impl implements DeviceCommunicationService {
        @Autowired
        private DeviceCommunicationRepository deviceCommunicationRepository;
        @Autowired
        private DeviceCommunicationHistoryRepository deviceCommunicationHistoryRepository;

        @Autowired
        private DeviceCommunicationSummarySQLRepository deviceCommunicationSummarySQLRepository;
        
        @Autowired
        private SystemPropertiesRepository systemPropertiesRepository;

        @Override
        public void save(DeviceCommunicationVO deviceCommunicationVO) {
        	
        	log.info("DeviceCommunicationVO in save method : "+deviceCommunicationVO);
            if (!StringUtils.isEmpty(deviceCommunicationVO.getImeiNumber())) {
                try {
                    DeviceCommunication deviceCommunctionDetails = deviceCommunicationRepository.getByImeiNumber(deviceCommunicationVO.getImeiNumber());
                    SystemProperties systemProperties = systemPropertiesRepository.findByPropertyName(SystemPropertiesEnum.SPEED.getType());
                    if (deviceCommunctionDetails == null) {
                        deviceCommunctionDetails = new DeviceCommunication();
                        deviceCommunctionDetails.setImeiNumber(deviceCommunicationVO.getImeiNumber());
                        deviceCommunctionDetails.setCreatedDate(DateUitls.getCurrentSystemTimestamp());
                    }
                    deviceCommunctionDetails.setUpdatedDate(DateUitls.getCurrentSystemTimestamp());
                    deviceCommunctionDetails.setPrevNetworkDate(!StringUtils.isEmpty(deviceCommunctionDetails.getNetworkDate()) ? deviceCommunctionDetails.getNetworkDate() : deviceCommunicationVO.getNetworkDate());
                    deviceCommunctionDetails.setPrevNetworkTime(!StringUtils.isEmpty(deviceCommunctionDetails.getNetworkTime()) ? deviceCommunctionDetails.getNetworkTime() : deviceCommunicationVO.getNetworkTime());
                    deviceCommunctionDetails.setPrevLangitude(!StringUtils.isEmpty(deviceCommunctionDetails.getLangitude()) ? deviceCommunctionDetails.getLangitude() : deviceCommunicationVO.getLangitude());
                    deviceCommunctionDetails.setPrevLatitude(!StringUtils.isEmpty(deviceCommunctionDetails.getLatitude()) ? deviceCommunctionDetails.getLatitude() : deviceCommunicationVO.getLatitude());

                    deviceCommunctionDetails.setNetworkDate(deviceCommunicationVO.getNetworkDate());
                    deviceCommunctionDetails.setNetworkTime(deviceCommunicationVO.getNetworkTime());
                    deviceCommunctionDetails.setLangitude(deviceCommunicationVO.getLangitude());
                    deviceCommunctionDetails.setLatitude(deviceCommunicationVO.getLatitude());
                    deviceCommunctionDetails.setLocation(deviceCommunicationVO.getLocation());
                    deviceCommunctionDetails.setStatus("Communicating");
                    deviceCommunctionDetails.setIgnitionStatus(deviceCommunicationVO.getIgnitionStatus());
                    deviceCommunctionDetails.setVehicleRegNo(deviceCommunicationVO.getVehicleRegNo());
                    deviceCommunctionDetails.setSpeed(deviceCommunicationVO.getSpeed());
                    deviceCommunctionDetails.setEmergencyStatus(deviceCommunicationVO.getEmergencyStatus());
                    deviceCommunctionDetails.setMainPowerStatus(deviceCommunicationVO.getMainPowerStatus());
                    deviceCommunctionDetails.setInternalBatteryVoltage(deviceCommunicationVO.getInternalBatteryVoltage());
                    deviceCommunctionDetails.setGsmSignalStrength(deviceCommunicationVO.getGsmSignalStrength());
                    deviceCommunctionDetails.setTamperAlert(deviceCommunicationVO.getTamperAlert());
                    deviceCommunctionDetails.setVehicleMode(deviceCommunicationVO.getVehicleMode());
                    deviceCommunctionDetails.setVehicleModeId(deviceCommunicationVO.getVehicleModeId());
                    deviceCommunctionDetails.setBatteryPercentage(deviceCommunicationVO.getBatteryPercentage());
                    deviceCommunctionDetails.setMemoryPercentage(deviceCommunicationVO.getMemoryPercentage());
                    deviceCommunctionDetails.setIsDeviceOverSpeed(Boolean.FALSE);
                    deviceCommunctionDetails.setBatteryPercentage(deviceCommunicationVO.getBatteryPercentage());
                    deviceCommunctionDetails.setMemoryPercentage(deviceCommunicationVO.getMemoryPercentage());
                    deviceCommunctionDetails.setEngineStatus(deviceCommunicationVO.getEngineStatus());
                    if(!ObjectUtils.isEmpty(systemProperties)) {
                    	if(Double.parseDouble(deviceCommunctionDetails.getSpeed()) > Double.parseDouble(systemProperties.getPropertyValue())) {
                        	deviceCommunctionDetails.setIsDeviceOverSpeed(Boolean.TRUE);
                        }
                    }
                    deviceCommunicationRepository.save(deviceCommunctionDetails);
                } catch (Exception e) {
                    log.info("Exception while saving DeviceCommunicationService Details :: " + e.getCause(), e);
                }
            } else {
                log.info("ImeiNumber is not available while saving DeviceCommunication");
            }
        }

        @Override
        public void saveHistory(DeviceCommunicationHistoryVO deviceCommunicationHistoryVO) {
            try {
                DeviceCommunicationHistory deviceCommunicationHistory = new DeviceCommunicationHistory();
                deviceCommunicationHistory.setCreatedDate(DateUitls.getCurrentSystemTimestamp());
                deviceCommunicationHistory.setIgnitionStatus(deviceCommunicationHistoryVO.getIgnitionStatus());
                deviceCommunicationHistory.setImeiNumber(deviceCommunicationHistoryVO.getImeiNumber());
                deviceCommunicationHistory.setLangitude(deviceCommunicationHistoryVO.getLangitude());
                deviceCommunicationHistory.setLatitude(deviceCommunicationHistoryVO.getLatitude());
                deviceCommunicationHistory.setLocation(deviceCommunicationHistoryVO.getLocation());
                deviceCommunicationHistory.setNetworkDate(deviceCommunicationHistoryVO.getNetworkDate());
                deviceCommunicationHistory.setNetworkTime(deviceCommunicationHistoryVO.getNetworkTime());
                deviceCommunicationHistory.setPrevLangitude(deviceCommunicationHistoryVO.getPrevLangitude());
                deviceCommunicationHistory.setPrevLatitude(deviceCommunicationHistoryVO.getPrevLatitude());
                deviceCommunicationHistory.setPrevNetworkDate(deviceCommunicationHistoryVO.getPrevNetworkDate());
                deviceCommunicationHistory.setPrevNetworkTime(deviceCommunicationHistoryVO.getPrevNetworkTime());
                deviceCommunicationHistory.setStatus(deviceCommunicationHistoryVO.getStatus());
                deviceCommunicationHistory.setVehicleRegNo(deviceCommunicationHistoryVO.getVehicleRegNo());
                deviceCommunicationHistory.setSpeed(deviceCommunicationHistoryVO.getSpeed());
                deviceCommunicationHistory.setEmergencyStatus(deviceCommunicationHistoryVO.getEmergencyStatus());
                deviceCommunicationHistory.setMainPowerStatus(deviceCommunicationHistoryVO.getMainPowerStatus());
                deviceCommunicationHistory.setInternalBatteryVoltage(deviceCommunicationHistoryVO.getInternalBatteryVoltage());
                deviceCommunicationHistory.setGsmSignalStrength(deviceCommunicationHistoryVO.getGsmSignalStrength());
                deviceCommunicationHistory.setTamperAlert(deviceCommunicationHistoryVO.getTamperAlert());
                deviceCommunicationHistory.setBatteryPercentage(deviceCommunicationHistoryVO.getBatteryPercentage());
                deviceCommunicationHistory.setLowBatteryThreshold(deviceCommunicationHistoryVO.getLowBatteryThreshold());
                deviceCommunicationHistory.setMemoryPercentage(deviceCommunicationHistoryVO.getMemoryPercentage());
                deviceCommunicationHistory.setVehicleMode(deviceCommunicationHistoryVO.getVehicleMode());
                deviceCommunicationHistory.setVehicleModeId(deviceCommunicationHistoryVO.getVehicleModeId());
                deviceCommunicationHistory.setEngineStatus(deviceCommunicationHistoryVO.getEngineStatus());
                deviceCommunicationHistoryRepository.save(deviceCommunicationHistory);
            } catch (Exception e) {
                log.info("Exception while saving DeviceCommunicationHistory Details :: " + e.getCause(), e);
            }
        }
        
		@Override
		public void saveHMPPacketData(DeviceCommunicationVO deviceCommunicationVO) {
			log.info("DeviceCommunicationService :: saveHMPPacketData :: start() ");
			if (!StringUtils.isEmpty(deviceCommunicationVO.getImeiNumber())) {
                try {
                    DeviceCommunication deviceCommunctionDetails = deviceCommunicationRepository.getByImeiNumber(deviceCommunicationVO.getImeiNumber());
                    if (deviceCommunctionDetails != null) {
                        deviceCommunctionDetails.setUpdatedDate(DateUitls.getCurrentSystemTimestamp());
                        deviceCommunctionDetails.setBatteryPercentage(deviceCommunicationVO.getBatteryPercentage());
                        deviceCommunctionDetails.setMemoryPercentage(deviceCommunicationVO.getMemoryPercentage());
                        deviceCommunicationRepository.save(deviceCommunctionDetails);
                    }
                } catch (Exception e) {
                    log.info("Exception while saving DeviceCommunicationService HMPPacketData Details :: " + e.getCause(), e);
                }
            } else {
                log.info("ImeiNumber is not available while saving DeviceCommunication HMPPacketData");
            }
			log.info("DeviceCommunicationService :: saveHMPPacketData :: end() ");
		}
        
        @Override
        public PageReadEvent<DeviceCommunicationVO> readData(ReadDeviceCommunicationEvent request) {
            DeviceCommunicationSpecifications specifications = new DeviceCommunicationSpecifications(request.getImeiNumber(),request.getStatus(),request.getSearchDate(),request.getVehicleMode(),
            																						request.getIgnitionStatus(),request.getEmergencyStatus(),request.getMainPowerStatus(),
            																						request.getTamperAlert(),request.getMemoryPercentage(),request.getBatteryPercentage());
            Page<DeviceCommunication> dbContent = deviceCommunicationRepository.findAll(specifications, DeviceCommunicationSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize()));
            List<DeviceCommunicationVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), DeviceCommunicationVO.class);
            Page<DeviceCommunicationVO> page = new PageImpl<>(content, request.getPageable(), dbContent != null ? dbContent.getTotalElements() : 0);
            return new PageReadEvent<>(page);
        }
        
        @Override
        public PageReadEvent<DeviceCommunicationVO> readDeviceCommHistoryData(ReadDeviceCommunicationEvent request) {
        	DeviceCommunicationHistorySpecifications specifications = new DeviceCommunicationHistorySpecifications(request.getImeiNumber(),request.getNetworkDate());
            Page<DeviceCommunicationHistory> dbContent = deviceCommunicationHistoryRepository.findAll(specifications, DeviceCommunicationHistorySpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize()));
            List<DeviceCommunicationVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), DeviceCommunicationVO.class);
            Page<DeviceCommunicationVO> page = new PageImpl<>(content, request.getPageable(), dbContent != null ? dbContent.getTotalElements() : 0);
            return new PageReadEvent<>(page);
        }

        @Override
        public List<DeviceCommunicationSummaryVO> readSummary(String[] actions) {
        	
            return deviceCommunicationSummarySQLRepository.readSummary(actions);
        }
        
        @Override
        public List<DeviceCommunicationVehicleModeSummaryVO> readVehicleModeSummary() {
        	
            return deviceCommunicationSummarySQLRepository.readVehicleModeSummary();
        }

    }
}
