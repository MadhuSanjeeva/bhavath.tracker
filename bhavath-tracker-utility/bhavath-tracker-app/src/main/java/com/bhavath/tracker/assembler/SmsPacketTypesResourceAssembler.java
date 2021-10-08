package com.bhavath.tracker.assembler;

import javax.validation.Valid;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.bhavath.tracker.query.controller.SmsPacketTypesQueryController;
import com.bhavath.tracker.resource.SmsPacketTypesResource;
import com.bhavath.tracker.vos.SmsPacketTypesVO;

@Component
public class SmsPacketTypesResourceAssembler extends ResourceAssemblerSupport<SmsPacketTypesVO, SmsPacketTypesResource> {
    public SmsPacketTypesResourceAssembler() {
        super(SmsPacketTypesQueryController.class, SmsPacketTypesResource.class);
    }

    @Override
    public SmsPacketTypesResource toResource(SmsPacketTypesVO entity) {
        return SmsPacketTypesResource.builder()
                .key(entity.getKey())
                .payload(entity.getPayload())
                .description(entity.getDescription())
                .commandType(entity.getCommandType())
                .imeiNumber(entity.getImeiNumber())
                .build();
    }

    public SmsPacketTypesVO fromResource(@Valid SmsPacketTypesResource resource) {
        return SmsPacketTypesVO.builder()
        		.key(resource.getKey())
                .payload(resource.getPayload())
                .description(resource.getDescription())
                .commandType(resource.getCommandType())
                .imeiNumber(resource.getImeiNumber())
                .build();
    }
}