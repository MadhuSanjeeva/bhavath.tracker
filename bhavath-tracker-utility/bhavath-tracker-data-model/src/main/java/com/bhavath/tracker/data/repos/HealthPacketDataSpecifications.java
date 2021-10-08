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

import com.bhavath.tracker.data.model.HealthPacketData;

public class HealthPacketDataSpecifications implements Specification<HealthPacketData> 
{
	private static final long serialVersionUID = 1L;
	private String imeiNumber;
	private String searchDate;

	public HealthPacketDataSpecifications(String imeiNumber, String searchDate)
	{
		super();
		this.imeiNumber = imeiNumber;
		this.searchDate = searchDate;
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
	public Predicate toPredicate(Root<HealthPacketData> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (!StringUtils.isEmpty(imeiNumber)) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("imeiNumber"), imeiNumber));
		}
		if (!StringUtils.isEmpty(searchDate)) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("networkDate"), searchDate));
		}
		return predicate;
	}
}