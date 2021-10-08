package com.bhavath.tracker.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(name = "vehicle_details_seq", sequenceName = "vehicle_details_seq", allocationSize = 1)
@Table(name = "vehicle_details")
public class VehicleDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "vehicle_details_seq")
    private Long id;
    
    @Column(name = "created_date", nullable = false)
	private Timestamp createdDate;

    @Column(name = "vehicle_reg_number",nullable = false, unique=true)
    private String vehicleRegNumber;

    @Column(name = "engine_number",nullable = false, unique=true)
    private String engineNumber;

    @Column(name = "chassis_number",nullable = false, unique=true)
    private String chassisNumber;

    @Column(name = "vehicle_make")
    private String vehicleMake;

    @Column(name = "vehicle_model")
    private String vehicleModel;
    
    @Column(name = "is_device_mapped" ,nullable = false)
	private Boolean isDeviceMapped;
    
    @Column(name = "device_mapped_date")
	private Timestamp deviceMappedDate;

	@Column(name = "imei_number")
	private String imeiNumber;
	
	@Column(name="mobile_number")
	private String mobileNumber;
}
