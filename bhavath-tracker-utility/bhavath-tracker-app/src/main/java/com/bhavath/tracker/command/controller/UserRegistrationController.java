package com.bhavath.tracker.command.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bhavath.tracker.assembler.UserDetailsResourceAssembler;
import com.bhavath.tracker.events.CreateUserEvent;
import com.bhavath.tracker.resource.UserDetailsResource;
import com.bhavath.tracker.services.UserService;
import com.bhavath.tracker.vos.ResponseVO;
import com.bhavath.tracker.vos.UsersDetailsVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/userDetails")
public class UserRegistrationController 
{

	@Autowired private UserService service;
	@Autowired private UserDetailsResourceAssembler assembler;
	@Autowired private PasswordEncoder passwordEncoder;

	@ApiOperation(value = "User Registration", response = ResponseVO.class)
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseVO save(@Valid @RequestBody UserDetailsResource userResource)
	{
		log.info("UserRegistrationController :: save :: start() ");
		UsersDetailsVO usersDTO = assembler.fromResource(userResource);
		CreateUserEvent  createUserEvent = new CreateUserEvent();
		if (usersDTO.getPassword() != null)
			usersDTO.setPassword(passwordEncoder.encode(usersDTO.getPassword()));
		createUserEvent.setUsersDetailsVO(usersDTO);
		log.info("UserRegistrationController :: save :: end() ");
		return service.save(createUserEvent);
	}
}