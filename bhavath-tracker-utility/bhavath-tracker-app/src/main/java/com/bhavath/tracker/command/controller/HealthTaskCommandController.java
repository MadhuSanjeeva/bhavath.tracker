package com.bhavath.tracker.command.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bhavath.tracker.assembler.TaskResourceAssembler;
import com.bhavath.tracker.events.CreateTaskCommandEvent;
import com.bhavath.tracker.resource.TaskResource;
import com.bhavath.tracker.services.TaskDetailsService;
import com.bhavath.tracker.vos.ResponseVO;
import com.bhavath.tracker.vos.TaskVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "v1")
public class HealthTaskCommandController 
{
	@Autowired private TaskDetailsService service;
	@Autowired private TaskResourceAssembler  assembler;

	@ApiOperation(value = "Create Health Task Command Settings", response = ResponseVO.class)
	@RequestMapping(value = "createHealthTask",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseVO save(@RequestBody TaskResource resource)
	{
		log.info("HealthTaskCommandController :: save :: start() ");
		TaskVO taskVO = assembler.fromResource(resource);
		CreateTaskCommandEvent event = new CreateTaskCommandEvent();
		event.setTaskVO(taskVO);
		log.info("HealthTaskCommandController :: save :: end() ");
		return service.save(event);
	}

}
