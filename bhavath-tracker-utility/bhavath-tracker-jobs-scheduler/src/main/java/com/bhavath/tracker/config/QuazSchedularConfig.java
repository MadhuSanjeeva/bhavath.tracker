package com.bhavath.tracker.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuazSchedularConfig {

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private AutowiringSpringBeanJobFactory autowireCapablities;

	private SchedulerFactory  factoryBean = new StdSchedulerFactory();

	@Bean(name = "scheduler")
	public Scheduler getSchedular() throws SchedulerException 
	{
		Scheduler scheduler = factoryBean.getScheduler();
		autowireCapablities.setApplicationContext(ctx);
		scheduler.setJobFactory(autowireCapablities);
		return scheduler;
	}
}
