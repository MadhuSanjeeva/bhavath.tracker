package com.bhavath.tracker.jobs.scheduler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.quartz.Job;
import org.springframework.stereotype.Component;

import com.bhavath.tracker.jobs.CommStatusJob;

@Component
public class JobsConfiguration {

	private Map<String, Class<? extends Job>> jobsList = new HashMap<String, Class<? extends Job>>();
	private Map<JobTypes, Long> jobTypes = new HashMap<JobTypes, Long>();

	@PostConstruct
	public void setJobs() {
		jobsList.put(JobTypes.COMM_STATUS_JOB.name(), CommStatusJob.class);
	}

	public Class<? extends Job> getJobClass(String jobName) {
		if (jobsList.containsKey(jobName)) {
			return jobsList.get(jobName);
		}
		return null;
	}

	public boolean getAppLevelJobs(JobTypes types) {
		return jobTypes.containsKey(types);
	}
}
