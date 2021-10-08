package com.bhavath.tracker.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bhavath.tracker.data.model.ModemDetailsView;

public interface ModemDetailsViewRepository extends TableRepository<ModemDetailsView, Long>, JpaSpecificationExecutor<ModemDetailsView> 
{
	
}
