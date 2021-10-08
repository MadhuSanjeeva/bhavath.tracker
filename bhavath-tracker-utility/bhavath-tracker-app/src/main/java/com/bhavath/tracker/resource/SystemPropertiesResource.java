package com.bhavath.tracker.resource;

import java.sql.Timestamp;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemPropertiesResource extends ResourceSupport{
	
	private Long systemPropertiesId;
	
	private String propertyName;
	
	private String propertyValue;

	private String createdDate;

	private String updatedDate;
}
