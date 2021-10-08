package com.bhavath.tracker.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.bhavath.tracker.data.model.HMPPacketData;
import com.bhavath.tracker.data.repos.HMPPacketDataRepository;
import com.bhavath.tracker.data.repos.HMPPacketDataSpecifications;
import com.bhavath.tracker.events.CreateHMPPacketDataEvent;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadHMPPacketDataSetEvent;
import com.bhavath.tracker.util.ObjectMapperUtils;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.HMPPacketDataVO;

import lombok.extern.slf4j.Slf4j;

public interface HMPPacketDataService {
	
	public void save(CreateHMPPacketDataEvent createHMPPacketDataEvent);
	public PageReadEvent<HMPPacketDataVO> readData(ReadHMPPacketDataSetEvent request);
	
	@Service
	@Slf4j
	public class impl implements HMPPacketDataService{
		
		@Autowired private HMPPacketDataRepository hmpPacketDataRepository;
		
		@Override
		public void save(CreateHMPPacketDataEvent createHMPPacketDataEvent) {
			
			HMPPacketDataVO hmpPacketDataVO = createHMPPacketDataEvent.getHMPPacketDataVO();
			HMPPacketData hmpPacketData = HMPPacketData.builder()
					.vendorId(hmpPacketDataVO.getVendorId())
					.createdDate(DateUtils.getCurrentSystemTimestamp())
					.firmwareVersion(hmpPacketDataVO.getFirmwareVersion())
					.imeiNumber(hmpPacketDataVO.getImeiNumber())
					.batteryPercentage(hmpPacketDataVO.getBatteryPercentage())
					.lowBatteryThrValue(hmpPacketDataVO.getLowBatteryThrValue())
					.memoryPercentage(hmpPacketDataVO.getMemoryPercentage())
					.dataUpdateON(hmpPacketDataVO.getDataUpdateON())
					.dataUpdateOFF(hmpPacketDataVO.getDataUpdateOFF())
					.digitalIOStatus(hmpPacketDataVO.getDigitalIOStatus())
					.analogIOStatus(hmpPacketDataVO.getAnalogIOStatus())
					.endOfPacket(hmpPacketDataVO.getEndOfPacket())
					.networkDate(hmpPacketDataVO.getNetworkDate())
					.networkTime(hmpPacketDataVO.getNetworkTime())
					.build();
			hmpPacketDataRepository.save(hmpPacketData);
			hmpPacketDataRepository.flush();
		}
		
		@Override
		public PageReadEvent<HMPPacketDataVO> readData(ReadHMPPacketDataSetEvent request) 
		{
			Page<HMPPacketData> dbContent = hmpPacketDataRepository.findAll(new HMPPacketDataSpecifications(request.getImeiNumber(),request.getStartDate(),request.getEndDate()),
					HMPPacketDataSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize()));
			
			List<HMPPacketDataVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), HMPPacketDataVO.class);
			Page<HMPPacketDataVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}
		
	}
	
}
