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

import com.bhavath.tracker.data.model.LMPPacketData;

public class LMPPacketDataSpecifications implements Specification<LMPPacketData> 
{
	private static final long serialVersionUID = 1L;
	private String imeiNumber;
	private String vehicleRegNo;
	private String startDate;
	private String endDate;
	private String searchValue;

	public LMPPacketDataSpecifications(String imeiNumber, String vehicleRegNo, String searchValue, String startDate,String endDate)
	{
		super();
		this.imeiNumber = imeiNumber;
		this.vehicleRegNo = vehicleRegNo;
		this.searchValue = searchValue;
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
	public Predicate toPredicate(Root<LMPPacketData> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (!StringUtils.isEmpty(imeiNumber)) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("imei"), imeiNumber));
		}
		if (!StringUtils.isEmpty(vehicleRegNo)) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("vehicleRegNo"), vehicleRegNo));
		}
		if (!StringUtils.isEmpty(searchValue))
		{
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(criteriaBuilder.equal(root.get("imei"),searchValue),
																		  criteriaBuilder.equal(root.get("vehicleRegNo"),searchValue)));
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