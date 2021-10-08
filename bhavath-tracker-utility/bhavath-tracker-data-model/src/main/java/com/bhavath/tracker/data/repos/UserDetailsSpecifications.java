package com.bhavath.tracker.data.repos;

import java.sql.Timestamp;
import java.text.ParseException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.data.model.UserDetails;
import com.bhavath.tracker.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserDetailsSpecifications implements Specification<UserDetails> 
{
	private static final long serialVersionUID = 1L;
	private String userName;
	private String mobileNumber;
	private String searchValue;
	private String searchDate;

	public UserDetailsSpecifications(String userName, String mobileNumber,String searchValue, String searchDate) 
	{
		super();
		this.userName = userName;
		this.mobileNumber = mobileNumber;
		this.searchValue = searchValue;
		this.searchDate = searchDate;
	}
	public static Sort sortByIdAsc() 
	{
		return new Sort(Sort.Direction.ASC, "id");
	}
	public static Pageable constructPageSpecification(int pageIndex, int pageSize) 
	{
		return PageRequest.of(pageIndex, pageSize);
	}

	@Override
	public Predicate toPredicate(Root<UserDetails> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		log.info("mobileNUmber : "+ userName + " "+mobileNumber);
		Predicate predicate = criteriaBuilder.conjunction();
		if (userName != null) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("username"),userName));
		}
		if (mobileNumber != null) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("mobileNumber"),mobileNumber));
		}
		if (!StringUtils.isEmpty(searchValue))
		{
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(criteriaBuilder.equal(root.get("username"),searchValue),
																		  criteriaBuilder.equal(root.get("mobileNumber"),searchValue)));
		}
		if (!StringUtils.isEmpty(searchDate)) 
		{
			try 
			{
				Timestamp sDate = DateUtils.appendStartTime(searchDate);
				Timestamp eDate = DateUtils.appendEndTime(searchDate);
				predicate = criteriaBuilder.and(predicate,criteriaBuilder.between(root.get("createdDate"),sDate,eDate));
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
		}
		return predicate;
	}
}