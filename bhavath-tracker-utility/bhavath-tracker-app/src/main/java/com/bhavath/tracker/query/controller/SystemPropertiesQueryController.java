package com.bhavath.tracker.query.controller;

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

import com.bhavath.tracker.assembler.SystemPropertiesAssembler;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadSystemPropertiesEvent;
import com.bhavath.tracker.resource.SystemPropertiesResource;
import com.bhavath.tracker.services.SystemPropertiesService;
import com.bhavath.tracker.vos.SystemPropertiesVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/systemProperties")
public class SystemPropertiesQueryController {
	
	@Autowired
	private SystemPropertiesService service;
	
	@Autowired
	private SystemPropertiesAssembler assembler;
	
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "View list of System Properties", response = ResponseEntity.class)
	public ResponseEntity<PagedResources<SystemPropertiesResource>> readModemDeviceData(
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchDate", required = false) String searchDate,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<SystemPropertiesVO> pagedAssembler)
	{
		ReadSystemPropertiesEvent request = new ReadSystemPropertiesEvent();
		 request.setSearchValue(searchValue);
		 request.setSearchDate(searchDate);
		request.setPageable(pageable);
		PageReadEvent<SystemPropertiesVO> event = service.readData(request);
		Page<SystemPropertiesVO> page = event.getPage();
		PagedResources<SystemPropertiesResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
	

}
