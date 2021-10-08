package com.bhavath.tracker.packet;

import com.bhavath.tracker.vos.LMPPacketDataVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Builder
@Setter
@Getter
@Accessors(chain = true)
@ToString
public class LMPPacket  extends CVPBasePacket 
{
	private static final long serialVersionUID = -5690895900343433600L;
	private LMPPacketDataVO lmpPacketDataVO;
}
