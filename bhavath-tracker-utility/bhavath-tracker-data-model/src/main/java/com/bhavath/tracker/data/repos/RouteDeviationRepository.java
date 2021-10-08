package com.bhavath.tracker.data.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bhavath.tracker.data.model.RouteDeviation;
import com.bhavath.tracker.data.model.TripDetails;

public interface RouteDeviationRepository extends JpaRepository<RouteDeviation, Long>, JpaSpecificationExecutor<RouteDeviation> {
	
	@Query("FROM RouteDeviation where tripDetails=:tripDetails")
	RouteDeviation findByTrip(TripDetails tripDetails);
}
