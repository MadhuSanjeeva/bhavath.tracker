package com.bhavath.tracker.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.bhavath.tracker.constants.Constants;
import com.bhavath.tracker.data.model.HealthPacketData;
import com.bhavath.tracker.data.model.Task;
import com.bhavath.tracker.data.repos.HealthPacketDataRepository;
import com.bhavath.tracker.data.repos.HealthPacketDataSpecifications;
import com.bhavath.tracker.data.repos.TaskRepository;
import com.bhavath.tracker.enums.MessageHeaderEnum;
import com.bhavath.tracker.events.CreateHealthPacketDataEvent;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadHealthPacketDataSetEvent;
import com.bhavath.tracker.util.CommonUtility;
import com.bhavath.tracker.util.ObjectMapperUtils;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.HealthPacketVO;
import com.bhavath.tracker.vos.ResponseVO;

import lombok.extern.slf4j.Slf4j;

public interface HealthPacketDataService {
	
	public ResponseVO save(CreateHealthPacketDataEvent createHealthPacketDataEvent);
	public PageReadEvent<HealthPacketVO> readData(ReadHealthPacketDataSetEvent request);
	public HealthPacketVO processRequest(String payload);
	
	@Service
	@Slf4j
	public class impl implements HealthPacketDataService{
		
		@Autowired private HealthPacketDataRepository healthPacketDataRepository;
		
		@Autowired private TaskRepository taskRepository;
		
		@Override
		public ResponseVO save(CreateHealthPacketDataEvent createHealthPacketDataEvent) {
			log.info("HealthPacketDataService :: save :: start() ");
			ResponseVO responseVO = new ResponseVO();
			HealthPacketVO healthPacketVO = createHealthPacketDataEvent.getHealthPacketVO();
			
			String latitude = CommonUtility.convertLatitude(healthPacketVO.getLatitude());
			String longitude = CommonUtility.convertLangitude(healthPacketVO.getLongnitude());
			
			HealthPacketData healthPacketData = HealthPacketData.builder()
					.header(healthPacketVO.getHeader())
					.randomCode(healthPacketVO.getRandomCode())
					.vendorId(healthPacketVO.getVendorId())
					.firmwareVersion(healthPacketVO.getFirmwareVersion())
					.imeiNumber(healthPacketVO.getImeiNumber())
					.alertId(healthPacketVO.getAlertId())
					.latitude(latitude)
					.latitudeDirection(healthPacketVO.getLatitudeDirection())
					.longnitude(longitude)
					.longnitudeDirection(healthPacketVO.getLongnitudeDirection())
					.gpsFix(healthPacketVO.getGpsFix())
					.date(healthPacketVO.getDate())
					.time(healthPacketVO.getTime())
					.heading(healthPacketVO.getHeading())
					.speed(healthPacketVO.getSpeed())
					.gsmStrength(healthPacketVO.getGsmStrength())
					.countryCode(healthPacketVO.getCountryCode())
					.networkCode(healthPacketVO.getNetworkCode())
					.lac(healthPacketVO.getLac())
					.mainPower(healthPacketVO.getMainPower())
					.ignStatus(healthPacketVO.getIgnStatus())
					.batteryVoltage(healthPacketVO.getBatteryVoltage())
					.frameNumber(healthPacketVO.getFrameNumber())
					.vehicleMode(healthPacketVO.getVehicleMode())
					.rawData(healthPacketVO.getRawData())
					.build();
			healthPacketDataRepository.save(healthPacketData);
			healthPacketDataRepository.flush();
			Task task = taskRepository.findTopOneByImeiNumberAndHeaderAndRequestCommandNameOrderByCreatedDateDesc(
					healthPacketVO.getImeiNumber(),MessageHeaderEnum.HEALTH.getType(),MessageHeaderEnum.HEALTH.name());
			log.info("HealthPacketDataService :: save :: taskRepo "+task);
			if(!ObjectUtils.isEmpty(task)) {
				task.setDeviceResponseStatus(true);
				task.setDeviceResponseTime(DateUtils.getCurrentSystemTimestamp());
				task.setResponseCommandName(MessageHeaderEnum.HEALTH.name());
				task.setDeviceResponse(MessageHeaderEnum.HEALTH_RESPONSE_STATUS.getType());
				taskRepository.save(task);
				taskRepository.flush();
				responseVO.setCode(Constants.ResponseMessages.CODE_200);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
			}
			log.info("HealthPacketDataService :: save :: end() ");
			return responseVO;
		}
		
		@Override
		public PageReadEvent<HealthPacketVO> readData(ReadHealthPacketDataSetEvent request) 
		{
			Page<HealthPacketData> dbContent = healthPacketDataRepository.findAll(new HealthPacketDataSpecifications(request.getImeiNumber(),request.getSearchDate()),
					HealthPacketDataSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize()));
			
			List<HealthPacketVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), HealthPacketVO.class);
			Page<HealthPacketVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}

		@Override
		public HealthPacketVO processRequest(String payload) {
			
			log.info(" #### Health Data " + payload);
			int cursorPos = 0;

			String delims = ",";
			String[] tokens = payload.substring(cursorPos).split(delims);

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

			String dateTime = tokens[11];
			log.info(" #### dateTime " + dateTime);
			cursorPos = getNextCursorPosition(cursorPos + dateTime.length());

			String heading = tokens[12];
			log.info(" #### heading " + heading);
			cursorPos = getNextCursorPosition(cursorPos + heading.length());

			String speed = tokens[13];
			log.info(" #### speed " + speed);
			cursorPos = getNextCursorPosition(cursorPos + speed.length());

			String gsmStrength = tokens[14];
			log.info(" #### gsmStrength " + gsmStrength);
			cursorPos = getNextCursorPosition(cursorPos + gsmStrength.length());

			String countryCode = tokens[15];
			log.info(" #### countryCode " + countryCode);
			cursorPos = getNextCursorPosition(cursorPos + countryCode.length());

			String networkCode = tokens[16];
			log.info(" #### networkCode " + networkCode);
			cursorPos = getNextCursorPosition(cursorPos + networkCode.length());

			String lac = tokens[17];
			log.info(" #### lac " + lac);
			cursorPos = getNextCursorPosition(cursorPos + lac.length());

			String mainPower = tokens[18];
			log.info(" #### mainPower " + mainPower);
			cursorPos = getNextCursorPosition(cursorPos + mainPower.length());

			String ignStatus = tokens[19];
			log.info(" #### ignStatus " + ignStatus);
			cursorPos = getNextCursorPosition(cursorPos + ignStatus.length());

			String batteryVoltage = tokens[20];
			log.info(" #### batteryVoltage " + batteryVoltage);
			cursorPos = getNextCursorPosition(cursorPos + batteryVoltage.length());

			String frameNumber = tokens[21];
			log.info(" #### frameNumber " + frameNumber);
			cursorPos = getNextCursorPosition(cursorPos + frameNumber.length());

			String vehicleMode = tokens[22];
			log.info(" #### vehicleMode " + vehicleMode);
			cursorPos = getNextCursorPosition(cursorPos + vehicleMode.length());
			
			log.info(" #### rawData " + payload);
			HealthPacketVO healthPacketVO = HealthPacketVO.builder()
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
											.date(dateTime)
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
											.rawData(payload)
											.build();

			return healthPacketVO;
		}
		
		private int getNextCursorPosition (int cursorPos)
		{
			cursorPos = cursorPos + 1;
			return cursorPos;
		}
		
	}
	
}
