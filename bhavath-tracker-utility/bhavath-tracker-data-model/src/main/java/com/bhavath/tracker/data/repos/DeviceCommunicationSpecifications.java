package com.bhavath.tracker.data.repos;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.data.enums.DeviceCommunicationStatusEnum;
import com.bhavath.tracker.data.model.DeviceCommunication;

public class DeviceCommunicationSpecifications implements Specification<DeviceCommunication> {
    private static final long serialVersionUID = 1L;

    private String imeiNumber;
    private String searchDate;
    private String status;
    private String vehicleMode;
    private String ignitionStatus;
    private String emergencyStatus;
    private String mainPowerStatus;
    private String tamperAlert;
    private String memoryPercentage;
    private String batteryPercentage;

    public DeviceCommunicationSpecifications(String imeiNumber,String status,String searchDate,String vehicleMode,String ignitionStatus,String emergencyStatus
    										,String mainPowerStatus,String tamperAlert,String memoryPercentage,String batteryPercentage) {
        this.imeiNumber = imeiNumber;
        this.searchDate = searchDate;
        this.status = status;
        this.vehicleMode = vehicleMode;
        this.ignitionStatus = ignitionStatus;
        this.emergencyStatus = emergencyStatus;
        this.mainPowerStatus = mainPowerStatus;
        this.tamperAlert = tamperAlert;
        this.memoryPercentage = memoryPercentage;
        this.batteryPercentage = batteryPercentage;
    }

    public static Sort sortByIdAsc() {
        return new Sort(Sort.Direction.DESC, "networkDate","networkTime");
    }

    public static Pageable constructPageSpecification(int pageIndex, int pageSize) {
        return PageRequest.of(pageIndex, pageSize, sortByIdAsc());
    }

    @Override
    public Predicate toPredicate(Root<DeviceCommunication> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();
        if (!StringUtils.isEmpty(imeiNumber)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("imeiNumber"), imeiNumber));
        }
        if (!StringUtils.isEmpty(status)) {
        	if(status.equals(DeviceCommunicationStatusEnum.COMMUNICATING.name())) {
        		predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), DeviceCommunicationStatusEnum.COMMUNICATING.getType()));
        	}
        	if(status.equals(DeviceCommunicationStatusEnum.NOTCOMMUNICATING.name())) {
        		predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), DeviceCommunicationStatusEnum.NOTCOMMUNICATING.getType()));
        	}
        }
        if (!StringUtils.isEmpty(searchDate)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("networkDate"), searchDate));
        }
        if (!StringUtils.isEmpty(vehicleMode)) {
        	if(vehicleMode.equals(DeviceCommunicationStatusEnum.RUNNINGMODE.name())) {
        		predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("vehicleMode"), DeviceCommunicationStatusEnum.RUNNINGMODE.getType()));
        	}
        	if(vehicleMode.equals(DeviceCommunicationStatusEnum.SLEEPMODE.name())) {
        		predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("vehicleMode"), DeviceCommunicationStatusEnum.SLEEPMODE.getType()));
        	}
        }
        if (!StringUtils.isEmpty(ignitionStatus)) {
        	if(ignitionStatus.equals(DeviceCommunicationStatusEnum.ON.name())) {
        		predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("ignitionStatus"), DeviceCommunicationStatusEnum.ON.getType()));
        	}
        	if(ignitionStatus.equals(DeviceCommunicationStatusEnum.OFF.name())) {
        		predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("ignitionStatus"), DeviceCommunicationStatusEnum.OFF.getType()));
        	}
        }
        if (!StringUtils.isEmpty(emergencyStatus)) {
        	if(emergencyStatus.equals(DeviceCommunicationStatusEnum.ON.name())) {
        		predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("emergencyStatus"), DeviceCommunicationStatusEnum.ON.getType()));
        	}
        	if(emergencyStatus.equals(DeviceCommunicationStatusEnum.OFF.name())) {
        		predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("emergencyStatus"), DeviceCommunicationStatusEnum.OFF.getType()));
        	}
        }
        if (!StringUtils.isEmpty(mainPowerStatus)) {
            if(mainPowerStatus.equals(DeviceCommunicationStatusEnum.ON.name())) {
        		predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("mainPowerStatus"), DeviceCommunicationStatusEnum.ON.getType()));
        	}
        	if(mainPowerStatus.equals(DeviceCommunicationStatusEnum.OFF.name())) {
        		predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("mainPowerStatus"), DeviceCommunicationStatusEnum.OFF.getType()));
        	}
        }
        if (!StringUtils.isEmpty(tamperAlert)) {
            if(tamperAlert.equals(DeviceCommunicationStatusEnum.TAMPERON.name())) {
        		predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("tamperAlert"), DeviceCommunicationStatusEnum.TAMPERON.getType()));
        	}
        	if(tamperAlert.equals(DeviceCommunicationStatusEnum.TAMPEROFF.name())) {
        		predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("tamperAlert"), DeviceCommunicationStatusEnum.TAMPEROFF.getType()));
        	}
        }
        if (!StringUtils.isEmpty(memoryPercentage)) {
        	if(memoryPercentage.equals(DeviceCommunicationStatusEnum.GOOD.name())) {
        		predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("memoryPercentage"), DeviceCommunicationStatusEnum.GOOD.getType()));
        	}
        	if(memoryPercentage.equals(DeviceCommunicationStatusEnum.CRITICAL.name())) {
        		predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("memoryPercentage"), DeviceCommunicationStatusEnum.CRITICAL.getType()));
        	}
        }
        if (!StringUtils.isEmpty(batteryPercentage)) {
        	if(batteryPercentage.equals(DeviceCommunicationStatusEnum.GOOD.name())) {
        		predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("batteryPercentage"), DeviceCommunicationStatusEnum.GOOD.getType()));
        	}
        	if(batteryPercentage.equals(DeviceCommunicationStatusEnum.CRITICAL.name())) {
        		predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("batteryPercentage"), DeviceCommunicationStatusEnum.CRITICAL.getType()));
        	}
        }
        return predicate;
    }
}