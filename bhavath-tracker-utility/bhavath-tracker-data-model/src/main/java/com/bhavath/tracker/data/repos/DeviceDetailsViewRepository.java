package com.bhavath.tracker.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bhavath.tracker.data.model.DeviceDetailsView;

public interface DeviceDetailsViewRepository extends TableRepository<DeviceDetailsView, Long>, JpaSpecificationExecutor<DeviceDetailsView> 
{

}
