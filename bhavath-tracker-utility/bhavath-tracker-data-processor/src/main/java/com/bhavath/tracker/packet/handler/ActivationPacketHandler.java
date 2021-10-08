package com.bhavath.tracker.packet.handler;

import com.bhavath.tracker.enums.CVPPacketType;
import com.bhavath.tracker.enums.GPSDataPacketParams;
import com.bhavath.tracker.packet.ActivationPacket;
import com.bhavath.tracker.packet.CVPBasePacket;
import com.bhavath.tracker.vos.ActivationPacketVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActivationPacketHandler implements CVPPacketHandler 
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
		if(request.getCvpPacketType().name() == CVPPacketType.ACTVR.name()) {
			log.info(" !!!!!!!!!!!!!!!!!!!!!!!!  I am in " + request.getCvpPacketType().name());
			log.info(" #### Activation Data " + request.getRawdata());
			int cursorPos = 0;

			String delims = ",";
			String[] tokens = request.getRawdata().substring(cursorPos).split(delims);

			String header = tokens[0];
			log.info(" #### header " + header);
			cursorPos = getNextCursorPosition(cursorPos + header.length());

			String randomCode = tokens[1];
			log.info(" #### randomCode " + randomCode);
			cursorPos = getNextCursorPosition(cursorPos + randomCode.length());

			String vendorId = tokens[2];
			log.info(" #### vendorId " + vendorId);
			cursorPos = getNextCursorPosition(cursorPos + vendorId.length());

			String firmwareVersion = tokens[3];
			log.info(" #### firmwareVersion " + firmwareVersion);
			cursorPos = getNextCursorPosition(cursorPos + firmwareVersion.length());

			String imeiNumber = tokens[4];
			log.info(" #### imeiNumber " + imeiNumber);
			cursorPos = getNextCursorPosition(cursorPos + imeiNumber.length());

			String alertId = tokens[5];
			log.info(" #### alertId " + alertId);
			cursorPos = getNextCursorPosition(cursorPos + alertId.length());

			String latitude = tokens[6];
			log.info(" #### latitude " + latitude);
			cursorPos = getNextCursorPosition(cursorPos + latitude.length());

			String latitudeDirection = tokens[7];
			log.info(" #### latitudeDirection " + latitudeDirection);
			cursorPos = getNextCursorPosition(cursorPos + latitudeDirection.length());

			String longnitude = tokens[8];
			log.info(" #### longnitude " + longnitude);
			cursorPos = getNextCursorPosition(cursorPos + longnitude.length());

			String longnitudeDirection = tokens[9];
			log.info(" #### longnitudeDirection " + longnitudeDirection);
			cursorPos = getNextCursorPosition(cursorPos + longnitudeDirection.length());

			String gpsFix = tokens[10];
			log.info(" #### gpsFix " + gpsFix);
			cursorPos = getNextCursorPosition(cursorPos + gpsFix.length());

			String date = tokens[11];
			log.info(" #### date " + date);
			cursorPos = getNextCursorPosition(cursorPos + date.length());

			String time = tokens[12];
			log.info(" #### time " + time);
			cursorPos = getNextCursorPosition(cursorPos + time.length());

			String heading = tokens[13];
			log.info(" #### heading " + heading);
			cursorPos = getNextCursorPosition(cursorPos + heading.length());

			String speed = tokens[14];
			log.info(" #### speed " + speed);
			cursorPos = getNextCursorPosition(cursorPos + speed.length());

			String gsmStrength = tokens[15];
			log.info(" #### gsmStrength " + gsmStrength);
			cursorPos = getNextCursorPosition(cursorPos + gsmStrength.length());

			String countryCode = tokens[16];
			log.info(" #### countryCode " + countryCode);
			cursorPos = getNextCursorPosition(cursorPos + countryCode.length());

			String networkCode = tokens[17];
			log.info(" #### networkCode " + networkCode);
			cursorPos = getNextCursorPosition(cursorPos + networkCode.length());

			String lac = tokens[18];
			log.info(" #### lac " + lac);
			cursorPos = getNextCursorPosition(cursorPos + lac.length());

			String mainPower = tokens[19];
			log.info(" #### mainPower " + mainPower);
			cursorPos = getNextCursorPosition(cursorPos + mainPower.length());

			String ignStatus = tokens[20];
			log.info(" #### ignStatus " + ignStatus);
			cursorPos = getNextCursorPosition(cursorPos + ignStatus.length());

			String batteryVoltage = tokens[21];
			log.info(" #### batteryVoltage " + batteryVoltage);
			cursorPos = getNextCursorPosition(cursorPos + batteryVoltage.length());

			String frameNumber = tokens[22];
			log.info(" #### frameNumber " + frameNumber);
			cursorPos = getNextCursorPosition(cursorPos + frameNumber.length());

			String vehicleMode = tokens[23];
			log.info(" #### vehicleMode " + vehicleMode);
			cursorPos = getNextCursorPosition(cursorPos + vehicleMode.length());
			
			ActivationPacketVO activationPacketVO = ActivationPacketVO.builder()
											.header(header)
											.randomCode(randomCode)
											.vendorId(vendorId)
											.firmwareVersion(firmwareVersion)
											.imeiNumber(imeiNumber)
											.alertId(alertId)
											.latitude(latitude)
											.latitudeDirection(latitudeDirection)
											.longnitude(longnitude)
											.longnitudeDirection(longnitudeDirection)
											.gpsFix(gpsFix)
											.date(date)
											.time(time)
											.heading(heading)
											.speed(speed)
											.gsmStrength(gsmStrength)
											.countryCode(countryCode)
											.networkCode(networkCode)
											.lac(lac)
											.mainPower(mainPower)
											.ignStatus(ignStatus)
											.batteryVoltage(batteryVoltage)
											.frameNumber(frameNumber)
											.vehicleMode(vehicleMode)
											.build();
			
			basePkt = ActivationPacket.builder().activationPacketVO(activationPacketVO).build();
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
