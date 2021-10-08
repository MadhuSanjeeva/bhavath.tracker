package com.bhavath.tracker.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bhavath.tracker.data.model.UserDetails;

public interface UserDetailsRepository  extends TableRepository<UserDetails, Long>, JpaSpecificationExecutor<UserDetails> 
{

	UserDetails findByUsername(String username);

	UserDetails findByMobileNumber(String mobileNumber);
	
}
