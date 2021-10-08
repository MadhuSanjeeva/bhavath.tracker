package com.bhavath.tracker.resource;

import java.sql.Timestamp;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HMPPacketDataResource extends ResourceSupport 
{
	private Long hmpPacketDataId;
    private String createdDate;
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
