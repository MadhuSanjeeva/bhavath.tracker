package com.bhavath.tracker.events;

import com.bhavath.tracker.vos.VehicleDetailsVO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class CreateVehicleDetailsEvent
{
	private VehicleDetailsVO vehicleDetailsVO;
}
