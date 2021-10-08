package com.bhavath.tracker.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.bhavath.tracker.command.controller.PrivilegesCommandController;
import com.bhavath.tracker.resource.PrivilegesResource;
import com.bhavath.tracker.vos.PrivilagesVO;

@Component
public class PrivilegesResourceAssembler extends ResourceAssemblerSupport<PrivilagesVO, PrivilegesResource> {

	 public PrivilegesResourceAssembler() 
	    {
			super(PrivilegesCommandController.class, PrivilegesResource.class);
		}

	@Override
	public PrivilegesResource toResource(PrivilagesVO entity)
	{
		return PrivilegesResource.builder()
				.privilegeId(entity.getId())
				.name(entity.getName())
				.build();
	}
	
	public PrivilagesVO fromResource(PrivilegesResource resource)
	{
		return PrivilagesVO.builder()
				.id(resource.getPrivilegeId())
				.name(resource.getName())
				.build();
	}
}
