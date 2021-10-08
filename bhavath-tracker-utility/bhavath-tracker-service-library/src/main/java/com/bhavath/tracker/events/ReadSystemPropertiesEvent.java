package com.bhavath.tracker.events;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadSystemPropertiesEvent extends ReadPageEvent<ReadSystemPropertiesEvent>
{
	private Long id;
	
	private String searchValue;
	
	private String searchDate;
	
	 

}