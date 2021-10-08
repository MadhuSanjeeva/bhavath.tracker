package com.bhavath.tracker.vos;

import java.io.Serializable;
import java.util.List;

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
public class DeviceDetailsVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String imeiNumber;
	private String imsiNumber;
	private String simNumber;
	private String ipAddress;
	private String signalStrength;
	private String version;
	private String createdDate;
	private String updatedDate;
	private String networkDate;
	private String networkTime;
	private String serialNumber;
    private String vehicleRegNo;
	//for sim details save
	private String telecomProvider;
	private String mobileNumber;
	private List<SIMDetailsVO> simDetails;
}
