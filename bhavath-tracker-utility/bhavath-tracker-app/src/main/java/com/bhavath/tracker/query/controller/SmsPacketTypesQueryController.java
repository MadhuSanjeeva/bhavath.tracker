package com.bhavath.tracker.query.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bhavath.tracker.events.ReadSmsPacketTypesEvent;
import com.bhavath.tracker.services.TaskDetailsService;
import com.bhavath.tracker.vos.SmsPacketTypesVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("v1")
@Slf4j
public class SmsPacketTypesQueryController {
	
	@Autowired private TaskDetailsService service;

	@ApiOperation(value = "View list of SMS Packet Types", response = ResponseEntity.class)
	@RequestMapping(value="smsPacketTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SmsPacketTypesVO>> summary(
			@RequestParam(value = "commandType", required = true) String commandType)
	{
		ReadSmsPacketTypesEvent request = new ReadSmsPacketTypesEvent();
		request.setCommandType(commandType);
		List<SmsPacketTypesVO>  smsPacketTypesVO = service.readSmsPacketTypes(request);
		return new ResponseEntity<>(smsPacketTypesVO, HttpStatus.OK);
		
	}
}
