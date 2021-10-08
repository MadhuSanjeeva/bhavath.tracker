package com.bhavath.tracker.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadUsersDataSetEvent extends ReadPageEvent<ReadUsersDataSetEvent>
{
	private Long id;
	private String sortDirection;
	private String sortColumnName;
	private String mobileNumber;
	private String userName;
	private String searchValue;
	private String searchDate;
}