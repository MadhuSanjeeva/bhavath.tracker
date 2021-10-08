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
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@SequenceGenerator(name = "device_communication_history_seq", sequenceName = "device_communication_history_seq", allocationSize = 1)
@Table(name = "device_communication_history")
public class DeviceCommunicationHistory implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device_communication_history_seq" )
	private Long id;
	
	@Column(name = "created_date" ,updatable = false)
	private Timestamp createdDate;

	@Column(name = "network_time")
	private String networkTime;

	@Column(name = "network_date")
	private String networkDate;
	
	private String latitude;
	
	private String langitude;
	
	private String location;
	
	@Column(name = "prev_network_time")
	private String prevNetworkTime;

	@Column(name = "prev_network_date")
	private String prevNetworkDate;
	
	@Column(name = "prev_latitude")
	private String prevLatitude;
	
	@Column(name = "prev_langitude")
	private String prevLangitude;
	
	@Column(name = "imei_number")
	private String imeiNumber;
	
	private String status;
	
	@Column(name = "ignition_status")
	private String ignitionStatus;
	
    @Column(name = "vehicle_reg_no")
    private String vehicleRegNo;

	@Column(name = "speed")
	private String speed;

	@Column(name = "emergency_status")
	private String emergencyStatus;

	@Column(name = "main_power_status")
	private String mainPowerStatus;

	@Column(name = "internal_battery_voltage")
	private String internalBatteryVoltage;

	@Column(name = "gsm_signal_strength")
	private String gsmSignalStrength;

	@Column(name = "tamper_alert")
	private String tamperAlert;
	
	@Column(name = "battery_percentage")
	private Double batteryPercentage;
	
	@Column(name = "low_battery_threshold")
	private Double lowBatteryThreshold;
	
	@Column(name = "memory_percentage")
	private Double memoryPercentage;

	@Column(name = "vehicle_mode")
	private String vehicleMode;
	
	@Column(name = "vehicle_mode_id")
	private String vehicleModeId;
	
	@Column(name = "engine_status")
	private String engineStatus;
}
