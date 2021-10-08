package com.bhavath.tracker.vos;

import java.io.Serializable;
import java.sql.Timestamp;

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
public class HMPPacketDataVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
    private Timestamp createdDate;
    private String vendorId;
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
    private String networkDate;
    private String networkTime;
}
