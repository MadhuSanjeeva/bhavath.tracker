package com.bhavath.tracker.data.model;

import java.io.Serializable;
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
@SequenceGenerator(name = "lmp_packet_data_seq", sequenceName = "lmp_packet_data_seq", allocationSize = 1)
@Table(name = "lmp_packet_data")
public class LMPPacketData implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lmp_packet_data_seq")
    private Long id;
    
    @Column(name = "created_date")
	private Timestamp createdDate;
    
    @Column(name = "vendor_id")
    private String vendorId;

    @Column(name = "firmware_version")
    private String firmwareVersion;

    @Column(name = "packet_type")
    private String packetType;

    @Column(name = "packet_status")
    private String packetStatus;

    @Column(name = "imei")
    private String imei;

    @Column(name = "vehicle_reg_no")
    private String vehicleRegNo;

    @Column(name = "gps_fix")
    private String gpsFix;

    @Column(name = "date")
    private String date;

    @Column(name = "time")
    private String time;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "latitude_direction")
    private String latitudeDirection;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "longitude_direction")
    private String longitudeDirection;

    @Column(name = "speed")
    private String speed;

    @Column(name = "heading")
    private String heading;

    @Column(name = "no_of_satellites")
    private String noOfSatellites;

    @Column(name = "altitude")
    private String altitude;

    @Column(name = "pdop")
    private String pdop;

    @Column(name = "hdop")
    private String hdop;

    @Column(name = "network_operator_name")
    private String networkOperatorName;

    @Column(name = "ignition")
    private String ignition;

    @Column(name = "main_power_status")
    private String mainPowerStatus;

    @Column(name = "main_input_voltage")
    private String mainInputVoltage;

    @Column(name = "internal_battery_voltage")
    private String internalBatteryVoltage;

    @Column(name = "emergency_status")
    private String emergencyStatus;

    @Column(name = "tamper_alert")
    private String tamperAlert;

    @Column(name = "gsm_signal_strength")
    private String gsmSignalStrength;

    @Column(name = "mcc")
    private String mcc;

    @Column(name = "lac")
    private String lac;

    @Column(name = "cell_id")
    private String cellId;

    @Column(name = "nmr")
    private String nmr;

    @Column(name = "digital_input_status")
    private String digitalInputStatus;

    @Column(name = "digital_output_status")
    private String digitalOutputStatus;

    @Column(name = "frame_number")
    private String frameNumber;

    @Column(name = "network_date")
	private String networkDate;
	
	@Column(name = "network_time")
	private String networkTime;
	
	@Column(name = "vehicle_mode")
	private String vehicleMode;
	
	@Column(name = "vehicle_mode_id")
	private String vehicleModeId;
	
	@Column(name = "engine_status")
	private String engineStatus;
	
	@Column(name = "over_network_ip")
	private String overNetworkIp;
	
	@Column(name = "over_sms_number")
	private String overSmsNumber;
	
	@Column(name = "analog_zero")
	private String analogZero;
	
	@Column(name = "analog_one")
	private String analogOne;
	
	@Column(name = "over_the_air_parameter")
	private String overTheAirParameter;
	
}