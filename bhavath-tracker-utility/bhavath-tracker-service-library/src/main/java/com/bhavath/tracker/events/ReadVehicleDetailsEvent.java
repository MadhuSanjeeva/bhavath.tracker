package com.bhavath.tracker.events;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class ReadVehicleDetailsEvent extends ReadPageEvent<ReadVehicleDetailsEvent> {
    private String searchValue;
    private String searchDate;
    private String columnName;
    private String columnOrder;
}

