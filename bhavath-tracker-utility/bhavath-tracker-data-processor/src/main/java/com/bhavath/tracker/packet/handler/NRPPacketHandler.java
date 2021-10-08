package com.bhavath.tracker.packet.handler;

import com.bhavath.tracker.enums.CVPPacketType;
import com.bhavath.tracker.enums.GPSDataPacketParams;
import com.bhavath.tracker.packet.CVPBasePacket;
import com.bhavath.tracker.packet.NRPPacket;
import com.bhavath.tracker.vos.NRPPacketDataVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NRPPacketHandler implements CVPPacketHandler 
{
	
	public CVPPacketHandler nextHandler;

	@Override
	public void nextHandler(CVPPacketHandler nextHandler) {
		this.nextHandler = nextHandler;
	}
	
	@Override
	public CVPBasePacket processRequest(CVPData request) 
	{
		CVPBasePacket basePkt = null;
		
		if(request.getCvpPacketType().name() == CVPPacketType.NRP.name()) {
			log.info(" !!!!!!!!!!!!!!!!!!!!!!!!  I am in " + request.getCvpPacketType().name());
			log.info(" #### NRP Data " + request.getRawdata());
			int cursorPos = 0;

			String delims = ",";
			String[] tokens = request.getRawdata().substring(cursorPos).split(delims);

			String packetType = tokens[0];
			log.info(" #### packetType " + packetType);
			cursorPos = getNextCursorPosition(cursorPos + packetType.length());

			String imeiNumber = tokens[1];
			log.info(" #### IMEI Number " + imeiNumber);
			cursorPos = getNextCursorPosition(cursorPos + imeiNumber.length());

			String packetStaus = tokens[2];
			log.info(" #### Packet Status " + packetStaus);
			cursorPos = getNextCursorPosition(cursorPos + packetStaus.length());

			String timestamp = tokens[3];
			log.info(" #### timestamp " + timestamp);
			cursorPos = getNextCursorPosition(cursorPos + timestamp.length());

			String gpsValidity = tokens[4];
			log.info(" #### GPS Validity " + gpsValidity);
			cursorPos = getNextCursorPosition(cursorPos + gpsValidity.length());

			String latitude = tokens[5];
			log.info(" #### latitude " + latitude);
			cursorPos = getNextCursorPosition(cursorPos + latitude.length());

			String latitudeDirection = tokens[6];
			log.info(" #### latitudeDirection " + latitudeDirection);
			cursorPos = getNextCursorPosition(cursorPos + latitudeDirection.length());

			String longitude = tokens[7];
			log.info(" #### longitude " + longitude);
			cursorPos = getNextCursorPosition(cursorPos + longitude.length());

			String longitudeDirection = tokens[8];
			log.info(" #### latitude " + longitudeDirection);
			cursorPos = getNextCursorPosition(cursorPos + longitudeDirection.length());

			String altitude = tokens[9];
			log.info(" #### altitude " + altitude);
			cursorPos = getNextCursorPosition(cursorPos + altitude.length());

			String speed = tokens[10];
			log.info(" #### speed " + speed);
			cursorPos = getNextCursorPosition(cursorPos + speed.length());

			String distance = tokens[11];
			log.info(" #### distance " + distance);
			cursorPos = getNextCursorPosition(cursorPos + distance.length());

			String provider = tokens[12];
			log.info(" #### provider " + provider);
			cursorPos = getNextCursorPosition(cursorPos + provider.length());

			String vehicleRegNo = tokens[13];
			log.info(" #### vehicleRegNo " + vehicleRegNo);
			cursorPos = getNextCursorPosition(cursorPos + vehicleRegNo.length());

			String firmwareVersion = tokens[14];
			log.info(" #### firmwareVersion " + firmwareVersion);
			cursorPos = getNextCursorPosition(cursorPos + firmwareVersion.length());

			String endCharacter = tokens[15];
			log.info(" #### endCharacter " + endCharacter);
			cursorPos = getNextCursorPosition(cursorPos + endCharacter.length());

			String checksum = tokens[16];
			log.info(" #### checksum " + checksum);
			cursorPos = getNextCursorPosition(cursorPos + checksum.length());
			
			NRPPacketDataVO nrpPacketDataVO = NRPPacketDataVO.builder()
					.packetType(packetType)
					.imeiNumber(imeiNumber)
					.packetStaus(packetStaus)
					.timestamp(timestamp)
					.gpsValidity(gpsValidity)
					.latitude(latitude)
					.latitudeDirection(latitudeDirection)
					.longitude(longitude)
					.longitudeDirection(longitudeDirection)
					.altitude(altitude)
					.speed(speed)
					.distance(distance)
					.provider(provider)
					.vehicleRegNo(vehicleRegNo)
					.firmwareVersion(firmwareVersion)
					.build();
				basePkt = NRPPacket.builder().nrpPacketDataVO(nrpPacketDataVO).build();
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
