package com.bhavath.tracker.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadSIMDetailsEvent extends ReadPageEvent<ReadSIMDetailsEvent>
{
	private Long id;
	private String createdDate;
	private String telecomProvider;
	private String mobileNumber;
	private String status;
	private String imeiNumber;
	private String searchDate;
	private String imsiNumber;
	private String searchValue;
}

