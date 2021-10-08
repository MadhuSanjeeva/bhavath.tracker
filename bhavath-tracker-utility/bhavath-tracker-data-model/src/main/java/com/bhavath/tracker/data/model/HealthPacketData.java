package com.bhavath.tracker.data.model;

import java.io.Serializable;

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
@SequenceGenerator(name = "health_packet_data_seq", sequenceName = "health_packet_data_seq", allocationSize = 1)
@Table(name = "health_packet_data")
public class HealthPacketData implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "health_packet_data_seq")
    private Long id;
    
    @Column(name = "header")
	private String header;
    
    @Column(name = "random_code")
	private String randomCode;
    
    @Column(name = "vendor_Id")
   	private String vendorId;
    
    @Column(name = "firmware_version")
   	private String firmwareVersion;
    
    @Column(name = "imei_number")
   	private String imeiNumber;
    
    @Column(name = "alert_id")
   	private String alertId;
    
    @Column(name = "latitude")
   	private String latitude;
    
    @Column(name = "latitude_direction")
   	private String latitudeDirection;
    
    @Column(name = "longnitude")
   	private String longnitude;
    
    @Column(name = "longnitude_direction")
   	private String longnitudeDirection;
    
    @Column(name = "gps_fix")
   	private String gpsFix;
    
    @Column(name = "date")
   	private String date;
    
    @Column(name = "time")
   	private String time;
    
    @Column(name = "heading")
   	private String heading;
    
    @Column(name = "speed")
   	private String speed;
    
    @Column(name = "gsm_strength")
   	private String gsmStrength;
    
    @Column(name = "country_code")
   	private String  countryCode;
    
    @Column(name = "network_code")
   	private String  networkCode;
    
    @Column(name = "lac")
   	private String  lac;
    
    @Column(name = "main_power")
   	private String  mainPower;
    
    @Column(name = "ign_status")
   	private String  ignStatus;
    
    @Column(name = "battery_voltage")
   	private String  batteryVoltage;
    
    @Column(name = "frame_number")
   	private String  frameNumber;
    
    @Column(name = "vehicle_mode")
   	private String  vehicleMode;
    
	@Column(name = "raw_data")
	private String rawData;
    
}