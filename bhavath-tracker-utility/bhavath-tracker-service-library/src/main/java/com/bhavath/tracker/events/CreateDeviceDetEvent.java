package com.bhavath.tracker.events;

import com.bhavath.tracker.vos.DeviceDetailsVO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class CreateDeviceDetEvent 
{
	private DeviceDetailsVO deviceDetailsVO;
}
