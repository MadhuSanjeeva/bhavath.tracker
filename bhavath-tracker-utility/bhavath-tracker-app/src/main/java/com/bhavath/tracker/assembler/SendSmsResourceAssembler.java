package com.bhavath.tracker.assembler;

import javax.validation.Valid;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.bhavath.tracker.command.controller.SmsCommandController;
import com.bhavath.tracker.resource.SendSmsResource;
import com.bhavath.tracker.vos.SendSmsVO;

@Component
public class SendSmsResourceAssembler extends ResourceAssemblerSupport<SendSmsVO, SendSmsResource> {
    public SendSmsResourceAssembler() {
        super(SmsCommandController.class, SendSmsResource.class);
    }

    @Override
    public SendSmsResource toResource(SendSmsVO entity) {
        return SendSmsResource.builder()
                .sender(entity.getSender())
                .content(entity.getContent())
                .inNumber(entity.getInNumber())
                .email(entity.getEmail())
                .credits(entity.getCredits())
                .build();
    }

    public SendSmsVO fromResource(@Valid SendSmsResource resource) {
        return SendSmsVO.builder()
                .sender(resource.getSender())
                .content(resource.getContent())
                .inNumber(resource.getInNumber())
                .email(resource.getEmail())
                .credits(resource.getCredits())
                .build();
    }
}