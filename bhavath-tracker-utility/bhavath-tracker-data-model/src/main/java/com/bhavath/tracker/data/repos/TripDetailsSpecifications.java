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

import com.bhavath.tracker.data.model.TripDetails;
import com.bhavath.tracker.utils.DateUtils;

public class TripDetailsSpecifications implements Specification<TripDetails> 
{
	private static final long serialVersionUID = 1L;
	private String imeiNumber;
	private String searchDate;
	private Boolean isTripClosed;
	private String tripId;
	private String identifier;
	private String searchValue;

	public TripDetailsSpecifications(String imeiNumber, String searchDate,Boolean isTripClosed,String tripId,String identifier, String searchValue) 
	{
		super();
		this.imeiNumber = imeiNumber;
		this.searchDate = searchDate;
		this.isTripClosed = isTripClosed;
		this.tripId = tripId;
		this.identifier = identifier;
		this.searchValue = searchValue;
	}
	public static Sort sortByIdAsc() 
	{
		return new Sort(Sort.Direction.DESC, "id");
	}
	public static Pageable constructPageSpecification(int pageIndex, int pageSize) 
	{
		return PageRequest.of(pageIndex, pageSize,sortByIdAsc());
	}

	@Override
	public Predicate toPredicate(Root<TripDetails> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (!StringUtils.isEmpty(imeiNumber)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("imeiNumber"),imeiNumber));
		}
		if (!StringUtils.isEmpty(isTripClosed)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("isTripClosed"),isTripClosed));
		}
		if (!StringUtils.isEmpty(identifier)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("identifier"),identifier));
		}
		if (!StringUtils.isEmpty(tripId)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("id"),tripId));
		}
		if (!StringUtils.isEmpty(searchValue))
		{
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(criteriaBuilder.equal(root.get("imeiNumber"),searchValue)));
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