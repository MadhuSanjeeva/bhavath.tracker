package com.bhavath.tracker.command.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bhavath.tracker.assembler.SystemPropertiesAssembler;
import com.bhavath.tracker.events.CreateSystemPropertiesEvent;
import com.bhavath.tracker.resource.SystemPropertiesResource;
import com.bhavath.tracker.services.SystemPropertiesService;
import com.bhavath.tracker.vos.ResponseVO;
import com.bhavath.tracker.vos.SystemPropertiesVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "v1/systemProperties")
public class SystemPropertiesCommandController {

	@Autowired
	SystemPropertiesAssembler assembler;
	@Autowired
	SystemPropertiesService service;
	
	@ApiOperation(value = "Create System Properties", response = ResponseVO.class)
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseVO save(@RequestBody SystemPropertiesResource resource)
	{
		log.info("SystemPropertiesCommandController :: save :: start() ");
		SystemPropertiesVO systemPropertiesVO = assembler.fromResource(resource);
		CreateSystemPropertiesEvent event = new CreateSystemPropertiesEvent();
		event.setSystemPropertiesVO(systemPropertiesVO);
		log.info("SystemPropertiesCommandController :: save :: end() ");
		return service.save(event);
	}
	
	@ApiOperation(value = "Update System Properties", response = ResponseVO.class)
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseVO update(@RequestBody SystemPropertiesResource resource)
	{
		log.info("SystemPropertiesCommandController :: save :: start() ");
		SystemPropertiesVO systemPropertiesVO = assembler.fromResource(resource);
		CreateSystemPropertiesEvent event = new CreateSystemPropertiesEvent();
		event.setSystemPropertiesVO(systemPropertiesVO);
		log.info("SystemPropertiesCommandController :: save :: end() ");
		return service.save(event);
	}
}
