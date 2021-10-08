package com.bhavath.tracker.resource;

import java.sql.Timestamp;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@NoArgsConstructor
@Builder
@AllArgsConstructor
@Accessors(chain = true)
@Setter
@Getter
@JsonInclude(Include.ALWAYS)
public class SIMDetailsResource extends ResourceSupport 
{
	private Long simDetailsId;
	private String createdDate;
	private String telecomProvider;
	private String mobileNumber;
	private String status;
	private String imeiNumber;
	private String imsiNumber;
}
