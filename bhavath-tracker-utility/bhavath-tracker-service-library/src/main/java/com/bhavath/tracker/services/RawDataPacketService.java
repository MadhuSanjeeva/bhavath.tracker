package com.bhavath.tracker.services;

		import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.bhavath.tracker.data.model.RawPacketData;
import com.bhavath.tracker.data.repos.RawDataSpecifications;
import com.bhavath.tracker.data.repos.RawPacketDataRepository;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadRawDataSetEvent;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.util.ObjectMapperUtils;
import com.bhavath.tracker.vos.RawDataPacketVO;

import lombok.extern.slf4j.Slf4j;


public interface RawDataPacketService
{
	public void save(RawDataPacketVO dataPacketVO);
	public List<RawPacketData> getDataBySerialNumberAndDate(String siteId, String date);
	public PageReadEvent<RawDataPacketVO> readData(ReadRawDataSetEvent request);

	@Slf4j
	@Service
	public class impl implements RawDataPacketService
	{
		@Autowired private RawPacketDataRepository rawPacketDataRepositary;

		@Override
		public void save(RawDataPacketVO dataPacketVO)
		{
			log.info("RawDataPacketService :: save :: start() ");
			log.info("RawDataPacketService :: save :: rawPacketData before  "+dataPacketVO);
			boolean isLmpType = ("LMP".equals(dataPacketVO.getCvpType()));
			boolean isEpbType = ("EPB".equals(dataPacketVO.getCvpType()));
			
			if(isLmpType) {
				dataPacketVO.setPacketStatus(dataPacketVO.getPacketStatus().equals("L") ? "Live" : "History" );
			}
			if(isEpbType) {
				dataPacketVO.setPacketStatus(dataPacketVO.getPacketStatus().equals("NM") ? "Normal Packet" : "Stored Packet" );
			}
			
			log.info("RawDataPacketService :: save :: rawPacketData before isLmpType  "+isLmpType);
			RawPacketData rawPacketData = RawPacketData.builder()
					.createdDate(DateUitls.getCurrentSystemTimestamp())
					.rawData(isLmpType ? convertUTF8(dataPacketVO.getRawData()) : dataPacketVO.getRawData())
					.networkDate(DateUitls.getSqlDateFromString(dataPacketVO.getNetworkDate()))
					.networkTime(dataPacketVO.getNetworkTime())
					.imeiNumber(dataPacketVO.getImeiNumber())
					.vehicleRegNo(dataPacketVO.getVehicleRegNo())
					.cvpType(dataPacketVO.getCvpType())
					.inBoundChannel(dataPacketVO.getInBoundChannel())
					.packetStatus(dataPacketVO.getPacketStatus())
					.packetType(dataPacketVO.getPacketType())
					.vehicleModeId(dataPacketVO.getVehicleModeId())
					.build();

			log.info("RawDataPacketService :: save :: rawPacketData after  "+rawPacketData);
			rawPacketDataRepositary.save(rawPacketData);
			rawPacketDataRepositary.flush();
			log.info("RawDataPacketService :: save :: end() ");
		}

		private String convertUTF8(String input) {
			byte ptext[] = null;
			try {
				ptext = input.getBytes("UTF8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return  new String(ptext);
		}

		@Override
		public List<RawPacketData> getDataBySerialNumberAndDate(String serialNumber, String date)
		{
			return rawPacketDataRepositary.getDataBySerialNumberAndDate(serialNumber,date.replaceAll("-", "/"));
		}

		@Override
		public PageReadEvent<RawDataPacketVO> readData(ReadRawDataSetEvent request)
		{
			Page<RawPacketData> dbContent = rawPacketDataRepositary.findAll(new RawDataSpecifications(request.getImeiNumber(),request.getStartDate(),request.getEndDate(), request.getInBoundChannel(), request.getCvpType(),request.getPacketStatus()),RawDataSpecifications.constructPageSpecification(request.getPageable().getPageNumber(),request.getPageable().getPageSize()));
			List<RawDataPacketVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), RawDataPacketVO.class);
			Page<RawDataPacketVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}
	}
}
