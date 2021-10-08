package com.bhavath.tracker.events;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadTripDetailsEvent extends ReadPageEvent<ReadTripDetailsEvent>
{
	private Long id;
	private Timestamp createdDate;
    private String imeiNumber;
	private String sourceLocation;
	private String destiLocation;
	private String sourceLatLang;
	private String destiLatLang;
	private Timestamp closeTime;
	private Boolean isTripClosed;
	private String searchDate;
	private String tripId;
	private String identifier;
	private String searchValue;
}

