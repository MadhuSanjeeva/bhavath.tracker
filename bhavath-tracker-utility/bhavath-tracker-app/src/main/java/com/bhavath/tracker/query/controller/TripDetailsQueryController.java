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

import com.bhavath.tracker.assembler.TripDetailsResourceAssembler;
import com.bhavath.tracker.assembler.TripTrackingResourceAssembler;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadTripDetailsEvent;
import com.bhavath.tracker.events.ReadTripTrackingEvent;
import com.bhavath.tracker.resource.TripDetailsResource;
import com.bhavath.tracker.resource.TripTrackingResource;
import com.bhavath.tracker.services.TripDetailsService;
import com.bhavath.tracker.vos.TripDetailsVO;
import com.bhavath.tracker.vos.TripTrackingVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("v1")
@Slf4j
public class TripDetailsQueryController {
	
	@Autowired private TripDetailsService service;
	@Autowired private TripDetailsResourceAssembler tripDetailsResourceAssembler;
	@Autowired private TripTrackingResourceAssembler tripTrackingResourceAssembler;

	
	@ApiOperation(value = "View list of Trip Details", response = ResponseEntity.class)
	@RequestMapping(value="tripDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<TripDetailsResource>> readTripDetails(
			@RequestParam(value = "imeiNumber", required = false) String imeiNumber,
			@RequestParam(value = "searchDate", required = false) String searchDate,
			@RequestParam(value = "isTripClosed" , required = false) Boolean isTripClosed,
			@RequestParam(value = "tripId", required = false) String tripId,
			@RequestParam(value = "identifier", required = false) String identifier,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<TripDetailsVO> pagedAssembler,HttpServletRequest httpServletRequest) 
		{
		ReadTripDetailsEvent request = new ReadTripDetailsEvent();
		request.setImeiNumber(imeiNumber);
		request.setSearchDate(searchDate);
		request.setIsTripClosed(isTripClosed);
		request.setTripId(tripId);
		request.setIdentifier(identifier);
		request.setSearchValue(searchValue);
		request.setPageable(pageable);
		
		PageReadEvent<TripDetailsVO> event = service.readTripDetails(request);
		Page<TripDetailsVO> page = event.getPage();
		PagedResources<TripDetailsResource> pagedResources = pagedAssembler.toResource(page, tripDetailsResourceAssembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}
	
	@ApiOperation(value = "View list of Trip Tracks", response = ResponseEntity.class)
	@RequestMapping(value="tripTracks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<TripTrackingResource>> readTripTracks(
			@RequestParam(value = "tripId", required = false) Long tripId,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<TripTrackingVO> pagedAssembler,HttpServletRequest httpServletRequest) 
		{
		ReadTripTrackingEvent request = new ReadTripTrackingEvent();
		request.setTripId(tripId);
		request.setPageable(pageable);
		
		PageReadEvent<TripTrackingVO> event = service.readTripTracks(request);
		Page<TripTrackingVO> page = event.getPage();
		PagedResources<TripTrackingResource> pagedResources = pagedAssembler.toResource(page, tripTrackingResourceAssembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

}
