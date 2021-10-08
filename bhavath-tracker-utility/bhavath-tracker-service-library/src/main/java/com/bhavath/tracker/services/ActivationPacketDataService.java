package com.bhavath.tracker.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.bhavath.tracker.constants.Constants;
import com.bhavath.tracker.data.model.ActivationPacketData;
import com.bhavath.tracker.data.model.Task;
import com.bhavath.tracker.data.repos.ActivationPacketDataRepository;
import com.bhavath.tracker.data.repos.ActivationPacketDataSpecifications;
import com.bhavath.tracker.data.repos.HealthPacketDataSpecifications;
import com.bhavath.tracker.data.repos.TaskRepository;
import com.bhavath.tracker.enums.MessageHeaderEnum;
import com.bhavath.tracker.events.CreateActivationPacketDataEvent;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadActivationPacketDataSetEvent;
import com.bhavath.tracker.util.CommonUtility;
import com.bhavath.tracker.util.ObjectMapperUtils;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.ActivationPacketVO;
import com.bhavath.tracker.vos.ResponseVO;

import lombok.extern.slf4j.Slf4j;

public interface ActivationPacketDataService {
	
	public ResponseVO save(CreateActivationPacketDataEvent createActivationPacketDataEvent);
	public PageReadEvent<ActivationPacketVO> readData(ReadActivationPacketDataSetEvent request);
	public ActivationPacketVO processRequest(String payload);
	
	@Service
	@Slf4j
	public class impl implements ActivationPacketDataService{
		
		@Autowired private ActivationPacketDataRepository activationPacketDataRepository;
		
		@Autowired private TaskRepository taskRepository;
		
		@Override
		public ResponseVO save(CreateActivationPacketDataEvent createActivationPacketDataEvent) {
			log.info("ActivationPacketDataService :: save :: start() ");
			ResponseVO responseVO = new ResponseVO();
			ActivationPacketVO activationPacketVO = createActivationPacketDataEvent.getActivationPacketVO();
			
			String latitude = CommonUtility.convertLatitude(activationPacketVO.getLatitude());
			String longitude = CommonUtility.convertLangitude(activationPacketVO.getLongnitude());
			
			ActivationPacketData activationPacketData = ActivationPacketData.builder()
					.header(activationPacketVO.getHeader())
					.randomCode(activationPacketVO.getRandomCode())
					.vendorId(activationPacketVO.getVendorId())
					.firmwareVersion(activationPacketVO.getFirmwareVersion())
					.imeiNumber(activationPacketVO.getImeiNumber())
					.alertId(activationPacketVO.getAlertId())
					.latitude(latitude)
					.latitudeDirection(activationPacketVO.getLatitudeDirection())
					.longnitude(longitude)
					.longnitudeDirection(activationPacketVO.getLongnitudeDirection())
					.gpsFix(activationPacketVO.getGpsFix())
					.date(activationPacketVO.getDate())
					.time(activationPacketVO.getTime())					
					.heading(activationPacketVO.getHeading())					
					.speed(activationPacketVO.getSpeed())
					.gsmStrength(activationPacketVO.getGsmStrength())
					.countryCode(activationPacketVO.getCountryCode())
					.networkCode(activationPacketVO.getNetworkCode())
					.lac(activationPacketVO.getLac())
					.mainPower(activationPacketVO.getMainPower())
					.ignStatus(activationPacketVO.getIgnStatus())
					.batteryVoltage(activationPacketVO.getBatteryVoltage())
					.frameNumber(activationPacketVO.getFrameNumber())
					.vehicleMode(activationPacketVO.getVehicleMode())
					.rawData(activationPacketVO.getRawData())
					.build();
			activationPacketDataRepository.save(activationPacketData);
			activationPacketDataRepository.flush();
			Task task = taskRepository.findTopOneByImeiNumberAndHeaderAndRequestCommandNameOrderByCreatedDateDesc(
					activationPacketVO.getImeiNumber(),MessageHeaderEnum.ACTIVATION.getType(),MessageHeaderEnum.ACTIVATION.name());
			log.info("ActivationPacketDataService :: save :: taskRepo "+task);
			if(!ObjectUtils.isEmpty(task)) {
				task.setDeviceResponseStatus(true);
				task.setDeviceResponseTime(DateUtils.getCurrentSystemTimestamp());
				task.setResponseCommandName(MessageHeaderEnum.ACTIVATION.name());
				task.setDeviceResponse(MessageHeaderEnum.ACTIVATION_RESPONSE_STATUS.getType());
				taskRepository.save(task);
				taskRepository.flush();
				responseVO.setCode(Constants.ResponseMessages.CODE_200);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
			}
			log.info("ActivationPacketDataService :: save :: end() ");
			return responseVO;
		}
		
		@Override
		public PageReadEvent<ActivationPacketVO> readData(ReadActivationPacketDataSetEvent request) 
		{
			Page<ActivationPacketData> dbContent = activationPacketDataRepository.findAll(new ActivationPacketDataSpecifications(request.getImeiNumber(),request.getSearchDate()),
					HealthPacketDataSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize()));
			
			List<ActivationPacketVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), ActivationPacketVO.class);
			Page<ActivationPacketVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}

		@Override
		public ActivationPacketVO processRequest(String payload) {
			
			log.info(" #### Activation Data payload " + payload);
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
			return activationPacketVO;
			
		}
		
		
		private int getNextCursorPosition (int cursorPos)
		{
			cursorPos = cursorPos + 1;
			return cursorPos;
		}
		
	}
	
	
	
	
}
