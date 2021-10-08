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

import com.bhavath.tracker.assembler.RouteDeviationResourceAssembler;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadRouteDeviationSetEvent;
import com.bhavath.tracker.resource.RouteDeviationResource;
import com.bhavath.tracker.services.RouteDeviationService;
import com.bhavath.tracker.vos.RouteDeviationVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/routeDeviation")
public class RouteDeviationQueryController {
    @Autowired
    private RouteDeviationService service;
    @Autowired
    private RouteDeviationResourceAssembler assembler;

    @ApiOperation(value = "View list of Route Deviations", response = ResponseEntity.class)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedResources<RouteDeviationResource>> readData(
    		@RequestParam(value = "searchValue", required = false) String searchValue,
    		@RequestParam(value = "searchDate", required = false) String searchDate,
            @PageableDefault(value = Integer.MAX_VALUE) Pageable pageable, PagedResourcesAssembler<RouteDeviationVO> pagedAssembler) {
        ReadRouteDeviationSetEvent request = new ReadRouteDeviationSetEvent();
        request.setSearchValue(searchValue);
        request.setSearchDate(searchDate);
        request.setPageable(pageable);

        PageReadEvent<RouteDeviationVO> event = service.readDeviationData(request);
        Page<RouteDeviationVO> page = event.getPage();
        PagedResources<RouteDeviationResource> pagedResources = pagedAssembler.toResource(page, assembler);
        return new ResponseEntity<>(pagedResources, HttpStatus.OK);
    }
}
