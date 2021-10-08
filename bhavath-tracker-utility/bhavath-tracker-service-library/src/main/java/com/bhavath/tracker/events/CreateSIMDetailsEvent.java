package com.bhavath.tracker.events;

import java.util.List;

import com.bhavath.tracker.vos.SIMDetailsVO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class CreateSIMDetailsEvent 
{
	private List<SIMDetailsVO> simDetailsVO;
}
