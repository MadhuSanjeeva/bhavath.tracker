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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bhavath.tracker.assembler.RawPacketDataResourceAssembler;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadRawDataSetEvent;
import com.bhavath.tracker.resource.RawPacketDataResource;
import com.bhavath.tracker.services.RawDataPacketService;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.RawDataPacketVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/rawData")
public class RawPacketDataQueryController 
{
	
	@Autowired private RawDataPacketService service;
	@Autowired private RawPacketDataResourceAssembler assembler;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "View list of Raw Packet Details", response = ResponseEntity.class)
	public ResponseEntity<PagedResources<RawPacketDataResource>> readRawPacketData(
			@RequestParam(value = "imeiNumber", required = false) String imeiNumber,
			@RequestParam(value = "searchDate", required = false) String searchDate,
			@RequestParam(value = "inBoundChannel", required = false) String inBoundChannel,
			@RequestParam(value = "cvpType", required = false) String cvpType,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "packetStatus", required = false) String packetStatus,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<RawDataPacketVO> pagedAssembler)
	{
		ReadRawDataSetEvent request = new ReadRawDataSetEvent();
		request.setImeiNumber(imeiNumber);
		request.setStartDate(!StringUtils.isEmpty(startDate) ? DateUtils.getSqlDateFromString(startDate): null);
		request.setEndDate(!StringUtils.isEmpty(endDate) ? DateUtils.getSqlDateFromString(endDate): null);
		request.setPacketStatus(packetStatus);
		request.setPageable(pageable);
		request.setInBoundChannel(inBoundChannel);
		request.setCvpType(cvpType);
		PageReadEvent<RawDataPacketVO> event = service.readData(request);
		Page<RawDataPacketVO> page = event.getPage();
		PagedResources<RawPacketDataResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
}
