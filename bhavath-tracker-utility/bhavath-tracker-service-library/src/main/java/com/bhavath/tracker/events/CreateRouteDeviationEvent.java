package com.bhavath.tracker.events;

import com.bhavath.tracker.vos.RouteDeviationVO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class CreateRouteDeviationEvent 
{
	private RouteDeviationVO routeDeviationVO;
}