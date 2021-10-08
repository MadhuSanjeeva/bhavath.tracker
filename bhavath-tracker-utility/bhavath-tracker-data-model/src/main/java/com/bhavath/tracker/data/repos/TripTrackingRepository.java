package com.bhavath.tracker.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bhavath.tracker.data.model.TripTracking;

public interface TripTrackingRepository extends TableRepository<TripTracking, Long>, JpaSpecificationExecutor<TripTracking> 
{
	public TripTracking findByImeiNumber(String imeiNumber);
	
	/*@Query("FROM TripDetails where tripDetails=:tripDetails")
	public Set<TripDetails> getTripDetails(TripDetails tripDetails);*/
}
