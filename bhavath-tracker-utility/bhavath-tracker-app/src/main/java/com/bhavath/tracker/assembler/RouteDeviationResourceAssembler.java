package com.bhavath.tracker.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.command.controller.RouteDeviationCommandController;
import com.bhavath.tracker.resource.RouteDeviationResource;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.RouteDeviationVO;

@Component
public class RouteDeviationResourceAssembler extends ResourceAssemblerSupport<RouteDeviationVO, RouteDeviationResource> {
    public RouteDeviationResourceAssembler() {
        super(RouteDeviationCommandController.class, RouteDeviationResource.class);
    }

    @Override
    public RouteDeviationResource toResource(RouteDeviationVO entity) {
        return RouteDeviationResource.builder()
        		.deviationId(entity.getId())
                .location(entity.getLocation())
                .createdDate(!StringUtils.isEmpty(entity.getCreatedDate()) ? DateUitls.getStringFromTimestamp(entity.getCreatedDate()) : null)
                .deviationTime(!StringUtils.isEmpty(entity.getDeviationTime()) ? DateUitls.getStringFromTimestamp(entity.getDeviationTime()) : null)
                .latLang(entity.getLatLang())
                .location(entity.getLocation())
                .imeiNumber(entity.getImeiNumber())
                .tripId(entity.getTripId())
                .build();
    }

    public RouteDeviationVO fromResource(RouteDeviationResource resource) {
        return RouteDeviationVO.builder()
        		.id(resource.getDeviationId())
                .location(resource.getLocation())
                .createdDate(!StringUtils.isEmpty(resource.getCreatedDate()) ? DateUtils.getTimeStampDateFromString(resource.getCreatedDate()) : null)
                .deviationTime(!StringUtils.isEmpty(resource.getDeviationTime()) ? DateUtils.getTimeStampDateFromString(resource.getDeviationTime()) : null)
                .latLang(resource.getLatLang())
                .location(resource.getLocation())
                .imeiNumber(resource.getImeiNumber())
                .tripId(resource.getTripId())
                .build();
    }
}