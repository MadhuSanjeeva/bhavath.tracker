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

import com.bhavath.tracker.assembler.RolesResourceAssembler;
import com.bhavath.tracker.resource.RolesResource;
import com.bhavath.tracker.services.RolesService;
import com.bhavath.tracker.vos.ResponseVO;
import com.bhavath.tracker.vos.RolesVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "v1/role")
public class RolesCommandController 
{

	@Autowired private RolesService service;
	@Autowired private RolesResourceAssembler assembler;

	@ApiOperation(value = "Create Role", response = ResponseVO.class)
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> save(@RequestBody RolesResource resource, HttpServletRequest request) 
	{
		log.info("RolesCommandController :: save :: start() ");
		RolesVO rolesVO = assembler.fromResource(resource);
		log.info("RolesCommandController :: save :: end() ");
		if(service.save(rolesVO))
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		else
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
