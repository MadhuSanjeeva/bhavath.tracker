package com.bhavath.tracker.data.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bhavath.tracker.data.model.RouteDeviation;
import com.bhavath.tracker.data.model.RouteDeviationView;
import com.bhavath.tracker.data.model.TripDetails;

public interface RouteDeviationViewRepository extends JpaRepository<RouteDeviationView, Long>, JpaSpecificationExecutor<RouteDeviationView> {
	
}
