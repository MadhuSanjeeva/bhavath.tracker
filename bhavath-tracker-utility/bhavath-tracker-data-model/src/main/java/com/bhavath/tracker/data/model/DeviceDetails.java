package com.bhavath.tracker.data.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "device_details")
@SequenceGenerator(name = "device_details_seq", sequenceName = "device_details_seq",  allocationSize = 1)
public class DeviceDetails implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device_details_seq")
	private Long id;

	@Column(name = "imei_number", unique = true)
	private String imeiNumber;

	@Column(name = "sim_number", unique = true)
	private String simNumber;

	@Column(name = "id_address")
	private String ipAddress;

	@Column(name = "signal_strength")
	private String signalStrength;
	
	@Column(name = "version")
	private String version;
	
	@Column(name = "created_date" ,updatable = false)
	private Timestamp createdDate;
	
	@Column(name = "updated_date")
	private Timestamp updatedDate;
	
    @Column(name = "network_date")
	private String networkDate;
	
	@Column(name = "network_time")
	private String networkTime;
	
	@Column(name = "serial_number", unique=true)
	private String serialNumber;
	
    @Column(name = "vehicle_reg_no", unique=true)
    private String vehicleRegNo;
	
	@OneToMany(mappedBy = "deviceDetails", fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<SIMDetails> simDetails = new HashSet<SIMDetails>();
}
