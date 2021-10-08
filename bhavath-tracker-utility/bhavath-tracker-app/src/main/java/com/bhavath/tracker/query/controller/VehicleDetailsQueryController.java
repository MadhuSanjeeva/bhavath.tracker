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

import com.bhavath.tracker.assembler.VehicleDetailsResourceAssembler;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadVehicleDetailsEvent;
import com.bhavath.tracker.resource.VehicleDetailsResource;
import com.bhavath.tracker.services.VehicleDetailsService;
import com.bhavath.tracker.vos.VehicleDetailsVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1")
public class VehicleDetailsQueryController {

    @Autowired
    private VehicleDetailsService service;
    @Autowired
    private VehicleDetailsResourceAssembler assembler;

    @ApiOperation(value = "View list of Vehicle Details", response = ResponseEntity.class)
    @RequestMapping(value = "vehicleDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedResources<VehicleDetailsResource>> vehicleDetails(
            @RequestParam(value = "searchValue", required = false) String searchValue,
            @RequestParam(value = "searchDate", required = false) String searchDate,
			@RequestParam(value = "columnName", required = false) String columnName,
			@RequestParam(value = "columnOrder", required = false) String columnOrder,
            @PageableDefault(value = Integer.MAX_VALUE) Pageable pageable, PagedResourcesAssembler<VehicleDetailsVO> pagedAssembler, HttpServletRequest httpServletRequest) {
        ReadVehicleDetailsEvent request = new ReadVehicleDetailsEvent();
        request.setSearchValue(searchValue);
        request.setSearchDate(searchDate);
        request.setColumnName(columnName);
        request.setColumnOrder(columnOrder);
        request.setPageable(pageable);

        PageReadEvent<VehicleDetailsVO> event = service.readVehicleData(request);
        Page<VehicleDetailsVO> page = event.getPage();
        PagedResources<VehicleDetailsResource> pagedResources = pagedAssembler.toResource(page, assembler);
        return new ResponseEntity<>(pagedResources, HttpStatus.OK);
    }


}
