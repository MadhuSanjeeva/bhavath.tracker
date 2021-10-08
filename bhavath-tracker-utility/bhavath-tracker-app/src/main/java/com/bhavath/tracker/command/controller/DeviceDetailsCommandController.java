package com.bhavath.tracker.command.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bhavath.tracker.assembler.DeviceDetailsResourceAssembler;
import com.bhavath.tracker.events.CreateDeviceDetEvent;
import com.bhavath.tracker.resource.DeviceDetailsResource;
import com.bhavath.tracker.services.DeviceDetailsService;
import com.bhavath.tracker.vos.DeviceDetailsVO;
import com.bhavath.tracker.vos.ResponseVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/deviceDetails")
public class DeviceDetailsCommandController {

    @Autowired
    private DeviceDetailsResourceAssembler assembler;
    @Autowired
    private DeviceDetailsService service;

    @ApiOperation(value = "Add Device", response = ResponseVO.class)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO save(@Valid @RequestBody DeviceDetailsResource resource, HttpServletRequest request) throws IOException {
    	log.info("DeviceDetailsCommandController :: save :: start() ");
        DeviceDetailsVO deviceDetailsVO = assembler.fromResource(resource);
        CreateDeviceDetEvent event = new CreateDeviceDetEvent().setDeviceDetailsVO(deviceDetailsVO);
        log.info("DeviceDetailsCommandController :: save :: end() ");
        return service.save(event);
    }
}