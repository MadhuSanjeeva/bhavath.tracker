package com.bhavath.tracker.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.bhavath.tracker.command.controller.RolesCommandController;
import com.bhavath.tracker.resource.RolesResource;
import com.bhavath.tracker.vos.RolesVO;

@Component
public class RolesResourceAssembler extends ResourceAssemblerSupport<RolesVO, RolesResource> {

	 public RolesResourceAssembler() 
	    {
			super(RolesCommandController.class, RolesResource.class);
		}

	@Override
	public RolesResource toResource(RolesVO entity)
	{
		return RolesResource.builder()
				.roleId(entity.getId())
				.name(entity.getName())
				.privileges(entity.getPrivileges())
				.build();
	}
	
	public RolesVO fromResource(RolesResource resource)
	{
		return RolesVO.builder()
				.id(resource.getRoleId())
				.name(resource.getName())
				.privilege(resource.getPrivilege())
				.build();
	}
}