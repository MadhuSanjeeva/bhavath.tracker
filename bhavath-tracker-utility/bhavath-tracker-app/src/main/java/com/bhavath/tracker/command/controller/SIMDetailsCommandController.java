package com.bhavath.tracker.command.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bhavath.tracker.assembler.SimDetailsResourceAssembler;
import com.bhavath.tracker.events.CreateSIMDetailsEvent;
import com.bhavath.tracker.resource.SIMDetailsResource;
import com.bhavath.tracker.services.SimDetailsService;
import com.bhavath.tracker.vos.ResponseVO;
import com.bhavath.tracker.vos.SIMDetailsVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "v1")
public class SIMDetailsCommandController 
{
	@Autowired private SimDetailsService service;
	@Autowired private SimDetailsResourceAssembler simDetailsAssembler;

	@ApiOperation(value = "Save Device Details", response = ResponseVO.class)
	@RequestMapping(value = "simDetails",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseVO save(@RequestBody List<SIMDetailsResource> resources,HttpServletRequest request)
	{
		log.info("SIMDetailsCommandController :: save :: start() ");
		List<SIMDetailsVO> listOfSims = simDetailsAssembler.fromResource(resources);
		CreateSIMDetailsEvent event = new CreateSIMDetailsEvent().setSimDetailsVO(listOfSims);
		log.info("SIMDetailsCommandController :: save :: end() ");
		return service.save(event);
	}

}
