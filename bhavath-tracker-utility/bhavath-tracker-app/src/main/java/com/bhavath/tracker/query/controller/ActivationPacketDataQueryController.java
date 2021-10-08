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

import com.bhavath.tracker.assembler.ActivationPacketDataResourceAssembler;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadActivationPacketDataSetEvent;
import com.bhavath.tracker.resource.ActivationPacketDataResource;
import com.bhavath.tracker.services.ActivationPacketDataService;
import com.bhavath.tracker.vos.ActivationPacketVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("v1")
@Slf4j
public class ActivationPacketDataQueryController {
	
	@Autowired private ActivationPacketDataService activationPacketDataService;
	@Autowired private ActivationPacketDataResourceAssembler assembler;

	
	@ApiOperation(value = "View list of ActivationPacketData", response = ResponseEntity.class)
	@RequestMapping(value="activationPacketData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<ActivationPacketDataResource>> readHMPPacketData(
			@RequestParam(value = "imeiNumber", required = false) String imeiNumber,
			@RequestParam(value = "searchDate", required = false) String searchDate,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<ActivationPacketVO> pagedAssembler,HttpServletRequest httpServletRequest) 
		{
		ReadActivationPacketDataSetEvent request = new ReadActivationPacketDataSetEvent();
		request.setImeiNumber(imeiNumber);
		request.setSearchDate(searchDate);
		request.setPageable(pageable);
		
		PageReadEvent<ActivationPacketVO> event = activationPacketDataService.readData(request);
		Page<ActivationPacketVO> page = event.getPage();
		PagedResources<ActivationPacketDataResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

}
