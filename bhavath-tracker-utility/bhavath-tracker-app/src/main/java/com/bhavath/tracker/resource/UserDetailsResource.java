package com.bhavath.tracker.resource;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.bhavath.tracker.vos.RolesVO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailsResource extends ResourceSupport 
{
	private Long userId;
	private String username;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private Boolean isEnabled;
	private long[] roles;
	private String password;
	private String emailId;
	private String createdDate;
	private List<RolesVO> rolesVO;
}