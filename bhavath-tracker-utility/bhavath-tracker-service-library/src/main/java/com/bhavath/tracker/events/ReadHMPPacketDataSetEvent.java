package com.bhavath.tracker.events;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class ReadHMPPacketDataSetEvent extends ReadPageEvent<ReadHMPPacketDataSetEvent>
{
	private Long id;
    private Timestamp vendorId;
    private String firmwareVersion;
    private String imeiNumber;
    private String batteryPercentage;
    private String lowBatteryThrValue;
    private String memoryPercentage;
    private String dataUpdateON;
    private String dataUpdateOFF;
    private String digitalIOStatus;
    private String analogIOStatus;
    private String endOfPacket;
    private String startDate;
    private String endDate;
}