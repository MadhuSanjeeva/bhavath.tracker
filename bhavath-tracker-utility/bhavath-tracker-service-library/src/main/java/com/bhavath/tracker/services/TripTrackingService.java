package com.bhavath.tracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.constants.Constants;
import com.bhavath.tracker.data.model.TripDetails;
import com.bhavath.tracker.data.model.TripTracking;
import com.bhavath.tracker.data.repos.TripDetailsRepository;
import com.bhavath.tracker.data.repos.TripTrackingRepository;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.ResponseVO;
import com.bhavath.tracker.vos.TripTrackingVO;

import lombok.extern.slf4j.Slf4j;

public interface TripTrackingService {

	public ResponseVO save(TripTrackingVO tripTrackingVO);

	@Service
	@Slf4j
	public class impl implements TripTrackingService {
		
		@Autowired
		private TripTrackingRepository tripTrackingRepository;
		@Autowired
		private TripDetailsRepository tripDetailsRepository;

		@Override
		public ResponseVO save(TripTrackingVO tripTrackingVO) {
			log.info("TripTrackingService :: save :: start() ");
			ResponseVO responseVO = new ResponseVO();
			try
			{
				log.info("IMEI number : "+tripTrackingVO.getImeiNumber());
				
				if(!tripTrackingVO.getLatitude().equals(".000000") || !tripTrackingVO.getLongitude().equals(".000000")) {
					TripDetails tripDetails = tripDetailsRepository.getTripByIMEINumberAndStatus(tripTrackingVO.getImeiNumber());
					if(!StringUtils.isEmpty(tripDetails)) {
						TripTracking tripTracking = new TripTracking();
						tripTracking.setCreatedDate(DateUtils.getCurrentSystemTimestamp());
						tripTracking.setImeiNumber(tripTrackingVO.getImeiNumber());
						tripTracking.setLatitude(tripTrackingVO.getLatitude());
						tripTracking.setLongitude(tripTrackingVO.getLongitude());
						tripTracking.setLocation(tripTrackingVO.getLocation());
						tripTracking.setLatitudeDirection(tripTrackingVO.getLatitudeDirection());
						tripTracking.setLongitudeDirection(tripTrackingVO.getLongitudeDirection());
						tripTracking.setNetworkDate(tripTrackingVO.getNetworkDate());
						tripTracking.setNetworkTime(tripTrackingVO.getNetworkTime());
						tripTracking.setTripDetails(tripDetails);
						tripTrackingRepository.save(tripTracking);
						tripTrackingRepository.flush();
						log.info("TripTrackingService :: save :: end() "+tripTracking);
						responseVO.setCode(Constants.ResponseMessages.CODE_200);
						responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
					}else {
						log.info("No Trips are available for this IMEI number:  "+tripTrackingVO.getImeiNumber());
						responseVO.setCode(Constants.ResponseMessages.CODE_200);
						responseVO.setMessage("No Trips are available for this IMEI number:  "+tripTrackingVO.getImeiNumber());
					}
				}else {
					log.info("Zero Latitude and Longitude for this IMEI number:  "+tripTrackingVO.getImeiNumber());
					responseVO.setCode(Constants.ResponseMessages.CODE_200);
					responseVO.setMessage("Zero Latitude and Longitude for this IMEI number:  "+tripTrackingVO.getImeiNumber());
				}
			}
			catch (Exception e)
			{
				log.info("Exception while saving TripTracking Details :: " + e.getCause(),e);
				responseVO.setCode(Constants.ResponseMessages.CODE_500);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_500);
			}
			log.info("TripTrackingService :: save :: end() ");
			return responseVO;
		}
	}
}
