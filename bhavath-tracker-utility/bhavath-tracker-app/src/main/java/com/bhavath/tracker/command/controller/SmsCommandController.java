package com.bhavath.tracker.command.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bhavath.tracker.constants.Constants;
import com.bhavath.tracker.data.model.Task;
import com.bhavath.tracker.data.repos.TaskRepository;
import com.bhavath.tracker.events.CreateActivationPacketDataEvent;
import com.bhavath.tracker.events.CreateHealthPacketDataEvent;
import com.bhavath.tracker.services.ActivationPacketDataService;
import com.bhavath.tracker.services.HealthPacketDataService;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.ActivationPacketVO;
import com.bhavath.tracker.vos.HealthPacketVO;
import com.bhavath.tracker.vos.ResponseVO;
import com.bhavath.tracker.vos.SendSmsVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SmsCommandController {

	@Autowired
	private ActivationPacketDataService activationPacketDataService;

	@Autowired
	private HealthPacketDataService healthPacketDataService;

	@Autowired
	private TaskRepository taskRepository;

	@ApiOperation(value = "Send SMS Packet", response = ResponseVO.class)
	@RequestMapping(value = "v2/sendSms", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public void sendSms(HttpServletRequest request) {
		log.info("SmsCommandController :: sendSms :: start() ");
		ResponseVO responseVO = new ResponseVO();
		String content = request.getParameter("content");
		String sender = request.getParameter("sender");
		String inNumber = request.getParameter("inNumber");
		String email = request.getParameter("email");
		String credits = request.getParameter("credits");
		SendSmsVO sendSmsVO = SendSmsVO.builder().content(content).email(email).inNumber(inNumber).sender(sender)
				.credits(credits).build();
		log.info("sendSmsVO                  ::::::::::::::  " + sendSmsVO);

		int cursorPos = 0;
		String delims = ",";
		String[] payloadTokens = content.substring(cursorPos).split(delims);

		String header = payloadTokens[0];

		if (header.equalsIgnoreCase("ACTVR")) {
			ActivationPacketVO activationPacketVO = activationPacketDataService.processRequest(content);
			CreateActivationPacketDataEvent event = new CreateActivationPacketDataEvent();
			event.setActivationPacketVO(activationPacketVO);
			activationPacketDataService.save(event);
		} else if (header.equalsIgnoreCase("HCHKR")) {
			HealthPacketVO healthPacketVO = healthPacketDataService.processRequest(content);
			CreateHealthPacketDataEvent event = new CreateHealthPacketDataEvent();
			event.setHealthPacketVO(healthPacketVO);
			healthPacketDataService.save(event);
		} else {
			log.info("SmsCommandController :: sendSms :: content :: start() " + content);
			try {
				// $,Set,353635080728260,AP01EE444,14-03-2019 15:52:30,perdicvalue-regular
				// periodicity value,ok,*
				String imeiNumber = null;
				String headerType = null;
				String commandName = null;
				String[] deviceResponse = null;
				if (content.startsWith("#")) {
					String[] devResp = content.split(",", 3);
					if (devResp[1].equalsIgnoreCase("SET")) {
						deviceResponse = content.split(",", 8);
					}
					if (devResp[1].equalsIgnoreCase("GET") || devResp[1].equalsIgnoreCase("CLR")) {
						deviceResponse = content.split(",", 7);
					}
					headerType = deviceResponse[1].toUpperCase();
					imeiNumber = deviceResponse[2];
					commandName = deviceResponse[5].split("-")[0];
					log.info("Header:" + headerType + ":");
					log.info("IMEI Number:" + imeiNumber + ":");
					log.info("Command Name:" + commandName + ":");
					Task task = taskRepository.findTopOneByImeiNumberAndHeaderAndRequestCommandNameOrderByCreatedDateDesc(
							imeiNumber, headerType, commandName);
					log.info("SmsCommandController :: sendSms :: taskRepo " + task);
					if (!ObjectUtils.isEmpty(task)) {
						task.setResponseCommandName(commandName);
						task.setDeviceResponse(content);
						task.setDeviceResponseTime(DateUtils.getCurrentSystemTimestamp());
						task.setDeviceResponseStatus(true);
						taskRepository.save(task);
					}
				} else {
					log.error("SmsCommandController :: sendSms :: required format is missing :: " + content);
				}
			} catch (Exception e) {
				responseVO.setCode(Constants.ResponseMessages.CODE_500);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_500);
				log.info("Exception while saving Response in Task Details :: " + e.getCause(), e);
			}
			log.info("SmsCommandController :: sendSms :: content :: end() " + content);
		}
	}
}
