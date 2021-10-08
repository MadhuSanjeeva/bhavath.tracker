package com.bhavath.tracker.data.repos;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.data.model.TripTracking;

public class TripTrackingSpecifications implements Specification<TripTracking> 
{
	private static final long serialVersionUID = 1L;
	private Long tripId;

	public TripTrackingSpecifications(Long tripId) 
	{
		super();
		this.tripId = tripId;
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
	public Predicate toPredicate(Root<TripTracking> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (!StringUtils.isEmpty(tripId)) 
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("tripDetails").get("id"),tripId));
		}
		return predicate;
	}
}