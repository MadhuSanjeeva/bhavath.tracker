package com.bhavath.tracker.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bhavath.tracker.assembler.SimDetailsResourceAssembler;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadSIMDetailsEvent;
import com.bhavath.tracker.services.SimDetailsService;
import com.bhavath.tracker.vos.DeviceDetailsVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/deviceDetails")
public class DeviceDetailsQueryController
{
	@Autowired private SimDetailsService service;
	@Autowired private SimDetailsResourceAssembler assembler;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "View list of Device SIM Details", response = ResponseEntity.class)
	public ResponseEntity<PagedResources<Resource<DeviceDetailsVO>>> readDeviceDetails(
			@RequestParam(value = "imeiNumber", required = false) String imeiNumber,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchDate", required = false) String searchDate,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<DeviceDetailsVO> pagedAssembler)
	{
		ReadSIMDetailsEvent request = new ReadSIMDetailsEvent();
		request.setImeiNumber(imeiNumber);
		request.setSearchValue(searchValue);
		request.setSearchDate(searchDate);
		request.setPageable(pageable);
		PageReadEvent<DeviceDetailsVO> event = service.readData(request);
		Page<DeviceDetailsVO> page = event.getPage();
		PagedResources<Resource<DeviceDetailsVO>> pagedResources = pagedAssembler.toResource(page);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
}
