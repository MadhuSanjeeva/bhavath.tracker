package com.bhavath.tracker.events;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadTripTrackingEvent extends ReadPageEvent<ReadTripTrackingEvent>
{
	private Long id;
	private Timestamp createdDate;
    private String imeiNumber;
    private String latitude;
    private String longitude;
    private String location;
    private String latitudeDirection;
    private String longitudeDirection;
	private String networkDate;
	private String networkTime;
	private String searchDate;
	private Long tripId;
}

