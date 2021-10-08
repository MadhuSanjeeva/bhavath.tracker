package com.bhavath.tracker.assembler;

import com.bhavath.tracker.command.controller.UserRegistrationController;
import com.bhavath.tracker.resource.UserDetailsResource;
import com.bhavath.tracker.vos.UsersDetailsVO;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
public class UserDetailsResourceAssembler extends ResourceAssemblerSupport<UsersDetailsVO, UserDetailsResource> {
    public UserDetailsResourceAssembler() {
        super(UserRegistrationController.class, UserDetailsResource.class);
    }

    @Override
    public UserDetailsResource toResource(UsersDetailsVO entity) {
        return UserDetailsResource.builder()
                .username(entity.getUsername())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .userId(entity.getId())
                .mobileNumber(entity.getMobileNumber())
                .isEnabled(entity.getIsEnabled())
                .emailId(entity.getEmailId())
                .createdDate(entity.getCreatedDate())
                .rolesVO(entity.getRolesVO())
                .userId(entity.getId())
                .build();
    }

    public UsersDetailsVO fromResource(@Valid UserDetailsResource resource) {
        return UsersDetailsVO.builder()
                .firstName(resource.getFirstName())
                .lastName(resource.getLastName())
                .mobileNumber(resource.getMobileNumber())
                .password(resource.getPassword())
                .roles(resource.getRoles())
                .username(resource.getUsername())
                .emailId(resource.getEmailId())
                .id(resource.getUserId())
                .build();
    }
}