package com.bhavath.tracker.jobs;

import java.sql.Timestamp;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.data.model.DeviceCommunication;
import com.bhavath.tracker.data.model.VehicleDetails;
import com.bhavath.tracker.data.repos.DeviceCommunicationRepository;
import com.bhavath.tracker.data.repos.VehicleDetailsRepository;
import com.bhavath.tracker.util.SMSConfiguration;
import com.bhavath.tracker.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommStatusJob implements Job {
	@Autowired
	private DeviceCommunicationRepository deviceCommunicationRepository;
	@Autowired
	private VehicleDetailsRepository vehicleDetailsRepository;
	@Autowired
	private SMSConfiguration smsConfig;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		log.info("Inside Comm status JOB::: ");
		List<DeviceCommunication> listOfDeviceCommunications = deviceCommunicationRepository
				.findByUpdatedDateBeforeAndStatusEquals(new Timestamp(System.currentTimeMillis() - 1800000),
						"Communicating");
		if (!ObjectUtils.isEmpty(listOfDeviceCommunications)) {
			for (DeviceCommunication deviceCommunication : listOfDeviceCommunications) {
				deviceCommunication.setStatus("Not Communicating");
				deviceCommunicationRepository.save(deviceCommunication);
				VehicleDetails vehicleDetails = vehicleDetailsRepository
						.findByImeiNumber(deviceCommunication.getImeiNumber());

				if (!ObjectUtils.isEmpty(vehicleDetails) && !StringUtils.isEmpty(vehicleDetails.getMobileNumber())
						&& !StringUtils.isEmpty(vehicleDetails.getVehicleRegNumber())) {
					smsConfig.sendSms(vehicleDetails.getMobileNumber(),
							"$,Non Comm Alert," + vehicleDetails.getImeiNumber() + ","
									+ vehicleDetails.getVehicleRegNumber() + "," + DateUtils.getCurrentTimestamp()
									+ ",Non Comm Alert,* ");
				}
			}
		}
	}
}
