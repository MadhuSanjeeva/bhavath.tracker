package com.bhavath.tracker.query.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bhavath.tracker.components.ComboComponent;
import com.bhavath.tracker.vos.ComboDataVO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/comboData")
public class ComponentQueryController
{
	@Autowired
	private ApplicationContext appContext;
	
	@ApiOperation(value = "View Data for Combo", response = ResponseEntity.class)
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ComboDataVO> getData4Combo(HttpServletRequest request)
    {
		log.info("ComponentQueryController :: getData4Combo :: start() ");
        ComboComponent comboComponent = (ComboComponent) appContext.getBean(request.getParameter("actionType"));
        log.info("ComponentQueryController :: getData4Combo :: end() ");
        return comboComponent.getData4Combo(request.getParameter("extraParams"));
    }
}