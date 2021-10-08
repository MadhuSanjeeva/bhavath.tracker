package com.bhavath.tracker.command.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bhavath.tracker.assembler.VehicleDetailsResourceAssembler;
import com.bhavath.tracker.events.CreateVehicleDetailsEvent;
import com.bhavath.tracker.resource.VehicleDetailsResource;
import com.bhavath.tracker.services.VehicleDetailsService;
import com.bhavath.tracker.vos.ResponseVO;
import com.bhavath.tracker.vos.VehicleDetailsVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "v1")
public class VehicleDetailsCommandController {
    @Autowired
    private VehicleDetailsService service;
    @Autowired
    private VehicleDetailsResourceAssembler assembler;

    @ApiOperation(value = "Create Vehicle Command Controller", response = ResponseVO.class)
    @RequestMapping(value = "createVehicle", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVO createVehicle(@RequestBody VehicleDetailsResource resource) {
    	log.info("VehicleCommandController :: createVehicle :: start() ");
        VehicleDetailsVO vehicleDetailsVO = assembler.fromResource(resource);
        CreateVehicleDetailsEvent event = new CreateVehicleDetailsEvent().setVehicleDetailsVO(vehicleDetailsVO);
        log.info("VehicleCommandController :: createVehicle :: end() ");
        return service.save(event);
    }
    
    @ApiOperation(value = "Map IMEI Number with Vehicle", response = ResponseVO.class)
	@RequestMapping(value = "mapImeiVehicle",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseVO mapIoTDevice(@RequestBody VehicleDetailsResource resource)
	{
    	log.info("VehicleCommandController :: mapIoTDevice :: start() ");
    	VehicleDetailsVO vehicleDetailsVO = assembler.fromResource(resource);
    	CreateVehicleDetailsEvent event = new CreateVehicleDetailsEvent().setVehicleDetailsVO(vehicleDetailsVO);
    	log.info("VehicleCommandController :: mapIoTDevice :: end() ");
		return service.mapImeiVehicle(event);
	}

}
