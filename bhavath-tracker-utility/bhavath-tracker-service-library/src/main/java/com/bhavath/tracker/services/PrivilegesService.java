package com.bhavath.tracker.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.components.ComboComponent;
import com.bhavath.tracker.data.model.Privilege;
import com.bhavath.tracker.data.repos.PrivilegeRepository;
import com.bhavath.tracker.vos.ComboDataVO;
import com.bhavath.tracker.vos.PrivilagesVO;

@Service
public class PrivilegesService implements ComboComponent
{
	
	@Autowired private PrivilegeRepository repository;

	@Override
	public List<ComboDataVO> getData4Combo(String... extraParams) 
	{
		List<Privilege> dbContent = repository.findAll();
		return dbContent.stream().map(p -> new ComboDataVO(p.getId(), p.getName(), null)).collect(Collectors.toList());
	}
	
	public boolean save(PrivilagesVO privilagesVO) 
	{
		Privilege privileges = null;
		if (!StringUtils.isEmpty(privilagesVO.getId()))
		{
			privileges = repository.getOne(privilagesVO.getId());
		}
		else
		{
			privileges = new Privilege();
		}
		privileges.setName(privilagesVO.getName());
		repository.save(privileges);
		return true;
	}
}
