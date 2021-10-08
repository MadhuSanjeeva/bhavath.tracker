package com.bhavath.tracker.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class ReadSmsPacketTypesEvent extends ReadPageEvent<ReadSmsPacketTypesEvent>
{
	private String key;
    private String payload;
    private String description;
    private String commandType;
}