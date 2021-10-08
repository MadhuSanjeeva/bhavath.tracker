package com.bhavath.tracker.packet.handler;

import com.bhavath.tracker.packet.CVPBasePacket;

public class CoRCVPPatternClient 
{
	public CVPBasePacket invokePacketHandler(CVPData request) 
	{
		
		CVPPacketHandler packetHandler = createChainOfRules();
		return packetHandler.processRequest(request);
	}

	private static CVPPacketHandler createChainOfRules()
	{
		return CVPPacketChainFactory.getInstance().createRules(
				new LMPPacketHandler(),
				new HMPPacketHandler(),
				new EPBPacketHandler(),
				new ActivationPacketHandler(),
				new HealthPacketHandler(),
				new NRPPacketHandler()
				);
	}
}
