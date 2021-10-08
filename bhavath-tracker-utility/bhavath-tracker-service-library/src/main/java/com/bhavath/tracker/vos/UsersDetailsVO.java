package com.bhavath.tracker.vos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
@JsonInclude(Include.NON_DEFAULT)
public class UsersDetailsVO implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private Long id;
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
