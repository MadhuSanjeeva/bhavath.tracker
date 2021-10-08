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
@SequenceGenerator(name = "hmp_packet_data_seq", sequenceName = "hmp_packet_data_seq", allocationSize = 1)
@Table(name = "hmp_packet_data")
public class HMPPacketData implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hmp_packet_data_seq")
    private Long id;

    @Column(name = "created_date")
	private Timestamp createdDate;
    
    @Column(name = "vendor_id")
    private String vendorId;

    @Column(name = "firmware_version")
    private String firmwareVersion;

    @Column(name = "imei_number")
    private String imeiNumber;

    @Column(name = "battery_percentage")
    private String batteryPercentage;

    @Column(name = "low_battery_thr_value")
    private String lowBatteryThrValue;

    @Column(name = "memory_percentage")
    private String memoryPercentage;

    @Column(name = "data_update_on")
    private String dataUpdateON;

    @Column(name = "data_update_off")
    private String dataUpdateOFF;

    @Column(name = "digital_io_status")
    private String digitalIOStatus;

    @Column(name = "analog_io_status")
    private String analogIOStatus;

    @Column(name = "end_of_packet")
    private String endOfPacket;
    
    @Column(name = "network_date")
	private String networkDate;
	
	@Column(name = "network_time")
	private String networkTime;
}