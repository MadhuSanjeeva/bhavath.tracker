package com.bhavath.tracker.assembler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.query.controller.DeviceDetailsQueryController;
import com.bhavath.tracker.resource.SIMDetailsResource;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.SIMDetailsVO;

@Component
public class SimDetailsResourceAssembler extends ResourceAssemblerSupport<SIMDetailsVO, SIMDetailsResource> 
{

	public SimDetailsResourceAssembler()
	{
		super(DeviceDetailsQueryController.class, SIMDetailsResource.class);
	}

	@Override
	public SIMDetailsResource toResource(SIMDetailsVO entity) 
	{
		SIMDetailsResource resource = SIMDetailsResource.builder()
			.simDetailsId(entity.getId())
			.createdDate(!StringUtils.isEmpty(entity.getCreatedDate()) ? DateUitls.getStringFromTimestamp(entity.getCreatedDate()) : null)
			.telecomProvider(entity.getTelecomProvider())
			.mobileNumber(entity.getMobileNumber())
			.status(entity.getStatus())
			.imeiNumber(entity.getImeiNumber())
			.imsiNumber(entity.getImsiNumber())
			.build();
		return resource;
	}
	
	public SIMDetailsVO fromResource(SIMDetailsResource resource) throws IOException 
	{
		return SIMDetailsVO.builder()
			.id(resource.getSimDetailsId())
			.createdDate(!StringUtils.isEmpty(resource.getCreatedDate()) ? DateUtils.getTimeStampDateFromString(resource.getCreatedDate()) : null)
			.telecomProvider(resource.getTelecomProvider())
			.mobileNumber(resource.getMobileNumber())
			.status(resource.getStatus())
			.imeiNumber(resource.getImeiNumber())
			.imsiNumber(resource.getImsiNumber())
			.build();
	}
	
	public List<SIMDetailsVO> fromResource(List<SIMDetailsResource> resources) 
	{
		List<SIMDetailsVO> simDetailsVOs = new ArrayList<>();
		for (SIMDetailsResource resource : resources)
		{
			SIMDetailsVO simDetailsVO = SIMDetailsVO.builder()
					.id(resource.getSimDetailsId())
					.createdDate(!StringUtils.isEmpty(resource.getCreatedDate()) ? DateUtils.getTimeStampDateFromString(resource.getCreatedDate()) : null)
					.telecomProvider(resource.getTelecomProvider())
					.mobileNumber(resource.getMobileNumber())
					.status(resource.getStatus())
					.imeiNumber(resource.getImeiNumber())
					.imsiNumber(resource.getImsiNumber())
					.build();
			simDetailsVOs.add(simDetailsVO);
		}
		return simDetailsVOs;
	}
}
