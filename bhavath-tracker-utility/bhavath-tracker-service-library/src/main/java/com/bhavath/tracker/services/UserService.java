package com.bhavath.tracker.services;

import com.bhavath.tracker.data.model.Privilege;
import com.bhavath.tracker.data.model.Roles;
import com.bhavath.tracker.data.model.UserDetails;
import com.bhavath.tracker.data.repos.RolesRepositary;
import com.bhavath.tracker.data.repos.UserDetailsRepository;
import com.bhavath.tracker.data.repos.UserDetailsSpecifications;
import com.bhavath.tracker.events.CreateUserEvent;
import com.bhavath.tracker.events.PageReadEvent;
import com.bhavath.tracker.events.ReadUsersDataSetEvent;
import com.bhavath.tracker.util.DateUitls;
import com.bhavath.tracker.util.NepheleValidationUtils;
import com.bhavath.tracker.vos.PrivilagesVO;
import com.bhavath.tracker.vos.ResponseVO;
import com.bhavath.tracker.vos.RolesVO;
import com.bhavath.tracker.vos.UsersDetailsVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface UserService extends UserDetailsService
{
	public ResponseVO save(CreateUserEvent createUserEvent);
	public PageReadEvent<UsersDetailsVO> readUsersData(ReadUsersDataSetEvent request);
	public UsersDetailsVO readUserData(String username);
	
	@Slf4j
	@Service
	public class impl implements UserService
	{
		@Autowired private UserDetailsRepository repository;
		@Autowired private RolesRepositary rolesRepositary;
		
		@Override
		public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
		{
			log.info("---- START ---- REQUEST:: UserName - "+ username);
			UserDetails user = repository.findByUsername(username);
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user.getRoles()));

		
		}
		private List<GrantedAuthority> getAuthority(List<Roles> authorities) 
		{
			 return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
		}
		@Override
		public ResponseVO save(CreateUserEvent createUserEvent) 
		{
			ResponseVO responseVO = new ResponseVO();
			UserDetails users = new UserDetails();
			List<Roles> authorities = new ArrayList<>();
			UsersDetailsVO usersDetailsVO = createUserEvent.getUsersDetailsVO();
			if (usersDetailsVO.getId() == null)
			{
				users.setPassword(usersDetailsVO.getPassword());
				users.setCreatedDate(DateUitls.getCurrentSystemTimestamp());
				users.setLastPasswordResetDate(new Date(DateUitls.getSqlTimeStamp().getTime()));
				
				UserDetails user = repository.findByUsername(usersDetailsVO.getUsername());
				if (user != null)
				{
					responseVO.setMessage("User with user name " + usersDetailsVO.getUsername() + " already exist." );
					responseVO.setCode(402L);
					return responseVO;
				}
				user = repository.findByMobileNumber(usersDetailsVO.getMobileNumber());
				if (user != null)
				{
					responseVO.setMessage("User with Mobile Number " + usersDetailsVO.getUsername() + " already exist." );
					responseVO.setCode(402L);
					return responseVO;
				}
			}
			if (usersDetailsVO.getRoles() != null && usersDetailsVO.getRoles().length > 0)
			{
				for (Long roleId : usersDetailsVO.getRoles())
				{
					Roles  authority  = rolesRepositary.getOne(roleId);
					authorities.add(authority);
				}
				users.setRoles(authorities);
			}
			users.setUsername(usersDetailsVO.getUsername());
			users.setRoles(authorities);
			users.setFirstName(usersDetailsVO.getFirstName());
			users.setLastName(usersDetailsVO.getLastName());
			users.setIsEnabled(true);
			users.setEmailId(usersDetailsVO.getEmailId());
			users.setMobileNumber(usersDetailsVO.getMobileNumber());
			users.setId(usersDetailsVO.getId());
			repository.save(users);
			responseVO.setMessage("Success");
			responseVO.setCode(200L);
			return responseVO;
		}
		
		@Override
		public PageReadEvent<UsersDetailsVO> readUsersData(ReadUsersDataSetEvent request)
		{
			Page<UserDetails> dbContent = repository.findAll(new UserDetailsSpecifications(request.getUserName(),request.getMobileNumber(),request.getSearchValue(),request.getSearchDate()),UserDetailsSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize()));
			List<UsersDetailsVO> content = new ArrayList<>();
			for (UserDetails record : NepheleValidationUtils.nullSafe(dbContent)) 
			{
				List<Roles> roles = record.getRoles();
				List<RolesVO> rolesVos = new ArrayList<>();
				for (Roles role : roles)
				{
					List<PrivilagesVO> privilagesVOs = new ArrayList<>();
					for (Privilege privilege : role.getPrivileges())
					{
						PrivilagesVO privilagesVO = new PrivilagesVO();
						privilagesVO.setId(privilege.getId());
						privilagesVO.setName(privilege.getName());
						privilagesVOs.add(privilagesVO);
					}
					RolesVO rolesVO = new RolesVO();
					rolesVO.setId(role.getId());
					rolesVO.setName(role.getName());
					rolesVO.setPrivileges(privilagesVOs);
					rolesVos.add(rolesVO);
				}
				UsersDetailsVO details = UsersDetailsVO.builder()
					.firstName(record.getFirstName())
					.lastName(record.getLastName())
					.username(record.getUsername())
					.id(record.getId())
					.mobileNumber(record.getMobileNumber())
					.isEnabled(record.getIsEnabled())
					.emailId(record.getEmailId())
					.rolesVO(rolesVos)
					.createdDate(record.getCreatedDate() != null ? DateUitls.getStringFromTimestamp(record.getCreatedDate()) : null)
					.build();
				content.add(details); 
			}
			Page<UsersDetailsVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}
		@Override
		public UsersDetailsVO readUserData(String username) 
		{
			UserDetails record = repository.findByUsername(username);
			List<Roles> roles = record.getRoles();
			List<RolesVO> rolesVos = new ArrayList<>();
			for (Roles role : roles)
			{
				List<PrivilagesVO> privilagesVOs = new ArrayList<>();
				for (Privilege privilege : role.getPrivileges())
				{
					PrivilagesVO privilagesVO = new PrivilagesVO();
					privilagesVO.setId(privilege.getId());
					privilagesVO.setName(privilege.getName());
					privilagesVOs.add(privilagesVO);
				}
				RolesVO rolesVO = new RolesVO();
				rolesVO.setId(role.getId());
				rolesVO.setName(role.getName());
				rolesVO.setPrivileges(privilagesVOs);
				rolesVos.add(rolesVO);
			}
			UsersDetailsVO details = UsersDetailsVO.builder()
				.firstName(record.getFirstName())
				.lastName(record.getLastName())
				.username(record.getUsername())
				.id(record.getId())
				.mobileNumber(record.getMobileNumber())
				.isEnabled(record.getIsEnabled())
				.emailId(record.getEmailId())
				.rolesVO(rolesVos)
				.createdDate(record.getCreatedDate() != null ? DateUitls.getStringFromTimestamp(record.getCreatedDate()) : null)
				.build();
			return details;
		}
	}
}
