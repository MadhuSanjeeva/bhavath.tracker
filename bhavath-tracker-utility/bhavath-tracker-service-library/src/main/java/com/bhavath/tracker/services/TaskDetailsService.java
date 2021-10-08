package com.bhavath.tracker.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.constants.Constants;
import com.bhavath.tracker.data.model.DeviceDetails;
import com.bhavath.tracker.data.model.SIMDetails;
import com.bhavath.tracker.data.model.Task;
import com.bhavath.tracker.data.repos.DeviceDetailsRepository;
import com.bhavath.tracker.data.repos.TaskDetailsSpecifications;
import com.bhavath.tracker.data.repos.TaskRepository;
import com.bhavath.tracker.enums.MessageHeaderEnum;
import com.bhavath.tracker.enums.SmsPacketTypesEnum;
import com.bhavath.tracker.events.CreateTaskCommandEvent;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadSmsPacketTypesEvent;
import com.bhavath.tracker.events.ReadTaskCommandEvent;
import com.bhavath.tracker.util.ObjectMapperUtils;
import com.bhavath.tracker.util.RandomCodeGenUtil;
import com.bhavath.tracker.util.SMSConfiguration;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.ResponseVO;
import com.bhavath.tracker.vos.SmsPacketTypesVO;
import com.bhavath.tracker.vos.TaskVO;

import lombok.extern.slf4j.Slf4j;

public interface TaskDetailsService {

	public ResponseVO save(CreateTaskCommandEvent createTaskCommandEvent);

	public PageReadEvent<TaskVO> readData(ReadTaskCommandEvent request);

	public List<SmsPacketTypesVO> readSmsPacketTypes(ReadSmsPacketTypesEvent request);

	public final String replayGateWayNumber = "7288806661";

	@Service
	@Slf4j
	public class impl implements TaskDetailsService {

		@Autowired
		private TaskRepository taskRepository;
		@Autowired
		private SMSConfiguration smsConfiguration;
		@Autowired
		private DeviceDetailsRepository deviceDetailsRepository;

		@Override
		public ResponseVO save(CreateTaskCommandEvent createTaskCommandEvent) {
			log.info("TaskDetailsService :: save :: start() ");
			ResponseVO responseVO = new ResponseVO();
			try {
				TaskVO taskVO = createTaskCommandEvent.getTaskVO();

				Long randomCode = RandomCodeGenUtil.createRandomInteger().longValue();

				if (MessageHeaderEnum.ACTIVATION.getType().equalsIgnoreCase(taskVO.getHeader())) {
					taskVO.setDescription(MessageHeaderEnum.ACTIVATION.name());
					String assignPayload = taskVO.getHeader() + "," + randomCode + ","
							+ replayGateWayNumber + "*";
					log.info(" ACTIVATION payload:" + assignPayload);
					taskVO.setPayload(assignPayload);
					taskVO.setRequestCommandName(MessageHeaderEnum.ACTIVATION.name());
				}
				if (MessageHeaderEnum.HEALTH.getType().equalsIgnoreCase(taskVO.getHeader())) {
					taskVO.setDescription(MessageHeaderEnum.HEALTH.name());
					String assignPayload = taskVO.getHeader() + "," + randomCode + ","
							+ replayGateWayNumber + "*";
					log.info(" HEALTH payload:" + assignPayload);
					taskVO.setPayload(assignPayload);
					taskVO.setRequestCommandName(MessageHeaderEnum.HEALTH.name());
				}

				DeviceDetails deviceDetails = deviceDetailsRepository.findByImeiNumber(taskVO.getImeiNumber());
				Set<SIMDetails> listOfSims = deviceDetails.getSimDetails();

				String status = "";
				if(!ObjectUtils.isEmpty(listOfSims)) {
					String mobileNumbers = listOfSims.stream().map(SIMDetails::getMobileNumber).filter(str -> str != null && str.length() > 0)
							.collect(Collectors.joining(","));
					log.info(" Device Mobile Numbers :" + mobileNumbers);
					taskVO.setMobileNumber(mobileNumbers);
					if(mobileNumbers.length()>1) {
						String smsStatus = smsConfiguration.sendSms(taskVO.getMobileNumber(), taskVO.getPayload());
						JSONParser jsonParser = new JSONParser();
						JSONObject json = (JSONObject) jsonParser.parse(smsStatus);
						status = (String) json.get("status");
					}else {
						status = MessageHeaderEnum.MOBILE_STATUS.getType();
						log.info("No Mobile numbers are available for this IMEI Number : "+taskVO.getImeiNumber());
						responseVO.setCode(Constants.ResponseMessages.CODE_400);
						responseVO.setMessage("No Mobile numbers are available for this IMEI Number : "+taskVO.getImeiNumber());
						return responseVO;
					}
				}else {
					status = MessageHeaderEnum.SIM_STATUS.getType();
					log.info("No Sim Details are available for this IMEI Number : "+taskVO.getImeiNumber());
					responseVO.setCode(Constants.ResponseMessages.CODE_400);
					responseVO.setMessage("No Sim Details are available for this IMEI Number : "+taskVO.getImeiNumber());
					return responseVO;
				}
				if (MessageHeaderEnum.GET.getType().equalsIgnoreCase(taskVO.getHeader()) || MessageHeaderEnum.CLR.getType().equalsIgnoreCase(taskVO.getHeader())) {
					String tempCommand = taskVO.getPayload().split("-", 3)[2].split("=")[0];
					taskVO.setRequestCommandName(tempCommand.substring(0, tempCommand.length()-1));
				}
				
				if (MessageHeaderEnum.SET.getType().equalsIgnoreCase(taskVO.getHeader())) {
					taskVO.setRequestCommandName(taskVO.getPayload().split("-", 3)[2].split("=")[0]);
				}
				
				log.info("Imei Number : "+taskVO.getImeiNumber());
				log.info("Header : "+taskVO.getHeader());
				log.info("Request Command Name : "+taskVO.getRequestCommandName());
				log.info("Device Response Status- Default  : "+Boolean.FALSE);
				log.info("smsStatus   : "+status);
				
				//Task taskRepo = taskRepository.getByImeiNumberAndCommandNameAndStatus(taskVO.getImeiNumber(),taskVO.getHeader(), taskVO.getRequestCommandName());
				
				Task task = Task.builder().id(taskVO.getId()).createdDate(DateUtils.getCurrentSystemTimestamp())
						.header(taskVO.getHeader()).replyGatewayNumber(replayGateWayNumber).payload(taskVO.getPayload())
						.imeiNumber(taskVO.getImeiNumber()).randomCode(randomCode).description(taskVO.getDescription())
						.smsStatus(status).mobileNumber(taskVO.getMobileNumber())
						.requestCommandName(taskVO.getRequestCommandName()).deviceResponseStatus(Boolean.FALSE)
						.build();
				taskRepository.save(task);
				responseVO.setCode(Constants.ResponseMessages.CODE_200);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
			} catch (Exception e) {
				responseVO.setCode(Constants.ResponseMessages.CODE_500);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_500);
				log.info("Exception while saving Task Details :: " + e.getCause(), e);
			}
			
			log.info("TaskDetailsService :: save :: end() ");
			return responseVO;
		}

		@Override
		public PageReadEvent<TaskVO> readData(ReadTaskCommandEvent request) {
			Page<Task> dbContent = taskRepository.findAll(new TaskDetailsSpecifications(request.getImeiNumber(),request.getSearchValue(),request.getSearchDate()),
					TaskDetailsSpecifications.constructPageSpecification(request.getPageable().getPageNumber(),
							request.getPageable().getPageSize()));
			List<TaskVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), TaskVO.class);
			Page<TaskVO> page = new PageImpl<>(content, request.getPageable(),
					dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}

		@Override
		public List<SmsPacketTypesVO> readSmsPacketTypes(ReadSmsPacketTypesEvent request) {
			String commandType = request.getCommandType();
			List<SmsPacketTypesVO> list = new ArrayList<SmsPacketTypesVO>();
			List<SmsPacketTypesEnum> enumKeys = SmsPacketTypesEnum.getEnumNameForValue(commandType);
			for (SmsPacketTypesEnum packetTypes : enumKeys) {
				SmsPacketTypesVO smsPacketTypesVO = SmsPacketTypesVO.builder().key(packetTypes.getKey())
						.description(packetTypes.getDescription()).payload(packetTypes.getPayload())
						.commandType(packetTypes.getCommandType()).build();
				list.add(smsPacketTypesVO);
			}
			return list;
		}
	}
}
