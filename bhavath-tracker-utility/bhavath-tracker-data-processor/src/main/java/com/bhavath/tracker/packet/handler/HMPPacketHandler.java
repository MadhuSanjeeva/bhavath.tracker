package com.bhavath.tracker.packet.handler;

import com.bhavath.tracker.enums.CVPPacketType;
import com.bhavath.tracker.enums.GPSDataPacketParams;
import com.bhavath.tracker.packet.CVPBasePacket;
import com.bhavath.tracker.packet.HMPPacket;
import com.bhavath.tracker.packet.LMPPacket;
import com.bhavath.tracker.vos.HMPPacketDataVO;
import com.bhavath.tracker.vos.LMPPacketDataVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HMPPacketHandler implements CVPPacketHandler 
{
	
	public CVPPacketHandler nextHandler;

	@Override
	public void nextHandler(CVPPacketHandler nextHandler) {
		this.nextHandler = nextHandler;
		
	}
	
	@Override
	public CVPBasePacket processRequest(CVPData request) 
	{
		log.info(" !!!!!!!!!!!!!!!!!!!!!!!!  processRequest(): " + request.getCvpPacketType().name());
		CVPBasePacket basePkt = null;
		if(request.getCvpPacketType().name() == CVPPacketType.HMP.name()) {
			log.info(" !!!!!!!!!!!!!!!!!!!!!!!!  I am in " + request.getCvpPacketType().name());
			log.info(" #### HMP Data " + request.getRawdata());
			int cursorPos = 0;

			String delims = ",";
			String[] tokens = request.getRawdata().substring(cursorPos).split(delims);

			String vendorId   = tokens[0];
			log.info(" #### Vendor ID   " + vendorId);
			cursorPos = getNextCursorPosition(cursorPos + vendorId.length());

			String firmwareVersion = tokens[1];
			log.info(" #### Firmware Version " + firmwareVersion);
			cursorPos = getNextCursorPosition(cursorPos + firmwareVersion.length());

			String imeiNumber = tokens[2];
			log.info(" #### IMEI Number " + imeiNumber);
			cursorPos = getNextCursorPosition(cursorPos + imeiNumber.length());

			String batteryPercentage = tokens[3];
			log.info(" #### Battery percentage " + batteryPercentage);
			cursorPos = getNextCursorPosition(cursorPos + batteryPercentage.length());

			String lowBatteryThrValue = tokens[4];
			log.info(" #### Low battery threshold value " + lowBatteryThrValue);
			cursorPos = getNextCursorPosition(cursorPos + lowBatteryThrValue.length());

			String memoryPercentage = tokens[5];
			log.info(" #### Memory percentage " + memoryPercentage);
			cursorPos = getNextCursorPosition(cursorPos + memoryPercentage.length());

			String dataUpdateON = tokens[6];
			log.info(" #### Data update rate when ignition ON " + dataUpdateON);
			cursorPos = getNextCursorPosition(cursorPos + dataUpdateON.length());

			String dataUpdateOFF = tokens[7];
			log.info(" #### Data update rate when ignition OFF " + dataUpdateOFF);
			cursorPos = getNextCursorPosition(cursorPos + dataUpdateOFF.length());

			String digitalIOStatus = tokens[8];
			log.info(" #### Digital I/O status  " + digitalIOStatus);
			cursorPos = getNextCursorPosition(cursorPos + digitalIOStatus.length());

			String analogIOStatus = tokens[9];
			log.info(" #### Analog I/O status " + analogIOStatus);
			cursorPos = getNextCursorPosition(cursorPos + analogIOStatus.length());
			
			String endOfPacket = tokens[10];
			log.info(" #### End Of Packet " + endOfPacket);
			cursorPos = getNextCursorPosition(cursorPos + endOfPacket.length());
			
			HMPPacketDataVO hmpPacketDataVO = HMPPacketDataVO.builder()
												.vendorId(vendorId)
												.firmwareVersion(firmwareVersion)
												.imeiNumber(imeiNumber)
												.batteryPercentage(batteryPercentage)
												.lowBatteryThrValue(lowBatteryThrValue)
												.memoryPercentage(memoryPercentage)
												.dataUpdateON(dataUpdateON)
												.dataUpdateOFF(dataUpdateOFF)
												.digitalIOStatus(digitalIOStatus)
												.analogIOStatus(analogIOStatus)
												.endOfPacket(endOfPacket)
												.build();
							basePkt = HMPPacket.builder().hmpPacketDataVO(hmpPacketDataVO).build();
		}else 
		{
			basePkt = nextHandler.processRequest(request);
		}
		
		return basePkt;
	}
	
	private int getNextCursorPosition (int cursorPos)
	{
		cursorPos = cursorPos + GPSDataPacketParams.SEPARATOR.getLength();
		return cursorPos;
	}

	
}
