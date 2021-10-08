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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trip_tracking")
@SequenceGenerator(name = "trip_tracking_seq", sequenceName = "trip_tracking_seq", allocationSize = 1)
public class TripTracking implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trip_tracking_seq")
	private Long id;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "imei_number")
    private String imeiNumber;
	
    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "latitude_direction")
    private String latitudeDirection;

    @Column(name = "longitude_direction")
    private String longitudeDirection;

    @Column(name = "network_date")
	private String networkDate;
	
	@Column(name = "network_time")
	private String networkTime;

	@ManyToOne
	@JoinColumn(name = "trip_id", nullable = true)
	private TripDetails tripDetails;
	
}
