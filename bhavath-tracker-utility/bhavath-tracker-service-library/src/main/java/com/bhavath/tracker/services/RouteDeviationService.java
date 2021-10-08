package com.bhavath.tracker.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.constants.Constants;
import com.bhavath.tracker.data.model.RouteDeviation;
import com.bhavath.tracker.data.model.RouteDeviationView;
import com.bhavath.tracker.data.model.TripDetails;
import com.bhavath.tracker.data.repos.RouteDeviationRepository;
import com.bhavath.tracker.data.repos.RouteDeviationViewRepository;
import com.bhavath.tracker.data.repos.RouteDeviationViewSpecifications;
import com.bhavath.tracker.data.repos.TripDetailsRepository;
import com.bhavath.tracker.events.CreateRouteDeviationEvent;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadRouteDeviationSetEvent;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.util.Geocoder;
import com.bhavath.tracker.util.ObjectMapperUtils;
import com.bhavath.tracker.vos.ResponseVO;
import com.bhavath.tracker.vos.RouteDeviationVO;

import lombok.extern.slf4j.Slf4j;

public interface RouteDeviationService {
    public ResponseVO save(CreateRouteDeviationEvent event);

    public PageReadEvent<RouteDeviationVO> readDeviationData(ReadRouteDeviationSetEvent request);

    @Slf4j
    @Service
    public class impl implements RouteDeviationService {
        @Autowired
        private RouteDeviationRepository routeDeviationRepository;
        @Autowired
        private TripDetailsRepository tripDetailsRepository;
        @Autowired
        private RouteDeviationViewRepository routeDeviationViewRepository;
        

        @Override
        public ResponseVO save(CreateRouteDeviationEvent event) {
        	log.info("RouteDeviationService :: save :: start() ");
        	ResponseVO responseVO = new ResponseVO();
			try
			{
				RouteDeviationVO routeDeviationVO = event.getRouteDeviationVO();
				TripDetails tripDetails = tripDetailsRepository.getTripByIMEINumberAndStatus(routeDeviationVO.getImeiNumber());
				RouteDeviation routeDeviation = routeDeviationRepository.findByTrip(tripDetails);
				
				if (StringUtils.isEmpty(routeDeviation)) {
					String location = Geocoder.getFormattedAddress(routeDeviationVO.getLatLang());
			        routeDeviation = new RouteDeviation();	
			        routeDeviation.setCreatedDate(DateUitls.getCurrentSystemTimestamp());
			        routeDeviation.setDeviationTime(DateUitls.getCurrentSystemTimestamp());
			        routeDeviation.setLatLang(routeDeviationVO.getLatLang());
			        routeDeviation.setLocation(location);
			        routeDeviation.setTripDetails(tripDetails);
					routeDeviationRepository.save(routeDeviation);
					routeDeviationRepository.flush();
					responseVO.setCode(Constants.ResponseMessages.CODE_200);
					responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
				}
			}
			catch (Exception e)
			{
				log.info("Exception while saving Route Deviation :: " + e.getCause(),e);
				responseVO.setCode(Constants.ResponseMessages.CODE_500);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_500);
			}
			log.info("RouteDeviationService :: save :: start() ");
			return responseVO;
        }

		@Override
		public PageReadEvent<RouteDeviationVO> readDeviationData(ReadRouteDeviationSetEvent request) {
			Page<RouteDeviationView> dbContent = routeDeviationViewRepository.findAll(new RouteDeviationViewSpecifications(request.getSearchDate(),request.getSearchValue()),
					RouteDeviationViewSpecifications.constructPageSpecification(request.getPageable().getPageNumber(),
							request.getPageable().getPageSize()));
			List<RouteDeviationVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), RouteDeviationVO.class);
			Page<RouteDeviationVO> page = new PageImpl<>(content, request.getPageable(),
					dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}
    }
}
