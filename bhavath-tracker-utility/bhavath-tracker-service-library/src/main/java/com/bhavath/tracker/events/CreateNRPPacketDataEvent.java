package com.bhavath.tracker.events;

import com.bhavath.tracker.vos.NRPPacketDataVO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class CreateNRPPacketDataEvent 
{
	private NRPPacketDataVO nrpPacketDataVO;
}
