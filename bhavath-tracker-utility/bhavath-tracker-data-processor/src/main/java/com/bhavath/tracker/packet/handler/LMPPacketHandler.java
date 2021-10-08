package com.bhavath.tracker.packet.handler;

import com.bhavath.tracker.enums.CVPPacketType;
import com.bhavath.tracker.enums.GeneralPacketAttributes;
import com.bhavath.tracker.enums.LMPacketTypes;
import com.bhavath.tracker.packet.CVPBasePacket;
import com.bhavath.tracker.packet.LMPPacket;
import com.bhavath.tracker.util.CommonUtility;
import com.bhavath.tracker.vos.LMPPacketDataVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LMPPacketHandler implements CVPPacketHandler 
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
		if(request.getCvpPacketType().name() == CVPPacketType.LMP.name()) {
			log.info(" !!!!!!!!!!!!!!!!!!!!!!!!  I am in " + request.getCvpPacketType().name());
			log.info(" #### LMP Data " + request.getRawdata());
			int cursorPos = 0;

			String delims = ",";
			String[] tokens = request.getRawdata().substring(cursorPos).split(delims);
			
			
			String vendorId = CommonUtility.nullCheck(tokens[0]);
			log.info(" #### Vendor Id " + vendorId);
			cursorPos = getNextCursorPosition(cursorPos + vendorId.length());
			
			String firmwareVersion = CommonUtility.nullCheck(tokens[1]);
			log.info(" #### Firmware Version " + firmwareVersion);
			cursorPos = getNextCursorPosition(cursorPos + firmwareVersion.length());
			
			String packetType = CommonUtility.nullCheck(tokens[2]);
			log.info(" #### Packet Type " + packetType);
			cursorPos = getNextCursorPosition(cursorPos + packetType.length());
			
			
			String packetTypeDesc= LMPacketTypes.valueOf(packetType).getType();
			
			String vehicleModeId = CommonUtility.nullCheck(tokens[3]);
			log.info(" #### vehicleModeId " + vehicleModeId);
			cursorPos = getNextCursorPosition(cursorPos + vehicleModeId.length());
			
			String packetStatus = CommonUtility.nullCheck(tokens[4]);
			log.info(" #### Packet Status " + packetStatus);
			cursorPos = getNextCursorPosition(cursorPos + packetStatus.length());
			
			String imei = CommonUtility.nullCheck(tokens[5]);
			log.info(" #### imei " + imei);
			cursorPos = getNextCursorPosition(cursorPos + imei.length());
			
			String vehicleRegNo = CommonUtility.nullCheck(tokens[6]);
			log.info(" #### vehicleRegNo " + vehicleRegNo);
			cursorPos = getNextCursorPosition(cursorPos + vehicleRegNo.length());
			
			String gpsFix = CommonUtility.nullCheck(tokens[7]);
			log.info(" #### gpsFix " + gpsFix);
			cursorPos = getNextCursorPosition(cursorPos + gpsFix.length());
			
			String date = CommonUtility.nullCheck(tokens[8]);
			log.info(" #### date " + date);
			cursorPos = getNextCursorPosition(cursorPos + date.length());
			
			String time = CommonUtility.nullCheck(tokens[9]);
			log.info(" #### time " + time);
			cursorPos = getNextCursorPosition(cursorPos + time.length());
			
			String latitude = CommonUtility.nullCheck(tokens[10]);
			log.info(" #### latitude " + latitude);
			cursorPos = getNextCursorPosition(cursorPos + latitude.length());
			
			String latitudeDirection = CommonUtility.nullCheck(tokens[11]);
			log.info(" #### latitudeDirection " + latitudeDirection);
			cursorPos = getNextCursorPosition(cursorPos + latitudeDirection.length());
			
			// Need to check
			
			String longitude = CommonUtility.nullCheck(tokens[12]);
			log.info(" #### longitude " + longitude);
			cursorPos = getNextCursorPosition(cursorPos + latitudeDirection.length());

			String longitudeDirection = CommonUtility.nullCheck(tokens[13]);
			log.info(" #### longitudeDirection " + longitudeDirection);
			cursorPos = getNextCursorPosition(cursorPos + latitudeDirection.length());
			
			String speed = CommonUtility.nullCheck(tokens[14]);
			log.info(" #### speed " + speed);
			cursorPos = getNextCursorPosition(cursorPos + speed.length());
			
			String heading = CommonUtility.nullCheck(tokens[15]);
			log.info(" #### heading " + heading);
			cursorPos = getNextCursorPosition(cursorPos + heading.length());
			
			String noOfSatellites = CommonUtility.nullCheck(tokens[16]);
			log.info(" #### noOfSatellites " + noOfSatellites);
			cursorPos = getNextCursorPosition(cursorPos + noOfSatellites.length());
			
			String altitude = CommonUtility.nullCheck(tokens[17]);
			log.info(" #### altitude " + altitude);
			cursorPos = getNextCursorPosition(cursorPos + altitude.length());
			
			String pdop = CommonUtility.nullCheck(tokens[18]);
			log.info(" #### pdop " + pdop);
			cursorPos = getNextCursorPosition(cursorPos + pdop.length());
			
			String hdop = CommonUtility.nullCheck(tokens[19]);
			log.info(" #### hdop " + hdop);
			cursorPos = getNextCursorPosition(cursorPos + hdop.length());
			
			String networkOperatorName = CommonUtility.nullCheck(tokens[20]);
			log.info(" #### networkOperatorName " + networkOperatorName);
			cursorPos = getNextCursorPosition(cursorPos + networkOperatorName.length());
			
			String ignition = CommonUtility.nullCheck(tokens[21]);
			log.info(" #### ignition " + ignition);
			cursorPos = getNextCursorPosition(cursorPos + ignition.length());
			
			String mainPowerStatus = CommonUtility.nullCheck(tokens[22]);
			log.info(" #### mainPowerStatus " + mainPowerStatus);
			cursorPos = getNextCursorPosition(cursorPos + mainPowerStatus.length());
			
			String mainInputVoltage = CommonUtility.nullCheck(tokens[23]);
			log.info(" #### mainInputVoltage " + mainInputVoltage);
			cursorPos = getNextCursorPosition(cursorPos + mainInputVoltage.length());
			
			String internalBatteryVoltage = CommonUtility.nullCheck(tokens[24]);
			log.info(" #### internalBatteryVoltage " + internalBatteryVoltage);
			cursorPos = getNextCursorPosition(cursorPos + internalBatteryVoltage.length());
			
			String emergencyStatus = CommonUtility.nullCheck(tokens[25]);
			log.info(" #### emergencyStatus " + emergencyStatus);
			cursorPos = getNextCursorPosition(cursorPos + emergencyStatus.length());
			
			String tamperAlert = CommonUtility.nullCheck(tokens[26]);
			log.info(" #### tamperAlert " + tamperAlert);
			cursorPos = getNextCursorPosition(cursorPos + tamperAlert.length());
			
			String gsmSignalStrength = CommonUtility.nullCheck(tokens[27]);
			log.info(" #### gsmSignalStrength " + gsmSignalStrength);
			cursorPos = getNextCursorPosition(cursorPos + gsmSignalStrength.length());
			
			String mcc = CommonUtility.nullCheck(tokens[28]);
			log.info(" #### mcc " + mcc);
			cursorPos = getNextCursorPosition(cursorPos + mcc.length());
			
			String mnc = CommonUtility.nullCheck(tokens[29]);
			log.info(" #### mnc " + mnc);
			cursorPos = getNextCursorPosition(cursorPos + mnc.length());
			
			String lac = CommonUtility.nullCheck(tokens[30]);
			log.info(" #### lac " + lac);
			cursorPos = getNextCursorPosition(cursorPos + lac.length());
			
			String cellId = CommonUtility.nullCheck(tokens[31]);
			log.info(" #### cellId " + cellId);
			cursorPos = getNextCursorPosition(cursorPos + cellId.length());
					
			String nmr = CommonUtility.nullCheck(tokens[32]);
			log.info(" #### nmr " + nmr);
			cursorPos = getNextCursorPosition(cursorPos + nmr.length());
			
			String digitalInputStatus = CommonUtility.nullCheck(tokens[33]);
			log.info(" #### digitalInputStatus " + digitalInputStatus);
			cursorPos = getNextCursorPosition(cursorPos + digitalInputStatus.length());
			
			String digitalOutputStatus = CommonUtility.nullCheck(tokens[34]);
			log.info(" #### digitalOutputStatus " + digitalOutputStatus);
			cursorPos = getNextCursorPosition(cursorPos + digitalOutputStatus.length());
			
			String frameNumber = CommonUtility.nullCheck(tokens[35]);
			log.info(" #### frameNumber " + frameNumber);
			cursorPos = getNextCursorPosition(cursorPos + frameNumber.length());
			
			String checksum = CommonUtility.nullCheck(tokens[36]);
			log.info(" #### checksum " + checksum);
			cursorPos = getNextCursorPosition(cursorPos + checksum.length());
			
			String overNetworkIp = CommonUtility.nullCheck(tokens[37]);
			log.info(" #### overNetworkIp " + overNetworkIp);
			cursorPos = getNextCursorPosition(cursorPos + overNetworkIp.length());
			
			String overSmsNumber = CommonUtility.nullCheck(tokens[38]);
			log.info(" #### overSmsNumber " + overSmsNumber);
			cursorPos = getNextCursorPosition(cursorPos + overSmsNumber.length());
			
			String analogZero = CommonUtility.nullCheck(tokens[39]);
			log.info(" #### analogZero " + analogZero);
			cursorPos = getNextCursorPosition(cursorPos + analogZero.length());
			
			String analogOne = CommonUtility.nullCheck(tokens[40]);
			log.info(" #### analogOne " + analogOne);
			cursorPos = getNextCursorPosition(cursorPos + analogOne.length());
			
			String overTheAirParameter = CommonUtility.nullCheck(tokens[41]);
			log.info(" #### overTheAirParameter " + overTheAirParameter);
			cursorPos = getNextCursorPosition(cursorPos + overTheAirParameter.length());
			
			String endOfPacket = CommonUtility.nullCheck(tokens[42]);
			log.info(" #### endOfPacket " + endOfPacket);
			cursorPos = getNextCursorPosition(cursorPos + endOfPacket.length());
			
			LMPPacketDataVO lmpPacketDataVO = LMPPacketDataVO.builder()
														.vendorId(vendorId)
													 	.firmwareVersion(firmwareVersion)
														.packetType(packetType)
														.packetStatus(packetStatus)
														.imei(imei)
														.vehicleRegNo(vehicleRegNo)
														.gpsFix(gpsFix)
														.date(date)
														.time(time)
														.latitude(latitude)
														.latitudeDirection(latitudeDirection)
														.longitude(longitude)
														.longitudeDirection(longitudeDirection)
														.speed(speed)
														.heading(heading)
														.noOfSatellites(noOfSatellites)
														.altitude(altitude)
														.pdop(pdop)
														.hdop(hdop)
														.networkOperatorName(networkOperatorName)
														.ignition(ignition)
														.mainPowerStatus(mainPowerStatus)
														.mainInputVoltage(mainInputVoltage)
														.internalBatteryVoltage(internalBatteryVoltage)
														.emergencyStatus(emergencyStatus)
														.tamperAlert(tamperAlert)
														.gsmSignalStrength(gsmSignalStrength)
														.mcc(mcc)
														.lac(lac)
														.cellId(cellId)
														.nmr(nmr)
														.digitalInputStatus(digitalInputStatus)
														.digitalOutputStatus(digitalOutputStatus)
														.frameNumber(frameNumber)
														.vehicleModeId(vehicleModeId)
														.overNetworkIp(overNetworkIp)
														.overSmsNumber(overSmsNumber)
														.analogOne(analogOne.trim())
														.analogZero(analogZero.trim())
														.overTheAirParameter(overTheAirParameter)
														.build();
			
			basePkt = LMPPacket.builder().lmpPacketDataVO(lmpPacketDataVO).build();
		}else 
		{
			basePkt = nextHandler.processRequest(request);
		}
		
		
		return basePkt;
	}
	
	private int getNextCursorPosition (int cursorPos)
	{
		cursorPos = cursorPos + GeneralPacketAttributes.SEPARATOR.getNoOfChars();
		return cursorPos;
	}

	
}
