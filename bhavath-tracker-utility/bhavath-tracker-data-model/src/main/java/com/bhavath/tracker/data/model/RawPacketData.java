package com.bhavath.tracker.data.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@SequenceGenerator(name = "raw_packet_data_seq", sequenceName = "raw_packet_data_seq", allocationSize = 1)
@Table(name = "raw_packet_data")
public class RawPacketData implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "raw_packet_data_seq")
	private Long id;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "raw_data")
	private String rawData;
	
	@Column(name = "network_date")
	private Date networkDate;
	
	@Column(name = "network_time")
	private String networkTime;
	
	@Column(name = "imei_number")
	private String imeiNumber;
	
    @Column(name = "vehicle_reg_no")
    private String vehicleRegNo;
    
    @Column(name = "cvp_type")
    private String cvpType;

	@Column(name = "in_bound_channel")
	private String inBoundChannel;
	
	@Column(name = "packet_status")
	private String packetStatus;
	
	@Column(name = "packet_type")
	private String packetType;
	
	@Column(name = "vehicle_mode_id")
	private String vehicleModeId;
	
}