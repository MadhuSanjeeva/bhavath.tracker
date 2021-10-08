package com.bhavath.tracker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.bhavath.tracker.constants.Constants;
import com.bhavath.tracker.data.model.SystemProperties;
import com.bhavath.tracker.data.repos.SystemPropertiesRepository;
import com.bhavath.tracker.data.repos.SystemPropertiesSpecifications;
import com.bhavath.tracker.events.CreateSystemPropertiesEvent;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadSystemPropertiesEvent;
import com.bhavath.tracker.util.ObjectMapperUtils;
import com.bhavath.tracker.utils.DateUtils;
import com.bhavath.tracker.vos.ResponseVO;
import com.bhavath.tracker.vos.SystemPropertiesVO;

import lombok.extern.slf4j.Slf4j;

public interface SystemPropertiesService {

	
		public ResponseVO save(CreateSystemPropertiesEvent event);
		
		public PageReadEvent<SystemPropertiesVO> readData(ReadSystemPropertiesEvent request);

		
		@Service
		@Slf4j
		class Impl implements SystemPropertiesService{
			
			@Autowired
			SystemPropertiesRepository repositiory;
			

			@Override
			public ResponseVO save(CreateSystemPropertiesEvent event) {
				
				ResponseVO responseVO = new ResponseVO();
				try {
					SystemPropertiesVO vo = event.getSystemPropertiesVO();
					SystemProperties   systemProperties =SystemProperties.builder()
							.id(vo.getId())
							.propertyName(vo.getPropertyName())
							.propertyValue(vo.getPropertyValue())
							.build();
					
					if(vo.getId()==null) {
						systemProperties.setCreatedDate(DateUtils.getCurrentSystemTimestamp());
					}else {
						Optional<SystemProperties> dbContent = repositiory.findById(vo.getId());
						systemProperties.setCreatedDate(dbContent.get().getCreatedDate());
						systemProperties.setUpdatedDate(DateUtils.getCurrentSystemTimestamp());
					}
					repositiory.save(systemProperties);
					
					responseVO.setCode(Constants.ResponseMessages.CODE_200);
					responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);

					return responseVO;
					
				}catch (Exception e) {
					responseVO.setCode(Constants.ResponseMessages.CODE_500);
					responseVO.setMessage(Constants.ResponseMessages.MESSAGE_500);
					log.info("Exception while saving System Properties :: " + e.getCause(), e);
					return responseVO;
				}
				
			}


			@Override
			public PageReadEvent<SystemPropertiesVO> readData(ReadSystemPropertiesEvent request) {
					Page<SystemProperties> dbContent = repositiory.findAll(new SystemPropertiesSpecifications(request.getSearchValue(),request.getSearchDate()),SystemPropertiesSpecifications.constructPageSpecification(request.getPageable().getPageNumber(),request.getPageable().getPageSize()));
				List<SystemPropertiesVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), SystemPropertiesVO.class);
				Page<SystemPropertiesVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
				return new PageReadEvent<>(page);
			}
			
		}



}
