package com.bhavath.tracker.jobs.scheduler;

import java.util.Map;
import java.util.TimeZone;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JobReSchedular {

	private final String GROUPNAME = "AMR";

	@Autowired
	private Scheduler scheduler;

	public void schedule(String jobName, String cronExp, Class<? extends Job> clazz, String timeZone,Map<String, Object> jobData) 
	{
		log.info("JobReSchedular :schedule -START  ");
		try 
		{
			Trigger Trigger = scheduler.getTrigger(new TriggerKey(jobName, GROUPNAME));
			if (Trigger == null) 
			{
				JobDetail job = JobBuilder.newJob(clazz).withIdentity(jobName, GROUPNAME).setJobData(new JobDataMap(jobData)).build();
				Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, GROUPNAME)
								  .withSchedule(CronScheduleBuilder.cronSchedule(cronExp).inTimeZone(TimeZone.getTimeZone(timeZone)))
								  .build();
				scheduler.start();
				scheduler.scheduleJob(job, trigger);
				log.info("Job Scheduled for " + jobName + " at " + trigger.getNextFireTime());
			} 
			else 
			{
				Trigger newTrigger = TriggerBuilder.newTrigger().withIdentity(jobName, GROUPNAME)
									.withSchedule(CronScheduleBuilder.cronSchedule(cronExp).inTimeZone(TimeZone.getTimeZone(timeZone)))
									.build();
				scheduler.rescheduleJob(Trigger.getKey(), newTrigger);
				log.info("Job ReScheduled for " + jobName + " at " + newTrigger.getNextFireTime());
			}
		} 
		catch (Exception e) 
		{
			log.error("Exception in jobReScheduler  " + e.getCause(),e);
		}
		log.info("JobReSchedular :schedule -END ");
	}

}
