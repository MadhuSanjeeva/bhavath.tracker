package com.bhavath.tracker.packet.handler;

import com.bhavath.tracker.packet.CVPBasePacket;

//The chain of responsibility Design Pattern has a
//group of objects that are able to solve a problem
//between them based on some internal communication.
//If one of the object is unable to solve it, it passes
//the problem to the next object in the chain

//Lets create a PacketHandler interface which has two methods 
public interface CVPPacketHandler 
{
	// It passes the data to next PacketHandler in chain 
	// if one fails to handle the problem
	public void nextHandler(CVPPacketHandler nextHandler);
	
	// It processes the request and if it fails it just passes
	// it to next PacketHandler in the chain.
	public CVPBasePacket processRequest(CVPData request);
}
