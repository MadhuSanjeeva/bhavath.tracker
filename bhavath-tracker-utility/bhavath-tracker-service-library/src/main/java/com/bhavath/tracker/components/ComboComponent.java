package com.bhavath.tracker.components;

import java.util.List;

import com.bhavath.tracker.vos.ComboDataVO;

public interface ComboComponent 
{
	public List<ComboDataVO> getData4Combo(String... extraParams);
}
