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

import com.bhavath.tracker.assembler.LMPPacketDataResourceAssembler;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadLMPPacketDataSetEvent;
import com.bhavath.tracker.resource.LMPPacketDataResource;
import com.bhavath.tracker.services.LMPPacketDataService;
import com.bhavath.tracker.vos.LMPPacketDataVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("v1")
@Slf4j
public class LMPPacketDataQueryController {
	
	@Autowired private LMPPacketDataService service;
	@Autowired private LMPPacketDataResourceAssembler assembler;

	
	@ApiOperation(value = "View list of LMPPacketData", response = ResponseEntity.class)
	@RequestMapping(value="lmpPacketData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<LMPPacketDataResource>> readLMPPacketData(
			@RequestParam(value = "imeiNumber", required = false) String imeiNumber,
			@RequestParam(value = "vehicleRegNo", required = false) String vehicleRegNo,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<LMPPacketDataVO> pagedAssembler,HttpServletRequest httpServletRequest) 
		{
		ReadLMPPacketDataSetEvent request = new ReadLMPPacketDataSetEvent();
		request.setImei(imeiNumber);
		request.setVehicleRegNo(vehicleRegNo);
		request.setSearchValue(searchValue);
		request.setStartDate(startDate);
		request.setEndDate(endDate);
		request.setPageable(pageable);

		PageReadEvent<LMPPacketDataVO> event = service.readData(request);
		Page<LMPPacketDataVO> page = event.getPage();
		PagedResources<LMPPacketDataResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

}
