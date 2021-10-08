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

import com.bhavath.tracker.assembler.EBPPacketDataResourceAssembler;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadEBPPacketDataSetEvent;
import com.bhavath.tracker.resource.EBPPacketDataResource;
import com.bhavath.tracker.services.EBPPacketDataService;
import com.bhavath.tracker.vos.EBPPacketDataVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("v1")
@Slf4j
public class EBPPacketDataQueryController {
	
	@Autowired private EBPPacketDataService service;
	@Autowired private EBPPacketDataResourceAssembler assembler;

	
	@ApiOperation(value = "View list of EBPPacketData", response = ResponseEntity.class)
	@RequestMapping(value="ebpPacketData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<EBPPacketDataResource>> readLMPPacketData(
			@RequestParam(value = "imeiNumber", required = false) String imeiNumber,
			@RequestParam(value = "vehicleRegNo", required = false) String vehicleRegNo,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<EBPPacketDataVO> pagedAssembler,HttpServletRequest httpServletRequest) 
		{
		ReadEBPPacketDataSetEvent request = new ReadEBPPacketDataSetEvent();
		request.setImeiNumber(imeiNumber);
		request.setVehicleRegNo(vehicleRegNo);
		request.setStartDate(startDate);
		request.setEndDate(endDate);
		request.setSearchValue(searchValue);
		request.setPageable(pageable);

		PageReadEvent<EBPPacketDataVO> event = service.readData(request);
		Page<EBPPacketDataVO> page = event.getPage();
		PagedResources<EBPPacketDataResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

}
