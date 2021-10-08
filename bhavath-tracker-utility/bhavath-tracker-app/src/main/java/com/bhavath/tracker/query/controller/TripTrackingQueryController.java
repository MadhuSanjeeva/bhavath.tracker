package com.bhavath.tracker.query.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("v1")
@Slf4j
public class TripTrackingQueryController {
	
	/*@Autowired private TripDetailsService service;
	@Autowired private TripDetailsResourceAssembler assembler;

	
	@ApiOperation(value = "View list of Trip Tracking Details", response = ResponseEntity.class)
	@RequestMapping(value="tripTrackingDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<TripDetailsResource>> readTripDetails(
			@RequestParam(value = "imeiNumber", required = false) String imeiNumber,
			@RequestParam(value = "searchDate", required = false) String searchDate,
			@PageableDefault(value = Integer.MAX_VALUE) Pageable pageable,PagedResourcesAssembler<TripDetailsVO> pagedAssembler,HttpServletRequest httpServletRequest) 
		{
		ReadTripDetailsEvent request = new ReadTripDetailsEvent();
		request.setImeiNumber(imeiNumber);
		request.setSearchDate(searchDate);
		request.setPageable(pageable);
		
		PageReadEvent<TripDetailsVO> event = service.readTripDetails(request);
		Page<TripDetailsVO> page = event.getPage();
		PagedResources<TripDetailsResource> pagedResources = pagedAssembler.toResource(page, assembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}*/

}
