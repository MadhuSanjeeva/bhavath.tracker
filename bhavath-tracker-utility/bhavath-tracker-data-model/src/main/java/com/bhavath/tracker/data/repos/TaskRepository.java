package com.bhavath.tracker.data.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bhavath.tracker.data.model.Task;

public interface TaskRepository extends TableRepository<Task, Long>, JpaSpecificationExecutor<Task> 
{
	public Task findByImeiNumber(String imeiNumber);
	
	@Query("FROM Task where imeiNumber=:imeiNumber and requestCommandName=:requestCommandName")
	public List<Task> getByImeiNumberAndCommandName(String imeiNumber, String requestCommandName);
	
	@Query("FROM Task where imeiNumber=:imeiNumber and header=:header and requestCommandName=:requestCommandName  and deviceResponseStatus=false order by createdDate desc")
	public List<Task> getByImeiNumberAndCommandNameAndStatus(@Param("imeiNumber") String imeiNumber, 
			                                           @Param("header") String header, 
			                                           @Param("requestCommandName") String requestCommandName);
	
	public Task findTopOneByImeiNumberAndHeaderAndRequestCommandNameOrderByCreatedDateDesc(@Param("imeiNumber") String imeiNumber, 
            @Param("header") String header, 
            @Param("requestCommandName") String requestCommandName);
	
}
