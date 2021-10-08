package com.bhavath.tracker.command.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bhavath.tracker.assembler.TripDetailsResourceAssembler;
import com.bhavath.tracker.events.CreateTripDetailsEvent;
import com.bhavath.tracker.resource.TripDetailsResource;
import com.bhavath.tracker.services.TripDetailsService;
import com.bhavath.tracker.vos.ResponseVO;
import com.bhavath.tracker.vos.TripDetailsVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "v1")
public class TripDetailsCommandController 
{
	@Autowired private TripDetailsService service;
	@Autowired private TripDetailsResourceAssembler  assembler;

	@ApiOperation(value = "Initiating Trip", response = ResponseVO.class)
	@RequestMapping(value = "initiateTrip",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseVO save(@RequestBody TripDetailsResource resource)
	{
		log.info("TripDetailsCommandController :: save :: start() ");
		TripDetailsVO tripDetailsVO = assembler.fromResource(resource);
		CreateTripDetailsEvent event = new CreateTripDetailsEvent().setTripDetailsVO(tripDetailsVO);
		log.info("TripDetailsCommandController :: save :: end() ");
		return service.save(event);
	}
	
	@ApiOperation(value = "End Trip", response = ResponseVO.class)
	@RequestMapping(value = "endTrip",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseVO endTrip(@RequestBody TripDetailsResource resource)
	{
		log.info("TripDetailsCommandController :: endTrip :: start() ");
		TripDetailsVO tripDetailsVO = assembler.fromResource(resource);
		CreateTripDetailsEvent event = new CreateTripDetailsEvent().setTripDetailsVO(tripDetailsVO);
		log.info("TripDetailsCommandController :: endTrip :: end() ");
		return service.endTrip(event);
	}

}
