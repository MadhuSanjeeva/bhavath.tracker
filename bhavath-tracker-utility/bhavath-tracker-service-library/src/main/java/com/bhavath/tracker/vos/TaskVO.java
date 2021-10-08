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
public class TaskVO implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Timestamp createdDate;
	private String header;
	private String randomCode;
	private String replyGatewayNumber;
    private String payload;
    private String imeiNumber;
    private String mobileNumber;
    private String smsStatus;
    private String description;
    private String requestCommandName;
    private String responseCommandName;
    private Boolean deviceResponseStatus;
    private String deviceResponse;
    private Timestamp deviceResponseTime;
}
