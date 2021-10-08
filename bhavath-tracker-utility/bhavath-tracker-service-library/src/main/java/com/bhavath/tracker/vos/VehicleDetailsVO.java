package com.bhavath.tracker.vos;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
@JsonInclude(Include.NON_DEFAULT)
public class VehicleDetailsVO implements Serializable
{
	private static final long serialVersionUID = 1L;
    private Long id;
    private Timestamp createdDate;
    private String vehicleRegNumber;
    private String engineNumber;
    private String chassisNumber;
    private String vehicleMake;
    private String vehicleModel;
    private Boolean isDeviceMapped;
    private Timestamp deviceMappedDate;
	private String imeiNumber;
	private String mobileNumber;
}
