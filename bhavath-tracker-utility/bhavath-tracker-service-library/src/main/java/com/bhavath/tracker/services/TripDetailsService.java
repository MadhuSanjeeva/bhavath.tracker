package com.bhavath.tracker.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.bhavath.tracker.constants.Constants;
import com.bhavath.tracker.data.model.TripDetails;
import com.bhavath.tracker.data.model.TripTracking;
import com.bhavath.tracker.data.repos.TripDetailsRepository;
import com.bhavath.tracker.data.repos.TripDetailsSpecifications;
import com.bhavath.tracker.data.repos.TripTrackingRepository;
import com.bhavath.tracker.data.repos.TripTrackingSpecifications;
import com.bhavath.tracker.events.CreateTripDetailsEvent;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadTripDetailsEvent;
import com.bhavath.tracker.events.ReadTripTrackingEvent;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.util.Geocoder;
import com.bhavath.tracker.util.ObjectMapperUtils;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.ResponseVO;
import com.bhavath.tracker.vos.TripDetailsVO;
import com.bhavath.tracker.vos.TripTrackingVO;

import lombok.extern.slf4j.Slf4j;

public interface TripDetailsService {

	public ResponseVO save(CreateTripDetailsEvent event);
	public ResponseVO endTrip(CreateTripDetailsEvent event);
	public PageReadEvent<TripDetailsVO> readTripDetails(ReadTripDetailsEvent request);
	public PageReadEvent<TripTrackingVO> readTripTracks(ReadTripTrackingEvent request);
	

	@Service
	@Slf4j
	public class impl implements TripDetailsService {
		
		@Autowired
		private TripDetailsRepository tripDetailsRepository;
		@Autowired
		private TripTrackingRepository tripTrackingRepository;

		@Override
		public ResponseVO save(CreateTripDetailsEvent event) {
			log.info("TripDetailsService :: save :: start() ");
			ResponseVO responseVO = new ResponseVO();
			try
			{
				TripDetailsVO tripDetailsVO = event.getTripDetailsVO();
				tripDetailsVO.setIdentifier(tripDetailsVO.getRadius() != null ? "GEOFENCING" : "TRIP");
				TripDetails tripDetailsRepo = tripDetailsRepository.getTripByIMEINumberAndStatus(tripDetailsVO.getImeiNumber());
				if(ObjectUtils.isEmpty(tripDetailsRepo)) {
					String sourceLocation = Geocoder.getFormattedAddress(tripDetailsVO.getSourceLatLang());
					String destiLocation = Geocoder.getFormattedAddress(tripDetailsVO.getDestiLatLang());
					TripDetails tripDetails = new TripDetails();
							tripDetails.setCreatedDate(DateUtils.getCurrentSystemTimestamp());
							tripDetails.setImeiNumber(tripDetailsVO.getImeiNumber());
							tripDetails.setSourceLatLang(tripDetailsVO.getSourceLatLang());
							tripDetails.setDestiLatLang(tripDetailsVO.getDestiLatLang());
							tripDetails.setSourceLocation(sourceLocation);
							tripDetails.setDestiLocation(destiLocation);
							tripDetails.setRadius(tripDetailsVO.getRadius());
							tripDetails.setIdentifier(tripDetailsVO.getIdentifier());
							tripDetails.setTripClosed(Boolean.FALSE);
					tripDetailsRepository.save(tripDetails);
					tripDetailsRepository.flush();
					responseVO.setCode(Constants.ResponseMessages.CODE_200);
					responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
				}
				else {
					log.info("Trip Already Exists for this IMEI Number ::  "+tripDetailsRepo.getImeiNumber()+" Identifier Type is : "+tripDetailsRepo.getIdentifier());
					responseVO.setCode(Constants.ResponseMessages.CODE_400);
					responseVO.setMessage("Trip Already Exists for this IMEI Number ::  "+tripDetailsRepo.getImeiNumber()+", Identifier Type is : "+tripDetailsRepo.getIdentifier());
				} 
			}
			catch (Exception e)
			{
				log.info("Exception while saving Trip Details :: " + e.getCause(),e);
				responseVO.setCode(Constants.ResponseMessages.CODE_500);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_500);
			}
			log.info("TripDetailsService :: save :: end() ");
			return responseVO;
		}
		
		@Override
		public ResponseVO endTrip(CreateTripDetailsEvent event)
		{
			log.info("TripDetailsService :: endTrip :: start() ");
			ResponseVO responseVO = new ResponseVO();
			try	
			{
				TripDetailsVO tripDetailsVO = event.getTripDetailsVO();
				TripDetails tripDetails = tripDetailsRepository.getTripByIMEINumberAndStatus(tripDetailsVO.getImeiNumber());
				if(!ObjectUtils.isEmpty(tripDetails)) {
					tripDetails.setCloseTime(DateUitls.getCurrentSystemTimestamp());
					tripDetails.setTripClosed(Boolean.TRUE);
					tripDetailsRepository.save(tripDetails);
					tripDetailsRepository.flush();
					responseVO.setCode(Constants.ResponseMessages.CODE_200);
					responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
				}else {
					log.info("No Trips are available for this IMEI Number ::  "+tripDetailsVO.getImeiNumber());
					responseVO.setCode(Constants.ResponseMessages.CODE_404);
					responseVO.setMessage("No Trips are available for this IMEI Number ::  "+tripDetailsVO.getImeiNumber());
				}
			}
			catch (Exception e) 
			{
				log.info("Exception while Closing Trip :: "+e.getCause(),e);
				responseVO.setCode(Constants.ResponseMessages.CODE_500);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_500);
			}
			log.info("TripDetailsService :: endTrip :: end() ");
			return responseVO;
		}

		@Override
		public PageReadEvent<TripDetailsVO> readTripDetails(ReadTripDetailsEvent request) {
			Page<TripDetails> dbContent = tripDetailsRepository.findAll(new TripDetailsSpecifications(request.getImeiNumber(),request.getSearchDate(),request.getIsTripClosed(),request.getTripId(),request.getIdentifier(),request.getSearchValue()),
					TripTrackingSpecifications.constructPageSpecification(request.getPageable().getPageNumber(),
							request.getPageable().getPageSize()));
			List<TripDetailsVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), TripDetailsVO.class);
			Page<TripDetailsVO> page = new PageImpl<>(content, request.getPageable(),
					dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}
		
		@Override
		public PageReadEvent<TripTrackingVO> readTripTracks(ReadTripTrackingEvent request) {
			Page<TripTracking> dbContent = tripTrackingRepository.findAll(new TripTrackingSpecifications(request.getTripId()),
					TripTrackingSpecifications.constructPageSpecification(request.getPageable().getPageNumber(),
							request.getPageable().getPageSize()));
			List<TripTrackingVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), TripTrackingVO.class);
			Page<TripTrackingVO> page = new PageImpl<>(content, request.getPageable(),
					dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}
	}
}
