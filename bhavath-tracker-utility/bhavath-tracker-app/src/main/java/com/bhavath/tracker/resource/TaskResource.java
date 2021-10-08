package com.bhavath.tracker.resource;

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
public class TaskResource extends ResourceSupport
{
	private Long taskId;
	private String createdDate;
	private String header;
	private String randomCode;
	private String replyGatewayNumber;
    private String payload;
    private String imeiNumber;
    private String description;
    private String mobileNumber;
    private String smsStatus;
    private String requestCommandName;
    private String responseCommandName;
    private Boolean deviceResponseStatus;
    private String deviceResponse;
    private String deviceResponseTime;
}