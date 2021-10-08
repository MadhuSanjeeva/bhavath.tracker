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
@Table(name = "route_deviation_view")
public class RouteDeviationView implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	@Column(name = "deviation_time")
	private Timestamp deviationTime;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "lat_long")
	private String latLang;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "imei_number")
	private String imeiNumber;
	
	@Column(name = "trip_id")
	private Long tripId;
	
}