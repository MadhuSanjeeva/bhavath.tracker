package com.bhavath.tracker.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name ="device_details_view")
public class DeviceDetailsView implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name = "imei_number")
	private String imeiNumber;

	@Column(name = "sim_number")
	private String simNumber;

	@Column(name = "imsi_number")
	private String imsiNumber;

	@Column(name = "id_address")
	private String ipAddress;

	@Column(name = "signal_strength")
	private String signalStrength;
	
	@Column(name = "version")
	private String version;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "mobile_number")
	private String mobileNumber;
	
	@Column(name = "updated_date")
	private Timestamp updatedDate;
	
    @Column(name = "network_date")
	private String networkDate;
	
	@Column(name = "network_time")
	private String networkTime;

	@Column(name = "telecom_provider")
	private String telecomProvider;

	@Column(name = "status")
	private String status;
}
