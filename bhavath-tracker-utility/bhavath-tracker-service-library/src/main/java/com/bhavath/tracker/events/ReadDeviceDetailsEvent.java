package com.bhavath.tracker.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadDeviceDetailsEvent extends ReadPageEvent<ReadDeviceDetailsEvent>
{
	private Long id;
	private String serialNumber;
	private String imeiNumber;
	private String searchDate;
	private String sortDirection;
	private String sortColumnName;
	private String searchValue;
}

