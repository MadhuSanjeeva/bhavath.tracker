package com.bhavath.tracker.vos;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
@JsonInclude(Include.NON_DEFAULT)
public class TripDetailsVO implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Timestamp createdDate;
    private String imeiNumber;
	private String sourceLocation;
	private String destiLocation;
	private String sourceLatLang;
	private String destiLatLang;
	private String radius;
	private String identifier;
	private Timestamp closeTime;
	private Boolean tripClosed;
}
