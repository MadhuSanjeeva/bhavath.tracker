package com.bhavath.tracker.query.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bhavath.tracker.assembler.DeviceCommunicationResourceAssembler;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadDeviceCommunicationEvent;
import com.bhavath.tracker.resource.DeviceCommunicationResource;
import com.bhavath.tracker.services.DeviceCommunicationService;
import com.bhavath.tracker.utils.DeviceCommunicationSummaryVO;
import com.bhavath.tracker.utils.DeviceCommunicationVehicleModeSummaryVO;
import com.bhavath.tracker.vos.DeviceCommunicationVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1")
public class DeviceCommunicationQueryController 
{
	
	@Autowired private DeviceCommunicationService service;
	@Autowired private DeviceCommunicationResourceAssembler assembler;

	@ApiOperation(value = "View list of Device Communication Details", response = ResponseEntity.class)
	@RequestMapping(value="deviceCommunication", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<DeviceCommunicationResource>> readModemDeviceData(
			@RequestParam(value = "imeiNumber", required = false) String imeiNumber,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "searchDate", required = false) String searchDate,
			@RequestParam(value = "vehicleMode", required = false) String vehicleMode,
			@RequestParam(value = "ignitionStatus", required = false) String ignitionStatus,
			@RequestParam(value = "emergencyStatus", required = false) String emergencyStatus,
			@RequestParam(value = "mainPowerStatus", required = false) String mainPowerStatus,
			@RequestParam(value = "tamperAlert", required = false) String tamperAlert,
			@RequestParam(value = "memoryPercentage", required = false) String memoryPercentage,
			@RequestParam(value = "batteryPercentage", required = false) String batteryPercentage,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<DeviceCommunicationVO> pagedAssembler)
	{
		ReadDeviceCommunicationEvent request = new ReadDeviceCommunicationEvent();
		request.setImeiNumber(imeiNumber);
		request.setStatus(status);
		request.setSearchDate(searchDate);
		request.setVehicleMode(vehicleMode);
		request.setIgnitionStatus(ignitionStatus);
		request.setEmergencyStatus(emergencyStatus);
		request.setMainPowerStatus(mainPowerStatus);
		request.setTamperAlert(tamperAlert);
		request.setMemoryPercentage(memoryPercentage);
		request.setBatteryPercentage(batteryPercentage);
		request.setPageable(pageable);
		PageReadEvent<DeviceCommunicationVO> event = service.readData(request);
		Page<DeviceCommunicationVO> page = event.getPage();
		PagedResources<DeviceCommunicationResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
	
	@ApiOperation(value = "View list of Device Communication History", response = ResponseEntity.class)
	@RequestMapping(value="deviceCommHistory", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<DeviceCommunicationResource>> readDeviceCommHistoryData(
			@RequestParam(value = "imeiNumber", required = false) String imeiNumber,
			@RequestParam(value = "networkDate", required = false) String networkDate,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<DeviceCommunicationVO> pagedAssembler)
	{
		ReadDeviceCommunicationEvent request = new ReadDeviceCommunicationEvent();
		request.setImeiNumber(imeiNumber);
		request.setNetworkDate(networkDate);
		request.setPageable(pageable);
		PageReadEvent<DeviceCommunicationVO> event = service.readDeviceCommHistoryData(request);
		Page<DeviceCommunicationVO> page = event.getPage();
		PagedResources<DeviceCommunicationResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

	@ApiOperation(value = "View list of Device Communication Details", response = ResponseEntity.class)
	@RequestMapping(value="deviceCommunication/summary", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DeviceCommunicationSummaryVO>> summary(
			@RequestParam(value = "action", required = true) String[] actions)
	{
		List<DeviceCommunicationSummaryVO>  listOfDeviceCommunicationSummaryVO = service.readSummary(actions);
		return new ResponseEntity<>(listOfDeviceCommunicationSummaryVO, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "View list of Device Communication Vehicle Mode Summary", response = ResponseEntity.class)
	@RequestMapping(value="deviceComm/vehicleModeSummary", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DeviceCommunicationVehicleModeSummaryVO>> readVehicleModeSummary(
			@RequestParam(value = "action", required = false) String action)
	{
		List<DeviceCommunicationVehicleModeSummaryVO>  listOfVehicleModeVO = service.readVehicleModeSummary();
		return new ResponseEntity<>(listOfVehicleModeVO, HttpStatus.OK);
		
	}
}
