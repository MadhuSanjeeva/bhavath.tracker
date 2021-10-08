package com.bhavath.tracker.data.repos;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.data.model.RawPacketData;

public class RawDataSpecifications implements Specification<RawPacketData> 
{
	private static final long serialVersionUID = 1L;
	private String imeiNumber;
	private String inBoundChannel;
	private String cvpType;
	private Date startDate;
	private Date endDate;
	private String packetStatus;

	public RawDataSpecifications(String imeiNumber,Date startDate,Date endDate, String inBoundChannel, String cvpType,String packetStatus)
	{
		super();
		this.imeiNumber = imeiNumber;
		this.startDate = startDate;
		this.endDate = endDate;
		this.inBoundChannel = inBoundChannel;
		this.cvpType = cvpType;
		this.packetStatus = packetStatus;
	}
	public static Sort sortByIdAsc() 
	{
		return new Sort(Sort.Direction.DESC, "networkDate","networkTime");
	}
	public static Pageable constructPageSpecification(int pageIndex, int pageSize) 
	{
		return PageRequest.of(pageIndex, pageSize,sortByIdAsc());
	}

	@Override
	public Predicate toPredicate(Root<RawPacketData> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		
		if (!StringUtils.isEmpty(imeiNumber))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("imeiNumber"),imeiNumber));
		}
		if (!ObjectUtils.isEmpty(startDate) && !ObjectUtils.isEmpty(endDate))  {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("networkDate"),startDate, endDate));
		}
		if (!ObjectUtils.isEmpty(startDate) && ObjectUtils.isEmpty(endDate))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("networkDate"), startDate));
		}
		if (ObjectUtils.isEmpty(startDate) && !ObjectUtils.isEmpty(endDate))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("networkDate"), endDate));
		}
		if (!StringUtils.isEmpty(inBoundChannel))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("inBoundChannel"), inBoundChannel));
		}
		if (!StringUtils.isEmpty(cvpType))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("cvpType"), cvpType));
		}
		if (!StringUtils.isEmpty(packetStatus))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("packetStatus"), packetStatus));
		}
		return predicate;
	}
}