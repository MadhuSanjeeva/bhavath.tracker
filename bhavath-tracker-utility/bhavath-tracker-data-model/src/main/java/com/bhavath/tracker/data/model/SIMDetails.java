package com.bhavath.tracker.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "sim_details")
@SequenceGenerator(name = "sim_details_seq", sequenceName = "sim_details_seq",  allocationSize = 1)
public class SIMDetails implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sim_details_seq")
	private Long id;
	
	@Column(name = "created_date" ,updatable = false)
	private Timestamp createdDate;

	@Column(name = "telecom_provider")
	private String telecomProvider;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "status")
	private String status;

	@Column(name = "imsi_number" , unique=true)
	private String imsiNumber;
	
	@ManyToOne
	@JoinColumn(name = "device_details_id",nullable = false)
	@JsonBackReference
	private DeviceDetails deviceDetails;
}
