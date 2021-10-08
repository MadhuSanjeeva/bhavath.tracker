package com.bhavath.tracker.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadRouteDeviationSetEvent extends ReadPageEvent<ReadRouteDeviationSetEvent>
{
	private Long id;
	private String createdDate;
	private String deviationTime;
	private String latLong;
	private String location;
	private String imeiNumber;
	private String searchValue;
	private String searchDate;
}
