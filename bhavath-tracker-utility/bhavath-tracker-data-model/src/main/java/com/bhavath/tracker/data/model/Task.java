package com.bhavath.tracker.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(name = "task_seq", sequenceName = "task_seq", allocationSize = 1)
@Table(name = "task")
public class Task implements Serializable 
{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "task_seq")
	private Long id;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "imei_number")
    private String imeiNumber;
	
	@Column(name = "header")
    private String header;
	
	@Column(name = "random_code")
    private Long randomCode;
	
	@Column(name = "reply_gateway_number")
    private String replyGatewayNumber;
	
	@Column(name = "payload")
    private String payload;
	
	@Column(name = "description")
    private String description;
	
	@Column(name = "sms_status")
    private String smsStatus;
	
	@Column(name = "mobile_number")
	private String mobileNumber;
	
	@Column(name = "request_command_name")
	private String requestCommandName;
	
	@Column(name = "response_command_name")
	private String responseCommandName;
	
	@Column(name = "device_response_status" , nullable=false)
	private boolean deviceResponseStatus;
	
	@Column(name = "device_response")
	private String deviceResponse;
	
	@Column(name = "device_response_time")
	private Timestamp deviceResponseTime;
	
}
