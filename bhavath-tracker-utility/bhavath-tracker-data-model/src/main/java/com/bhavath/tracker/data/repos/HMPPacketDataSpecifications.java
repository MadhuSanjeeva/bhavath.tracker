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

import com.bhavath.tracker.data.model.HMPPacketData;

public class HMPPacketDataSpecifications implements Specification<HMPPacketData> 
{
	private static final long serialVersionUID = 1L;
	private String imeiNumber;
	private String startDate;
	private String endDate;

	public HMPPacketDataSpecifications(String imeiNumber, String startDate, String endDate)
	{
		super();
		this.imeiNumber = imeiNumber;
		this.startDate = startDate;
		this.endDate = endDate;
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
	public Predicate toPredicate(Root<HMPPacketData> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (!StringUtils.isEmpty(imeiNumber)) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("imeiNumber"), imeiNumber));
		}
		if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate))  {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("networkDate"),startDate, endDate));
		}
		if (!StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("networkDate"), startDate));
		}
		if (StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("networkDate"), endDate));
		}
		return predicate;
	}
}