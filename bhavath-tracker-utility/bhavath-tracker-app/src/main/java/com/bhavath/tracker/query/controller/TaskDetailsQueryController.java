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

import com.bhavath.tracker.assembler.TaskResourceAssembler;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadTaskCommandEvent;
import com.bhavath.tracker.resource.TaskResource;
import com.bhavath.tracker.services.TaskDetailsService;
import com.bhavath.tracker.vos.TaskVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("v1")
@Slf4j
public class TaskDetailsQueryController {
	
	@Autowired private TaskDetailsService service;
	@Autowired private TaskResourceAssembler assembler;

	
	@ApiOperation(value = "View list of Tasks", response = ResponseEntity.class)
	@RequestMapping(value="taskDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<TaskResource>> readLMPPacketData(
			@RequestParam(value = "imeiNumber", required = false) String imeiNumber,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchDate", required = false) String searchDate,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<TaskVO> pagedAssembler,HttpServletRequest httpServletRequest) 
		{
		ReadTaskCommandEvent request = new ReadTaskCommandEvent();
		request.setImeiNumber(imeiNumber);
		request.setSearchValue(searchValue);
		request.setSearchDate(searchDate);
		request.setPageable(pageable);

		PageReadEvent<TaskVO> event = service.readData(request);
		Page<TaskVO> page = event.getPage();
		PagedResources<TaskResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

}
