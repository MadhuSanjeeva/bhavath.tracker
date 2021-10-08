package com.bhavath.tracker.assembler;

import javax.validation.Valid;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.command.controller.VehicleDetailsCommandController;
import com.bhavath.tracker.resource.VehicleDetailsResource;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.VehicleDetailsVO;

@Component
public class VehicleDetailsResourceAssembler extends ResourceAssemblerSupport<VehicleDetailsVO, VehicleDetailsResource> {
    public VehicleDetailsResourceAssembler() {
        super(VehicleDetailsCommandController.class, VehicleDetailsResource.class);
    }

    @Override
    public VehicleDetailsResource toResource(VehicleDetailsVO entity) {
        return VehicleDetailsResource.builder()
                .vehicleId(entity.getId())
                .createdDate(!StringUtils.isEmpty(entity.getCreatedDate()) ? DateUitls.getStringFromTimestamp(entity.getCreatedDate()) : null)
                .vehicleRegNumber(entity.getVehicleRegNumber())
                .engineNumber(entity.getEngineNumber())
                .chassisNumber(entity.getChassisNumber())
                .vehicleMake(entity.getVehicleMake())
                .vehicleModel(entity.getVehicleModel())
                .imeiNumber(entity.getImeiNumber())
                .mobileNumber(entity.getMobileNumber())
                .isDeviceMapped(entity.getIsDeviceMapped())
                .deviceMappedDate(!StringUtils.isEmpty(entity.getDeviceMappedDate()) ? DateUitls.getStringFromTimestamp(entity.getDeviceMappedDate()) : null)
                .build();
    }

    public VehicleDetailsVO fromResource(@Valid VehicleDetailsResource resource) {
        return VehicleDetailsVO.builder()
                .id(resource.getVehicleId())
                .createdDate(!StringUtils.isEmpty(resource.getCreatedDate()) ? DateUtils.getTimeStampDateFromString(resource.getCreatedDate()) : null)
                .vehicleRegNumber(resource.getVehicleRegNumber())
                .engineNumber(resource.getEngineNumber())
                .chassisNumber(resource.getChassisNumber())
                .vehicleMake(resource.getVehicleMake())
                .vehicleModel(resource.getVehicleModel())
                .imeiNumber(resource.getImeiNumber())
                .mobileNumber(resource.getMobileNumber())
                .isDeviceMapped(!StringUtils.isEmpty(resource.getIsDeviceMapped())  ? resource.getIsDeviceMapped() : false)
				.deviceMappedDate(!StringUtils.isEmpty(resource.getDeviceMappedDate()) ? DateUtils.getTimeStampDateFromString(resource.getDeviceMappedDate()) : null)
                .build();
    }
}