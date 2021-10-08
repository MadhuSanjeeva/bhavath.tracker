package com.bhavath.tracker.data.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bhavath.tracker.data.model.AI400JobSchedular;


public interface AI400JobSchedularRepository extends TableRepository<AI400JobSchedular, Long>, JpaSpecificationExecutor<AI400JobSchedular> 
{

  @Query(" Select ce from AI400JobSchedular ce where ce.status =:status")
  public List<AI400JobSchedular> getByStatus(@Param("status") String status);

  @Query("from AI400JobSchedular where jobType =:jobType")
  public AI400JobSchedular getByJobType(@Param("jobType") String jobType);

}
