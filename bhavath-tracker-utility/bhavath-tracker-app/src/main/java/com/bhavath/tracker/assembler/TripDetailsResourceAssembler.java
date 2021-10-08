package com.bhavath.tracker.assembler;

import javax.validation.Valid;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.command.controller.TripDetailsCommandController;
import com.bhavath.tracker.resource.TripDetailsResource;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.TripDetailsVO;

@Component
public class TripDetailsResourceAssembler extends ResourceAssemblerSupport<TripDetailsVO, TripDetailsResource> {
    public TripDetailsResourceAssembler() {
        super(TripDetailsCommandController.class, TripDetailsResource.class);
    }

    @Override
    public TripDetailsResource toResource(TripDetailsVO entity) {
        return TripDetailsResource.builder()
                .tripId(entity.getId())
                .createdDate(!StringUtils.isEmpty(entity.getCreatedDate()) ? DateUitls.getStringFromTimestamp(entity.getCreatedDate()) : null)
                .imeiNumber(entity.getImeiNumber())
                .sourceLocation(entity.getSourceLocation())
                .destiLocation(entity.getDestiLocation())
                .sourceLatLang(entity.getSourceLatLang())
                .destiLatLang(entity.getDestiLatLang())
                .closeTime(!StringUtils.isEmpty(entity.getCloseTime()) ? DateUitls.getStringFromTimestamp(entity.getCloseTime()) : null)
                .tripClosed(entity.getTripClosed())
                .radius(entity.getRadius())
                .identifier(entity.getIdentifier())
                .build();
    }

    public TripDetailsVO fromResource(@Valid TripDetailsResource resource) {
        return TripDetailsVO.builder()
        		.id(resource.getTripId())
        		.createdDate(!StringUtils.isEmpty(resource.getCreatedDate()) ? DateUtils.getTimeStampDateFromString(resource.getCreatedDate()) : null)
                .imeiNumber(resource.getImeiNumber())
                .sourceLocation(resource.getSourceLocation())
                .destiLocation(resource.getDestiLocation())
                .sourceLatLang(resource.getSourceLatLang())
                .destiLatLang(resource.getDestiLatLang())
                .closeTime(!StringUtils.isEmpty(resource.getCloseTime()) ? DateUtils.getTimeStampDateFromString(resource.getCloseTime()) : null)
                .tripClosed(resource.getTripClosed())
                .radius(resource.getRadius())
                .identifier(resource.getIdentifier())
                .build();
    }
}