package com.bhavath.tracker.data.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(name = "nrb_packet_data_seq", sequenceName = "nrb_packet_data_seq", allocationSize = 1)
@Table(name = "nrb_packet_data")
public class NRPPacketData implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nrb_packet_data_seq")
    private Long id;
    
    @Column(name = "created_date")
	private Timestamp createdDate;

    @Column(name = "packet_type")
    private String packetType;

    @Column(name = "imei_number")
    private String imeiNumber;

    @Column(name = "packet_staus")
    private String packetStaus;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "gps_validity")
    private String gpsValidity;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "latitude_direction")
    private String latitudeDirection;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "longitude_direction")
    private String longitudeDirection;

    @Column(name = "altitude")
    private String altitude;

    @Column(name = "speed")
    private String speed;

    @Column(name = "distance")
    private String distance;

    @Column(name = "provider")
    private String provider;

    @Column(name = "vehicleRegNo")
    private String vehicleRegNo;

    @Column(name = "firmwareVersion")
    private String firmwareVersion;

    @Column(name = "end_character")
    private String endCharacter;

    @Column(name = "checksum")
    private String checksum;
    
    @Column(name = "network_date")
	private String networkDate;
	
	@Column(name = "network_time")
	private String networkTime;
}