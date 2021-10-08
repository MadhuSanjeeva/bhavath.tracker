package com.bhavath.tracker.resource;

import java.sql.Timestamp;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TripTrackingResource extends ResourceSupport 
{
	private Long tripTrackingId;
	private String createdDate;
    private String imeiNumber;
    private String latitude;
    private String longitude;
    private String location;
    private String latitudeDirection;
    private String longitudeDirection;
	private String networkDate;
	private String networkTime;
	private Long tripId;
}