package com.bhavath.tracker.events;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class ReadTaskCommandEvent extends ReadPageEvent<ReadTaskCommandEvent>
{
	private Long id;
	private String header;
	private String randomCode;
	private String replyGatewayNumber;
    private String payload;
    private String imeiNumber;
    private String description;
    private Timestamp createdDate;
    private String searchDate;
    private String searchValue;
}