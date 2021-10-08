package com.bhavath.tracker.assembler;

import javax.validation.Valid;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.command.controller.TaskCommandController;
import com.bhavath.tracker.resource.TaskResource;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.TaskVO;

@Component
public class TaskResourceAssembler extends ResourceAssemblerSupport<TaskVO, TaskResource> {
    public TaskResourceAssembler() {
        super(TaskCommandController.class, TaskResource.class);
    }

    @Override
    public TaskResource toResource(TaskVO entity) {
        return TaskResource.builder()
                .taskId(entity.getId())
                .createdDate(!StringUtils.isEmpty(entity.getCreatedDate()) ? DateUitls.getStringFromTimestamp(entity.getCreatedDate()) : null)
                .header(entity.getHeader())
                .randomCode(entity.getRandomCode())
                .replyGatewayNumber(entity.getReplyGatewayNumber())
                .payload(entity.getPayload())
                .imeiNumber(entity.getImeiNumber())
                .description(entity.getDescription())
                .mobileNumber(entity.getMobileNumber())
                .smsStatus(entity.getSmsStatus())
                .requestCommandName(entity.getRequestCommandName())
                .responseCommandName(entity.getResponseCommandName())
                .deviceResponseStatus(entity.getDeviceResponseStatus())
                .deviceResponse(entity.getDeviceResponse())
                .deviceResponseTime(!StringUtils.isEmpty(entity.getDeviceResponseTime()) ? DateUitls.getStringFromTimestamp(entity.getDeviceResponseTime()) : null)
                .build();
    }

    public TaskVO fromResource(@Valid TaskResource resource) {
        return TaskVO.builder()
        		.id(resource.getTaskId())
        		.header(resource.getHeader())
                .randomCode(resource.getRandomCode())
                .replyGatewayNumber(resource.getReplyGatewayNumber())
                .payload(resource.getPayload())
                .imeiNumber(resource.getImeiNumber())
                .description(resource.getDescription())
                .mobileNumber(resource.getMobileNumber())
                .smsStatus(resource.getSmsStatus())
                .requestCommandName(resource.getRequestCommandName())
                .responseCommandName(resource.getResponseCommandName())
                .deviceResponseStatus(resource.getDeviceResponseStatus())
                .deviceResponse(resource.getDeviceResponse())
        		.deviceResponseTime(!StringUtils.isEmpty(resource.getDeviceResponseTime()) ? DateUtils.getTimeStampDateFromString(resource.getDeviceResponseTime()) : null)
                .build();
    }
}