package com.bhavath.tracker.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.bhavath.tracker.constants.Constants;
import com.bhavath.tracker.data.model.DeviceDetails;
import com.bhavath.tracker.data.model.SIMDetails;
import com.bhavath.tracker.data.repos.DeviceDetailsRepository;
import com.bhavath.tracker.data.repos.DeviceDetailsSpecifications;
import com.bhavath.tracker.data.repos.SIMDetailsRepository;
import com.bhavath.tracker.events.CreateDeviceDetEvent;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadDeviceDetailsEvent;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.util.ObjectMapperUtils;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.DeviceDetailsVO;
import com.bhavath.tracker.vos.ResponseVO;

import lombok.extern.slf4j.Slf4j;

public interface DeviceDetailsService {
	public void save(DeviceDetailsVO deviceDetailsVO);
	public ResponseVO save(CreateDeviceDetEvent deviceDetailsVO);
	public DeviceDetails getModemBySerialNumber(String serialNumber);

	public PageReadEvent<DeviceDetailsVO> readModemDeviceData(ReadDeviceDetailsEvent request);

	@Service
	@Slf4j
	public class impl implements DeviceDetailsService {
		@Autowired
		private DeviceDetailsRepository deviceDetailsRepository;
		@Autowired
		private SIMDetailsRepository simDetailsRepository;
		
		@Override
		public ResponseVO save(CreateDeviceDetEvent event) {
			log.info("DeviceDetailsService :: save :: start() ");
			ResponseVO responseVO = new ResponseVO();
			DeviceDetails deviceDetails = null;
			SIMDetails simDetails = null;
			DeviceDetailsVO deviceDetailsVO = event.getDeviceDetailsVO();
			log.info("Device Details :::: save ::: start()");
			try {
				deviceDetails = deviceDetailsRepository.findByImeiNumber(deviceDetailsVO.getImeiNumber());
				if (deviceDetails == null) {
					deviceDetails = new DeviceDetails();
					deviceDetails.setCreatedDate(DateUitls.getCurrentSystemTimestamp());
				}
				deviceDetails.setImeiNumber(deviceDetailsVO.getImeiNumber());
				deviceDetails.setSimNumber(deviceDetailsVO.getSimNumber());
				deviceDetails.setUpdatedDate(DateUitls.getCurrentSystemTimestamp());
				deviceDetails.setSerialNumber(deviceDetailsVO.getSerialNumber());
				deviceDetails.setVehicleRegNo(deviceDetailsVO.getVehicleRegNo());
				
				deviceDetailsRepository.save(deviceDetails);
				deviceDetailsRepository.flush();
				log.info("Device Details :::: save ::: end()");
				
				log.info("Sim Details :::: save ::: start()");
				simDetails = simDetailsRepository.findByImsiNumber(deviceDetailsVO.getImsiNumber());
				if(ObjectUtils.isEmpty(simDetails)) {
					simDetails = new SIMDetails();
					simDetails.setCreatedDate(DateUtils.getCurrentSystemTimestamp());
				}
				simDetails.setImsiNumber(deviceDetailsVO.getImsiNumber());
				simDetails.setTelecomProvider(deviceDetailsVO.getTelecomProvider());
				simDetails.setMobileNumber(deviceDetailsVO.getMobileNumber());
				simDetails.setDeviceDetails(deviceDetails);
				simDetailsRepository.save(simDetails);
				simDetailsRepository.flush();
				responseVO.setCode(Constants.ResponseMessages.CODE_200);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
				log.info("Sim Details :::: save ::: end()");
			} catch (Exception e) {
				log.info("Exception while Saving New Device Details :: " + e.getCause());
				responseVO.setCode(Constants.ResponseMessages.CODE_500);
				responseVO.setMessage("Unable to save new device details..");
			}
			log.info("DeviceDetailsService :: save :: end() ");
			return responseVO;
		}

		@Override
		public void save(DeviceDetailsVO deviceDetailsVO) {
			log.info("DeviceDetailsService :: save :: start() ");
			try {
				DeviceDetails deviceDetails = deviceDetailsRepository.findByImeiNumber(deviceDetailsVO.getImeiNumber());
				if (deviceDetails == null) {
					deviceDetails = new DeviceDetails();
					deviceDetails.setCreatedDate(DateUitls.getCurrentSystemTimestamp());
				}
				deviceDetails.setImeiNumber(deviceDetailsVO.getImeiNumber());
				deviceDetails.setSimNumber(deviceDetailsVO.getSimNumber());
				deviceDetails.setIpAddress(deviceDetailsVO.getIpAddress());
				deviceDetails.setUpdatedDate(DateUitls.getCurrentSystemTimestamp());
				deviceDetails.setVersion(deviceDetailsVO.getVersion());
				deviceDetails.setNetworkDate(deviceDetailsVO.getNetworkDate());
				deviceDetails.setNetworkTime(deviceDetailsVO.getNetworkTime());

				deviceDetailsRepository.save(deviceDetails);
				deviceDetailsRepository.flush();
			} catch (Exception e) {
				log.info("DeviceDetailsService :: save :: exception() ",e);
				log.info("Exception while Saving Device Details :: " + e.getCause());
			}
			log.info("DeviceDetailsService :: save :: end() ");
		}

		@Override
		public DeviceDetails getModemBySerialNumber(String serialNumber) {
			return deviceDetailsRepository.findBySerialNumber(serialNumber);
		}

		@Override
		public PageReadEvent<DeviceDetailsVO> readModemDeviceData(ReadDeviceDetailsEvent request) {
			Page<DeviceDetails> dbContent = deviceDetailsRepository.findAll(
					new DeviceDetailsSpecifications(request.getImeiNumber(), request.getSearchValue(),request.getSearchDate()),
					DeviceDetailsSpecifications.constructPageSpecification(request.getPageable().getPageNumber(),
							request.getPageable().getPageSize()));
			List<DeviceDetailsVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), DeviceDetailsVO.class);
			Page<DeviceDetailsVO> page = new PageImpl<>(content, request.getPageable(),
					dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}
	}
}
