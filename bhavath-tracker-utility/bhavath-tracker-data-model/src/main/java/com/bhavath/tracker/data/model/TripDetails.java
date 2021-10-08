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
@Table(name = "trip_details")
@SequenceGenerator(name = "trip_details_seq", sequenceName = "trip_details_seq", allocationSize = 1)
public class TripDetails implements Serializable
{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trip_details_seq")
	private Long id;
	
	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;
	
	@Column(name = "imei_number",nullable = false)
    private String imeiNumber;
	
	@Column(name = "source_location")
	private String sourceLocation;

	@Column(name = "desti_location")
	private String destiLocation;

	@Column(name = "source_lat_lang")
	private String sourceLatLang;

	@Column(name = "desti_lat_lang")
	private String destiLatLang;
	
	@Column(name = "radius")
	private String radius;
	
	@Column(name = "identifier")
	private String identifier;

	@Column(name = "close_time")
	private Timestamp closeTime;

	@Column(name = "is_trip_closed" ,nullable = false)
	private boolean isTripClosed;
	
	@OneToMany(mappedBy = "tripDetails", fetch = FetchType.LAZY)
	private Set<TripTracking> tripTracking = new HashSet<TripTracking>();

	@OneToMany(mappedBy = "tripDetails", fetch = FetchType.LAZY)
	private Set<RouteDeviation> deviations = new HashSet<RouteDeviation>();
}
