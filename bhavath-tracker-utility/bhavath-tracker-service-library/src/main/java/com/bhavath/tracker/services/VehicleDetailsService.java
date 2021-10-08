package com.bhavath.tracker.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.constants.Constants;
import com.bhavath.tracker.data.model.VehicleDetails;
import com.bhavath.tracker.data.repos.VehicleDetailsRepository;
import com.bhavath.tracker.data.repos.VehicleDetailsSpecifications;
import com.bhavath.tracker.events.CreateVehicleDetailsEvent;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadVehicleDetailsEvent;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.util.ObjectMapperUtils;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.ResponseVO;
import com.bhavath.tracker.vos.VehicleDetailsVO;

import lombok.extern.slf4j.Slf4j;

public interface VehicleDetailsService {

    public ResponseVO save(CreateVehicleDetailsEvent createVehicleDetailsEvent);
    public ResponseVO mapImeiVehicle(CreateVehicleDetailsEvent createVehicleDetailsEvent);
    public PageReadEvent<VehicleDetailsVO> readVehicleData(ReadVehicleDetailsEvent request);


    @Service
    @Slf4j
    public class impl implements VehicleDetailsService {

        @Autowired
        private VehicleDetailsRepository vehicleDetailsRepository;

        @SuppressWarnings("unlikely-arg-type")
        @Override
        public ResponseVO save(CreateVehicleDetailsEvent event) {
            log.info("VehicleDetailsService :: save :: start() ");
            ResponseVO responseVO = new ResponseVO();
            try
			{
				VehicleDetailsVO vehicleDetailsVO = event.getVehicleDetailsVO();
				VehicleDetails vehicleDetails = null;
				
				if (!StringUtils.isEmpty(vehicleDetailsVO.getId())) 
				{
					vehicleDetails = vehicleDetailsRepository.getOne(vehicleDetailsVO.getId());
				}else {
					vehicleDetails = vehicleDetailsRepository.findByChassisNumber(vehicleDetailsVO.getChassisNumber());
					if(vehicleDetails != null)
					{
						responseVO.setCode(Constants.ResponseMessages.CODE_400);
						responseVO.setMessage("Vehicle with Chassis number - " + vehicleDetailsVO.getChassisNumber() + " already exists.");
						 return responseVO;
					}
					vehicleDetails = vehicleDetailsRepository.findByEngineNumber(vehicleDetailsVO.getEngineNumber());
					if(vehicleDetails != null)
					{
						responseVO.setCode(Constants.ResponseMessages.CODE_400);
						responseVO.setMessage("Vehicle with Engine number - " + vehicleDetailsVO.getEngineNumber() + " already exists.");
						 return responseVO;
					}
					vehicleDetails = vehicleDetailsRepository.findByVehicleRegNumber(vehicleDetailsVO.getVehicleRegNumber());
					if(vehicleDetails != null)
					{
						responseVO.setCode(Constants.ResponseMessages.CODE_400);
						responseVO.setMessage("Vehicle with Vehicle number - " + vehicleDetailsVO.getVehicleRegNumber() + " already exists.");
						 return responseVO;
					}
					vehicleDetails = new VehicleDetails();
					vehicleDetails.setCreatedDate(DateUtils.getCurrentSystemTimestamp());
				}
				vehicleDetails.setVehicleRegNumber(vehicleDetailsVO.getVehicleRegNumber());
				vehicleDetails.setEngineNumber(vehicleDetailsVO.getEngineNumber());
				vehicleDetails.setChassisNumber(vehicleDetailsVO.getChassisNumber());
				vehicleDetails.setVehicleMake(vehicleDetailsVO.getVehicleMake());
				vehicleDetails.setVehicleModel(vehicleDetailsVO.getVehicleModel());
				vehicleDetails.setIsDeviceMapped(vehicleDetailsVO.getIsDeviceMapped());
				vehicleDetails.setMobileNumber(vehicleDetailsVO.getMobileNumber());
				vehicleDetailsRepository.save(vehicleDetails);
				vehicleDetailsRepository.flush();
				responseVO.setCode(Constants.ResponseMessages.CODE_200);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
			}
			catch (Exception e)
			{
				log.info("Exception while saving Vehicle Details :: " + e.getCause(),e);
				responseVO.setCode(Constants.ResponseMessages.CODE_500);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_500);
			}
            log.info("VehicleDetailsService :: save :: end() ");
            return responseVO;
        }

        @Override
        public PageReadEvent<VehicleDetailsVO> readVehicleData(ReadVehicleDetailsEvent request) {
            Page<VehicleDetails> dbContent = vehicleDetailsRepository.findAll(new VehicleDetailsSpecifications(request.getSearchValue(),request.getSearchDate(),request.getColumnName(),request.getColumnOrder()),
            		VehicleDetailsSpecifications.constructPageSpecification(request.getPageable().getPageNumber(),
                            request.getPageable().getPageSize(),request.getColumnName(),request.getColumnOrder()));
            List<VehicleDetailsVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), VehicleDetailsVO.class);
            Page<VehicleDetailsVO> page = new PageImpl<>(content, request.getPageable(),
                    dbContent != null ? dbContent.getTotalElements() : 0);
            return new PageReadEvent<>(page);
        }
        
        @Override
        public ResponseVO mapImeiVehicle(CreateVehicleDetailsEvent createVehicleDetailsEvent) 
		{
        	log.info("VehicleDetailsService :: mapImeiVehicle :: start() ");
			ResponseVO responseVO = new ResponseVO();
			try {
					VehicleDetailsVO vehicleDetailsVO = createVehicleDetailsEvent.getVehicleDetailsVO();
					VehicleDetails vehicleDetailsRepo = vehicleDetailsRepository.findByImeiNumber(vehicleDetailsVO.getImeiNumber());
					if(vehicleDetailsRepo != null) {
						log.info("VehicleDetailsService :: IMEI Number :"+vehicleDetailsRepo.getImeiNumber()+" is already mapped with Vehicle Number : "+vehicleDetailsRepo.getVehicleRegNumber());
						responseVO.setCode(Constants.ResponseMessages.CODE_400);
						responseVO.setMessage(Constants.ResponseMessages.MESSAGE_400 +" - IMEI Number :"+vehicleDetailsRepo.getImeiNumber()+" is already mapped with Vehicle Number : "+vehicleDetailsRepo.getVehicleRegNumber());
					}
					else {
						VehicleDetails vehicleDetails = vehicleDetailsRepository.findByVehicleRegNumber(vehicleDetailsVO.getVehicleRegNumber());
						vehicleDetails.setImeiNumber(vehicleDetailsVO.getImeiNumber());
						vehicleDetails.setIsDeviceMapped(Boolean.TRUE);
						vehicleDetails.setDeviceMappedDate(DateUitls.getCurrentSystemTimestamp());
						vehicleDetailsRepository.save(vehicleDetails);
						vehicleDetailsRepository.flush();
						log.info("VehicleDetailsService :: Vehicle Reg No :"+vehicleDetailsVO.getVehicleRegNumber()+" is mapped with IMEI Number : "+vehicleDetailsVO.getImeiNumber());
						responseVO.setCode(Constants.ResponseMessages.CODE_200);
						responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200 +" : Vehicle Reg No -"+vehicleDetailsVO.getVehicleRegNumber()+" is mapped with IMEI Number - "+vehicleDetailsVO.getImeiNumber());
					}
				} catch (Exception e) {
					log.info("Exception while Mapping IMEI Number with Vehicle :: " + e.getCause(),e);
					responseVO.setCode(Constants.ResponseMessages.CODE_500);
					responseVO.setMessage(Constants.ResponseMessages.MESSAGE_500);
				}
			log.info("VehicleDetailsService :: mapImeiVehicle :: end() ");
			return responseVO;
		}
    }
}
