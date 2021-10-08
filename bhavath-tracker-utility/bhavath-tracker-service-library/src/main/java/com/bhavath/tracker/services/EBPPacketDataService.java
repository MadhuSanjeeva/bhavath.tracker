package com.bhavath.tracker.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.bhavath.tracker.data.model.EBPPacketData;
import com.bhavath.tracker.data.repos.EBPPacketDataRepository;
import com.bhavath.tracker.data.repos.EBPPacketDataSpecifications;
import com.bhavath.tracker.events.CreateEBPPacketDataEvent;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadEBPPacketDataSetEvent;
import com.bhavath.tracker.util.CommonUtility;
import com.bhavath.tracker.util.ObjectMapperUtils;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.EBPPacketDataVO;

import lombok.extern.slf4j.Slf4j;

public interface EBPPacketDataService {
	
	public void save(CreateEBPPacketDataEvent CreateEBPPacketDataEvent);
	public PageReadEvent<EBPPacketDataVO> readData(ReadEBPPacketDataSetEvent request);
	
	@Service
	@Slf4j
	public class impl implements EBPPacketDataService{
		
		@Autowired private EBPPacketDataRepository ebpPacketDataRepository;
		
		@Override
		public void save(CreateEBPPacketDataEvent createEBPPacketDataEvent) {
			
			EBPPacketDataVO ebpPacketDataVO = createEBPPacketDataEvent.getEBPPacketDataVO();
			
			String latitude = CommonUtility.convertLatitude(ebpPacketDataVO.getLatitude());
			String longitude = CommonUtility.convertLangitude(ebpPacketDataVO.getLongitude());
			
			//Provider - for G - Fine GPS N – Coarse GPS or data from the network
			//Setting by default Provider - G
			EBPPacketData ebpPacketData = EBPPacketData.builder()
					.createdDate(DateUtils.getCurrentSystemTimestamp())
					.packetType(ebpPacketDataVO.getPacketType())
					.imeiNumber(ebpPacketDataVO.getImeiNumber())
					.packetStaus(ebpPacketDataVO.getPacketStaus())
					.timestamp(ebpPacketDataVO.getTimestamp())
					.gpsValidity(ebpPacketDataVO.getGpsValidity())
					.latitude(latitude)
					.latitudeDirection(ebpPacketDataVO.getLatitudeDirection())
					.longitude(longitude)
					.longitudeDirection(ebpPacketDataVO.getLongitudeDirection())
					.altitude(ebpPacketDataVO.getAltitude())
					.speed(ebpPacketDataVO.getSpeed())
					.distance(ebpPacketDataVO.getDistance())
					.provider("G")
					.vehicleRegNo(ebpPacketDataVO.getVehicleRegNo())
					.replyNumber(ebpPacketDataVO.getReplyNumber())
					.networkDate(ebpPacketDataVO.getNetworkDate())
					.networkTime(ebpPacketDataVO.getNetworkTime())
					.build();
			ebpPacketDataRepository.save(ebpPacketData);
			ebpPacketDataRepository.flush();
		}
		
		@Override
		public PageReadEvent<EBPPacketDataVO> readData(ReadEBPPacketDataSetEvent request) 
		{
			Page<EBPPacketData> dbContent = ebpPacketDataRepository.findAll(new EBPPacketDataSpecifications( request.getImeiNumber(), request.getVehicleRegNo(),request.getSearchValue(),request.getStartDate(),request.getEndDate()),
					EBPPacketDataSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize()));
			
			List<EBPPacketDataVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), EBPPacketDataVO.class);
			Page<EBPPacketDataVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}
		
	}
	
}
