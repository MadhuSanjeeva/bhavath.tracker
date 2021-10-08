package com.bhavath.tracker.assembler;

import javax.validation.Valid;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.query.controller.SystemPropertiesQueryController;
import com.bhavath.tracker.resource.SystemPropertiesResource;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.SystemPropertiesVO;

@Component
public class SystemPropertiesAssembler extends ResourceAssemblerSupport<SystemPropertiesVO,SystemPropertiesResource>{

	public SystemPropertiesAssembler() {
		super(SystemPropertiesQueryController.class, SystemPropertiesResource.class);
	}

	@Override
	public SystemPropertiesResource toResource(SystemPropertiesVO entity) {
		
		return SystemPropertiesResource.builder()
				.systemPropertiesId(entity.getId())
				.propertyName(entity.getPropertyName())
				.propertyValue(entity.getPropertyValue())
				.createdDate(!StringUtils.isEmpty(entity.getCreatedDate()) ? DateUitls.getStringFromTimestamp(entity.getCreatedDate()) : null)
				.build();
	}
	
	public SystemPropertiesVO fromResource(@Valid SystemPropertiesResource resource) {
			
			return SystemPropertiesVO.builder()
					.id(resource.getSystemPropertiesId())
					.propertyName(resource.getPropertyName())
					.propertyValue(resource.getPropertyValue())
					.createdDate(!StringUtils.isEmpty(resource.getCreatedDate()) ? DateUtils.getTimeStampDateFromString(resource.getCreatedDate()) : null)
					.build();
	}
	
	 

}
