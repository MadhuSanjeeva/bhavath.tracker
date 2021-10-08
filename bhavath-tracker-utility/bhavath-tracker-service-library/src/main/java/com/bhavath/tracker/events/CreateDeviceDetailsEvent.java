package com.bhavath.tracker.events;

import java.util.List;

import com.bhavath.tracker.vos.DeviceDetailsVO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class CreateDeviceDetailsEvent 
{
	private List<DeviceDetailsVO> deviceDetailsVOs;
}
