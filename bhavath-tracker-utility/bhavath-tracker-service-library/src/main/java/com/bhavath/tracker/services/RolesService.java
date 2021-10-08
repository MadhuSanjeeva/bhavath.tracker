package com.bhavath.tracker.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.components.ComboComponent;
import com.bhavath.tracker.data.model.Privilege;
import com.bhavath.tracker.data.model.Roles;
import com.bhavath.tracker.data.repos.PrivilegeRepository;
import com.bhavath.tracker.data.repos.RolesRepositary;
import com.bhavath.tracker.vos.ComboDataVO;
import com.bhavath.tracker.vos.RolesVO;

@Service
public class RolesService implements ComboComponent
{
	
	@Autowired private RolesRepositary repository;
	@Autowired private PrivilegeRepository privilegeRepository;

	@Override
	public List<ComboDataVO> getData4Combo(String... extraParams) 
	{
		List<Roles> dbContent = repository.findAll();
		return dbContent.stream().map(p -> new ComboDataVO(p.getId(), p.getName(), null)).collect(Collectors.toList());
	}

	public boolean save(RolesVO rolesVO) 
	{
		List<Privilege> privileges = new ArrayList<>();
		Roles roles = null;
		if (!StringUtils.isEmpty(rolesVO.getId()))
		{
			roles = repository.getOne(rolesVO.getId());
		}
		else
		{
			roles = new Roles();
		}
		roles.setName(rolesVO.getName());
		if (rolesVO.getPrivilege() != null && rolesVO.getPrivilege().length > 0)
		{
			for (Long privilegeId : rolesVO.getPrivilege())
			{
				Privilege  privilege  = privilegeRepository.getOne(privilegeId);
				privileges.add(privilege);
			}
		}
		roles.setPrivileges(privileges);
		repository.save(roles);
		return true;
	}

	
}
