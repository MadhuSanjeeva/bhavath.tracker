package com.bhavath.tracker.command.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bhavath.tracker.assembler.RouteDeviationResourceAssembler;
import com.bhavath.tracker.events.CreateRouteDeviationEvent;
import com.bhavath.tracker.resource.RouteDeviationResource;
import com.bhavath.tracker.services.RouteDeviationService;
import com.bhavath.tracker.vos.ResponseVO;
import com.bhavath.tracker.vos.RouteDeviationVO;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/routeDeviation")
public class RouteDeviationCommandController {

    @Autowired
    private RouteDeviationResourceAssembler assembler;
    @Autowired
    private RouteDeviationService service;

    @ApiOperation(value = "Create Route Deviation Request", response = ResponseVO.class)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO save(@Valid @RequestBody RouteDeviationResource resource, HttpServletRequest request) throws JsonProcessingException {
    	log.info("RouteDeviationCommandController :: save :: start() ");
        RouteDeviationVO routeDeviationVO = assembler.fromResource(resource);
        CreateRouteDeviationEvent event = new CreateRouteDeviationEvent().setRouteDeviationVO(routeDeviationVO);
        log.info("RouteDeviationCommandController :: save :: end() ");
        return service.save(event);

    }
}