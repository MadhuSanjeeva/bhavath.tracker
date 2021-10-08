package com.bhavath.tracker.assembler;

import javax.validation.Valid;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.query.controller.TripTrackingQueryController;
import com.bhavath.tracker.resource.TripTrackingResource;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.TripTrackingVO;

@Component
public class TripTrackingResourceAssembler extends ResourceAssemblerSupport<TripTrackingVO, TripTrackingResource> {
    public TripTrackingResourceAssembler() {
        super(TripTrackingQueryController.class, TripTrackingResource.class);
    }

    @Override
    public TripTrackingResource toResource(TripTrackingVO entity) {
        return TripTrackingResource.builder()
                .tripTrackingId(entity.getId())
                .createdDate(!StringUtils.isEmpty(entity.getCreatedDate()) ? DateUitls.getStringFromTimestamp(entity.getCreatedDate()) : null)
				.imeiNumber(entity.getImeiNumber())
				.latitude(entity.getLatitude())
				.longitude(entity.getLongitude())
				.location(entity.getLocation())
				.latitudeDirection(entity.getLatitudeDirection())
				.longitudeDirection(entity.getLongitudeDirection())
				.networkDate(entity.getNetworkDate())
				.networkTime(entity.getNetworkTime())
				.tripId(entity.getTripId())
                .build();
    }

    public TripTrackingVO fromResource(@Valid TripTrackingResource resource) {
        return TripTrackingVO.builder()
        		.id(resource.getTripTrackingId())
        		.createdDate(!StringUtils.isEmpty(resource.getCreatedDate()) ? DateUtils.getTimeStampDateFromString(resource.getCreatedDate()) : null)
				.imeiNumber(resource.getImeiNumber())
				.latitude(resource.getLatitude())
				.longitude(resource.getLongitude())
				.location(resource.getLocation())
				.latitudeDirection(resource.getLatitudeDirection())
				.longitudeDirection(resource.getLongitudeDirection())
				.networkDate(resource.getNetworkDate())
				.networkTime(resource.getNetworkTime())
				.tripId(resource.getTripId())
                .build();
    }
}