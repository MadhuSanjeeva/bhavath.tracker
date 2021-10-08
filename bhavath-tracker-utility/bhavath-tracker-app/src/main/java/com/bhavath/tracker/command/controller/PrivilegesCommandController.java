package com.bhavath.tracker.command.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bhavath.tracker.assembler.PrivilegesResourceAssembler;
import com.bhavath.tracker.resource.PrivilegesResource;
import com.bhavath.tracker.services.PrivilegesService;
import com.bhavath.tracker.vos.PrivilagesVO;
import com.bhavath.tracker.vos.ResponseVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "v1/privilege")
public class PrivilegesCommandController 
{

	@Autowired private PrivilegesService service;
	@Autowired private PrivilegesResourceAssembler assembler;

	@ApiOperation(value = "Create Privileges", response = ResponseVO.class)
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> save(@RequestBody PrivilegesResource resource, HttpServletRequest request) 
	{
		log.info("PrivilegesCommandController :: save :: start() ");
		PrivilagesVO rolesVO = assembler.fromResource(resource);
		log.info("PrivilegesCommandController :: save :: end() ");
		if(service.save(rolesVO))
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		else
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
