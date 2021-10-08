package com.bhavath.tracker.processor;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.enums.CVPPacketType;
import com.bhavath.tracker.enums.GeneralPacketAttributes;
import com.bhavath.tracker.packet.CVPBasePacket;
import com.bhavath.tracker.packet.handler.CVPData;
import com.bhavath.tracker.packet.handler.CoRCVPPatternClient;
import com.bhavath.tracker.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

public interface DataIngestionProcessorHelper {

	public CVPBasePacket buildPackets(String input);

	@Service
	@Slf4j
	public class impl implements DataIngestionProcessorHelper {
		@Override
		public CVPBasePacket buildPackets(String input) {
			log.info("DataIngestionProcessorHelper :: buildPackets :: start() ");
			log.info("**** parse() - " + input);
			CVPBasePacket cvpBasePacket = null;
			String networkTime = null;
			String networkDate = null;
			
			String simNumber = null;
			String ipAddress = null;
			String imsi = null;
			String header = null;
			
			CVPPacketType cvpPacketType =  null;
			String cvpRawData = null;
			
			try {
				int cursorPos = 0;

				String delims = ",";
				String[] tokens = input.substring(cursorPos).split(delims);

				String startOfPacket = tokens[0];
				log.info(" startOfPacket :" + startOfPacket);
				cursorPos = getNextCursorPosition(cursorPos + startOfPacket.length());

				String identifier = tokens[1];
				log.info(" identifier :" + identifier);
				cursorPos = getNextCursorPosition(cursorPos + identifier.length());
				
				
				if(!(identifier.equalsIgnoreCase(CVPPacketType.EPB.name()) || identifier.equalsIgnoreCase(CVPPacketType.NRP.name()))) {
					
					networkTime = tokens[2];
					networkTime = !StringUtils.isEmpty(networkTime) ? networkTime
							: DateUtils.getCurrentDateAsString(DateUtils.dd_MM_yyyy_str);
					log.info(" networkTime :" + networkTime);
					cursorPos = getNextCursorPosition(cursorPos + networkTime.length());

					networkDate = tokens[3];
					networkDate = !StringUtils.isEmpty(networkDate) ? networkDate
							: DateUtils.getCurrentDateAsString(DateUtils.dd_MM_yyyy_str);
					log.info(" networkDate :" + networkDate);
					cursorPos = getNextCursorPosition(cursorPos + networkDate.length());

					simNumber = tokens[4];
					log.info(" simNumber :" + simNumber);
					cursorPos = getNextCursorPosition(cursorPos + simNumber.length());

					ipAddress = tokens[5];
					log.info(" ipAddress :" + ipAddress);
					cursorPos = getNextCursorPosition(cursorPos + ipAddress.length());

					imsi = tokens[6];
					log.info(" imsi :" + imsi);
					cursorPos = getNextCursorPosition(cursorPos + imsi.length());

					header = tokens[7];
					log.info(" header :" + header);
					// cursorPos = getNextCursorPosition(cursorPos + header.length());
					cvpRawData = input.substring(cursorPos = getNextCursorPosition(cursorPos + header.length()));
					
					cvpPacketType = CVPPacketType.valueOf(header);
				}else {
					cvpRawData = input.substring(cursorPos + startOfPacket.length()-1);
					cvpPacketType = CVPPacketType.valueOf(identifier);
				}
				

				try {

					log.info(" ^^^^^^^^^^^^^^^^^^^^^^^^^ CVP PacketType : " + cvpPacketType.name());

					CVPData cvpData = CVPData.builder().rawdata(cvpRawData).networkDate(networkDate)
							.networkTime(networkTime).cvpPacketType(cvpPacketType).build();

					log.info(" ^^^^^^^^^^^^^^^^^^^^^^^^^ cvpData : " + cvpData);
					CoRCVPPatternClient coRPatternClient = new CoRCVPPatternClient();

					cvpBasePacket = coRPatternClient.invokePacketHandler(cvpData);

					cvpBasePacket.setPacketType(cvpPacketType);
					cvpBasePacket.setIdentifier(identifier);
					cvpBasePacket.setNetworkDate(networkDate);
					cvpBasePacket.setNetworkTime(networkTime);
					cvpBasePacket.setIpAddress(ipAddress);
					cvpBasePacket.setSimNumber(simNumber);
					cvpBasePacket.setImsi(imsi);

					log.info(" ^^^^^^^^^^^^^^^^^^^^^^^^^ CVPBasePacket : " + cvpBasePacket);

					cvpBasePacket.setNetworkDate(!StringUtils.isEmpty(networkDate) ? networkDate
							: DateUtils.getCurrentDateAsString(DateUtils.dd_MM_yyyy_str));

					cvpBasePacket.setNetworkTime(!StringUtils.isEmpty(networkTime) ? networkTime
							: DateUtils.getTimestamp(DateUtils.HH_MM_SS));

					log.info(" ^^^^^^^^^^^^^^^^^^^^^^^^^ CVPBasePacket : " + cvpBasePacket);
					log.info("DataIngestionProcessorHelper :: buildPackets :: end() ");
					return cvpBasePacket;

				} catch (Exception e) {
					log.info("DataIngestionProcessorHelper :: buildPackets :: exception() ", e);
					log.info(" ******* :" + e.getMessage());
					e.printStackTrace();

				}
			} catch (Exception e) {
				log.info("DataIngestionProcessorHelper :: buildPackets :: exception1() ", e);
				log.info(" ######## :" + e.getMessage());
				e.printStackTrace();
			}

			return cvpBasePacket;

		}

		private int getNextCursorPosition(int cursorPos) {
			cursorPos = cursorPos + GeneralPacketAttributes.SEPARATOR.getNoOfChars();
			return cursorPos;
		}
	}
}
