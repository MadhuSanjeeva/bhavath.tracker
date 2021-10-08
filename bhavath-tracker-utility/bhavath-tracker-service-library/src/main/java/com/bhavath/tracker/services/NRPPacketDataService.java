package com.bhavath.tracker.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.bhavath.tracker.data.model.NRPPacketData;
import com.bhavath.tracker.data.repos.EBPPacketDataSpecifications;
import com.bhavath.tracker.data.repos.NRPPacketDataRepository;
import com.bhavath.tracker.data.repos.NRPPacketDataSpecifications;
import com.bhavath.tracker.events.CreateNRPPacketDataEvent;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadEBPPacketDataSetEvent;
import com.bhavath.tracker.events.ReadNRPPacketDataSetEvent;
import com.bhavath.tracker.util.CommonUtility;
import com.bhavath.tracker.util.ObjectMapperUtils;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.EBPPacketDataVO;
import com.bhavath.tracker.vos.NRPPacketDataVO;

import lombok.extern.slf4j.Slf4j;

public interface NRPPacketDataService {
	
	public void save(CreateNRPPacketDataEvent createNRPPacketDataEvent);
	public PageReadEvent<NRPPacketDataVO> readData(ReadNRPPacketDataSetEvent request);
	
	@Service
	@Slf4j
	public class impl implements NRPPacketDataService{
		
		@Autowired private NRPPacketDataRepository nrpPacketDataRepository;
		
		@Override
		public void save(CreateNRPPacketDataEvent createNRPPacketDataEvent) {
			
			NRPPacketDataVO nrpPacketDataVO = createNRPPacketDataEvent.getNrpPacketDataVO();
			
			String latitude = CommonUtility.convertLatitude(nrpPacketDataVO.getLatitude());
			String longitude = CommonUtility.convertLangitude(nrpPacketDataVO.getLongitude());
			
			//Provider - for G - Fine GPS N – Coarse GPS or data from the network
			//Setting by default Provider - G
			NRPPacketData nrpPacketData = NRPPacketData.builder()
					.createdDate(DateUtils.getCurrentSystemTimestamp())
					.packetType(nrpPacketDataVO.getPacketType())
					.imeiNumber(nrpPacketDataVO.getImeiNumber())
					.packetStaus(nrpPacketDataVO.getPacketStaus())
					.timestamp(nrpPacketDataVO.getTimestamp())
					.gpsValidity(nrpPacketDataVO.getGpsValidity())
					.latitude(latitude)
					.latitudeDirection(nrpPacketDataVO.getLatitudeDirection())
					.longitude(longitude)
					.longitudeDirection(nrpPacketDataVO.getLongitudeDirection())
					.altitude(nrpPacketDataVO.getAltitude())
					.speed(nrpPacketDataVO.getSpeed())
					.distance(nrpPacketDataVO.getDistance())
					.provider("G")
					.vehicleRegNo(nrpPacketDataVO.getVehicleRegNo())
					.firmwareVersion(nrpPacketDataVO.getFirmwareVersion())
					.networkDate(nrpPacketDataVO.getNetworkDate())
					.networkTime(nrpPacketDataVO.getNetworkTime())
					.build();
			nrpPacketDataRepository.save(nrpPacketData);
			nrpPacketDataRepository.flush();
		}
		
		@Override
		public PageReadEvent<NRPPacketDataVO> readData(ReadNRPPacketDataSetEvent request) 
		{
			Page<NRPPacketData> dbContent = nrpPacketDataRepository.findAll(new NRPPacketDataSpecifications( request.getImeiNumber(), request.getVehicleRegNo(),request.getSearchValue(),request.getStartDate(),request.getEndDate()),
					NRPPacketDataSpecifications	.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize()));
			
			List<NRPPacketDataVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), NRPPacketDataVO.class);
			Page<NRPPacketDataVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}
		
	}
	
}
