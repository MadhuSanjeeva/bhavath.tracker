package com.bhavath.tracker.jobs.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bhavath.tracker.data.model.AI400JobSchedular;
import com.bhavath.tracker.data.repos.AI400JobSchedularRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JobsWatcher {

	private final String GROUPNAME = "AMR";

	@Autowired
	private AI400JobSchedularRepository jobsRepo;

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private JobsConfiguration jobsConfiguration;
	
	@Autowired
	private JobReSchedular cronSchedular;

	private SimpleDateFormat dateFormatGmt = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss");

	@Scheduled(cron = "0 * * * * *")
	public void watcher() {
		log.info("Watcher Scheduler Running.............." + dateFormatGmt.format(new Date()));
		if (!jobsRepo.findAll().isEmpty()) {
			List<AI400JobSchedular> records = jobsRepo.getByStatus("ACTIVE");
			
			for (AI400JobSchedular record : records) {
				Map<String, Object> map = new HashMap<String, Object>();
				PrepaidMeterTimeZone timeZone = PrepaidMeterTimeZone.UTC;
				String jobName = record.getJobType();
				log.info("JobsWatcher::::watcher:::"+jobName);
				try {
					CronTriggerImpl cronTrigger = (CronTriggerImpl) scheduler.getTrigger(new TriggerKey(jobName, GROUPNAME));
					log.info("JobsWatcher::::watcher:::"+cronTrigger);
					if (null != jobsConfiguration.getJobClass(jobName) && (null == cronTrigger
							|| !cronTrigger.getCronExpression().equals(record.getCronExp().trim()))) {
						log.info("cronTrigger............." + cronTrigger);
						Class<? extends Job> clazz = jobsConfiguration.getJobClass(jobName);
						cronSchedular.schedule(jobName, record.getCronExp().trim(), clazz, timeZone.utcTimeDifference,
								map);
					}
				} catch (SchedulerException e) {
					log.info("Exception in Schedular" + e.getMessage());
				}
			}
		}
	}
}
