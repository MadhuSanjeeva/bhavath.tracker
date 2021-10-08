package com.bhavath.tracker.resource;


import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleDetailsResource extends ResourceSupport {
    private Long vehicleId;
    private String createdDate;
    private String vehicleRegNumber;
    private String engineNumber;
    private String chassisNumber;
    private String vehicleMake;
    private String vehicleModel;
    private Boolean isDeviceMapped;
    private String deviceMappedDate;
	private String imeiNumber;
	private String mobileNumber;
}