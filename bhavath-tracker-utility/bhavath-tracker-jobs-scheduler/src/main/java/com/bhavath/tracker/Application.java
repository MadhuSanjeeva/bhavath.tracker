package com.bhavath.tracker;

import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.bhavath.tracker.Application;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootApplication
@EnableScheduling
public class Application 
{
    public static void main(String[] args) throws Exception 
    {
    	log.debug("Starting abhaya-vehicle-tracking-jobs-scheduler");
    	TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    	ApplicationContext ctx = SpringApplication.run(Application.class, args);
		log.debug("abhaya-vehicle-tracking-jobs-scheduler application name: {}", ctx.getApplicationName());
		log.debug("Active profiles: " + Arrays.toString(ctx.getEnvironment().getActiveProfiles()));
  }
    
    
}
