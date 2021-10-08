package com.bhavath.tracker.events;

import com.bhavath.tracker.vos.TripDetailsVO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class CreateTripDetailsEvent 
{
	private TripDetailsVO tripDetailsVO;
}
