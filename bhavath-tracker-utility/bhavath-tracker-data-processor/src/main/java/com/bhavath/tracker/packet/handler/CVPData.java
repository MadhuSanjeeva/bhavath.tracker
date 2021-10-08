package com.bhavath.tracker.packet.handler;

import com.bhavath.tracker.enums.CVPPacketType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class CVPData
{
	private String rawdata;
	private String networkTime;
	private String networkDate;
	private CVPPacketType cvpPacketType;
}
