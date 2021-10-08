package com.bhavath.tracker.query.controller;

import javax.servlet.http.HttpServletRequest;

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

import com.bhavath.tracker.assembler.HMPPacketDataResourceAssembler;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadHMPPacketDataSetEvent;
import com.bhavath.tracker.resource.HMPPacketDataResource;
import com.bhavath.tracker.services.HMPPacketDataService;
import com.bhavath.tracker.vos.HMPPacketDataVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("v1")
@Slf4j
public class HMPPacketDataQueryController {
	
	@Autowired private HMPPacketDataService service;
	@Autowired private HMPPacketDataResourceAssembler assembler;

	
	@ApiOperation(value = "View list of HMPPacketData", response = ResponseEntity.class)
	@RequestMapping(value="hmpPacketData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<HMPPacketDataResource>> readHMPPacketData(
			@RequestParam(value = "imeiNumber", required = false) String imeiNumber,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<HMPPacketDataVO> pagedAssembler,HttpServletRequest httpServletRequest) 
		{
		ReadHMPPacketDataSetEvent request = new ReadHMPPacketDataSetEvent();
		request.setImeiNumber(imeiNumber);
		request.setStartDate(startDate);
		request.setEndDate(endDate);
		request.setPageable(pageable);

		PageReadEvent<HMPPacketDataVO> event = service.readData(request);
		Page<HMPPacketDataVO> page = event.getPage();
		PagedResources<HMPPacketDataResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

}
