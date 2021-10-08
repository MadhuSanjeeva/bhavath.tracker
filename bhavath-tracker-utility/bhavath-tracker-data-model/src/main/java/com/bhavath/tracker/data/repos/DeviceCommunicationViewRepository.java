package com.bhavath.tracker.data.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bhavath.tracker.data.model.DeviceCommunicationView;

public interface DeviceCommunicationViewRepository extends JpaRepository<DeviceCommunicationView, Long>, JpaSpecificationExecutor<DeviceCommunicationView> {

}
