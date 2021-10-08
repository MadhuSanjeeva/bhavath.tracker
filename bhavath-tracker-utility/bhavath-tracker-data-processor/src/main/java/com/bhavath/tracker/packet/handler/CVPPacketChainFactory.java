package com.bhavath.tracker.packet.handler;

import java.util.Arrays;
import java.util.List;

public class CVPPacketChainFactory 
{
    private CVPPacketChainFactory() {
    }

    private static class Holder 
    {
    	private static CVPPacketChainFactory instance = new CVPPacketChainFactory();
    }

    public static CVPPacketChainFactory getInstance() {
    	return Holder.instance;
    }

    public CVPPacketHandler createRules(CVPPacketHandler... rules) 
    {

		if (rules.length < 2) {
			throw new IllegalArgumentException("a chain must contain at least two rules");
		}

		List<CVPPacketHandler> listOrdered = Arrays.asList(rules);

		CVPPacketHandler prevRule = listOrdered.get(0);
		for (int i = 1; i < listOrdered.size(); i++) 
		{
			CVPPacketHandler currentRule = listOrdered.get(i);
			prevRule.nextHandler(currentRule);
			prevRule = currentRule;
		}
		return listOrdered.get(0);
	}
}
