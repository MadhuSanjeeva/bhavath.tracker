package com.bhavath.tracker.vos;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteDeviationVO implements Serializable
{

	private static final long serialVersionUID = 1L;
	private Long id;
	private Timestamp createdDate;
	private Timestamp deviationTime;
	private String latLang;
	private String location;
	private String imeiNumber;
	private Long tripId;
}
