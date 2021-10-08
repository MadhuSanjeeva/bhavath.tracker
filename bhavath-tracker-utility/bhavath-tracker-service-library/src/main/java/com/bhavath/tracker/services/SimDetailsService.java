package com.bhavath.tracker.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.constants.Constants;
import com.bhavath.tracker.data.model.DeviceDetails;
import com.bhavath.tracker.data.model.SIMDetails;
import com.bhavath.tracker.data.repos.DeviceDetailsRepository;
import com.bhavath.tracker.data.repos.DeviceDetailsSpecifications;
import com.bhavath.tracker.data.repos.DeviceDetailsViewSpecifications;
import com.bhavath.tracker.data.repos.SIMDetailsRepository;
import com.bhavath.tracker.events.CreateSIMDetailsEvent;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadSIMDetailsEvent;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.DeviceDetailsVO;
import com.bhavath.tracker.vos.ResponseVO;
import com.bhavath.tracker.vos.SIMDetailsVO;

import lombok.extern.slf4j.Slf4j;

public interface SimDetailsService {
	public void save(SIMDetailsVO simDetailsVO);

	public ResponseVO save(CreateSIMDetailsEvent event);

	public PageReadEvent<DeviceDetailsVO> readData(ReadSIMDetailsEvent request);

	@Service
	@Slf4j
	public class impl implements SimDetailsService {
		@Autowired
		private DeviceDetailsRepository deviceDetailsRepository;
		@Autowired
		private SIMDetailsRepository simDetailsRepository;

		@Override
		public void save(SIMDetailsVO simDetailsVO) {
			log.info("SimDetailsService :: save :: start() ");
			try {
				String imsiNumber = simDetailsVO.getImsiNumber();
				imsiNumber = StringUtils.isEmpty(imsiNumber) ? imsiNumber : imsiNumber.replaceAll("\"", "");
				String telecomProvider = simDetailsVO.getTelecomProvider();
				telecomProvider = StringUtils.isEmpty(telecomProvider) ? telecomProvider : telecomProvider.replaceAll("\"", "");
				SIMDetails simDetails = simDetailsRepository.findByImsiNumber(imsiNumber);
				DeviceDetails deviceDetails = deviceDetailsRepository.findByImeiNumber(simDetailsVO.getImeiNumber());
				if (ObjectUtils.isEmpty(simDetails)) {
					simDetails = new SIMDetails();
					simDetails.setCreatedDate(DateUitls.getCurrentSystemTimestamp());
				}
				simDetails.setTelecomProvider(telecomProvider);
				simDetails.setStatus(simDetailsVO.getStatus());
				simDetails.setImsiNumber(imsiNumber);
				simDetails.setDeviceDetails(deviceDetails);
				simDetailsRepository.saveAndFlush(simDetails);
			} catch (Exception e) {
				log.info("SimDetailsService :: save :: :: exception() ", e);
				log.info("Exception while Saving SIM Details :: " + e.getCause());
			}
			log.info("SimDetailsService :: save :: :: end() ");
		}

		@Override
		public ResponseVO save(CreateSIMDetailsEvent event) {
			log.info("SimDetailsService :::: save ::: start()");
			ResponseVO responseVO = new ResponseVO();
			try {
				List<SIMDetailsVO> list = event.getSimDetailsVO();
				log.info("SimDetailsService :::: save ::: "+list.get(0).getImeiNumber());
				
				DeviceDetails deviceDetails = deviceDetailsRepository.findByImeiNumber(list.get(0).getImeiNumber());
				log.info("SimDetailsService :::: save ::: "+deviceDetails);

				simDetailsRepository.deleteAll(simDetailsRepository.getDeviceDetails(deviceDetails));

				List<SIMDetails> result = list.stream().map(temp -> {
					SIMDetails sim = SIMDetails.builder()
							.deviceDetails(deviceDetails)
							.createdDate(DateUtils.getCurrentSystemTimestamp())
							.mobileNumber(temp.getMobileNumber())
							.imsiNumber(temp.getImsiNumber())
							.status(temp.getStatus())
							.telecomProvider(temp.getTelecomProvider())
							.build();
					return sim;
				}).collect(Collectors.toList());

				simDetailsRepository.saveAll(result);

				responseVO.setCode(Constants.ResponseMessages.CODE_200);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
				log.info("SimDetailsService :::: save ::: end()");
			} catch (Exception e) {
				responseVO.setCode(Constants.ResponseMessages.CODE_500);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_500 + " " + e.getMessage());
				log.info("Exception while saving SIMDetails :: " + e.getCause(), e);
			}
			return responseVO;
		}

		@Override
		public PageReadEvent<DeviceDetailsVO> readData(ReadSIMDetailsEvent request) {
			Page<DeviceDetails> dbContent = deviceDetailsRepository.findAll(
					new DeviceDetailsSpecifications(request.getImeiNumber(),request.getSearchValue(), request.getSearchDate()),
					DeviceDetailsViewSpecifications.constructPageSpecification(request.getPageable().getPageNumber(),
							request.getPageable().getPageSize()));
			List<DeviceDetailsVO> content = new ArrayList<>();
			for (DeviceDetails record : dbContent) {
				ModelMapper modelMapper = new ModelMapper();
				DeviceDetailsVO details = modelMapper.map(record, DeviceDetailsVO.class);
				List<SIMDetailsVO> simDetailsVOS = modelMapper.map(record.getSimDetails(), List.class);
				details.setSimDetails(simDetailsVOS);
				content.add(details);
			}
			Page<DeviceDetailsVO> page = new PageImpl<>(content, request.getPageable(),
					dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}

	}
}
