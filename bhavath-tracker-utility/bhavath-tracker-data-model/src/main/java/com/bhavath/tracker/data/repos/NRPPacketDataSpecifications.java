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

import com.bhavath.tracker.data.model.EBPPacketData;
import com.bhavath.tracker.data.model.NRPPacketData;
import com.bhavath.tracker.utils.DateUtils;

public class NRPPacketDataSpecifications implements Specification<NRPPacketData> 
{
	private static final long serialVersionUID = 1L;
	private String vehicleRegNo;
	private String imeiNumber;
	private String searchValue;
	private String startDate;
	private String endDate;

	public NRPPacketDataSpecifications( String imeiNumber, String vehicleRegNo,String searchValue,String startDate,String endDate)
	{
		super();
		this.imeiNumber = imeiNumber;
		this.vehicleRegNo = vehicleRegNo;
		this.startDate = startDate;
		this.endDate = endDate;
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
	public Predicate toPredicate(Root<NRPPacketData> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		
		if (!StringUtils.isEmpty(vehicleRegNo)) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("vehicleRegNo"), vehicleRegNo));
		}
		if (!StringUtils.isEmpty(imeiNumber)) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("imeiNumber"), imeiNumber));
		}
		if (!StringUtils.isEmpty(searchValue))
		{
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(criteriaBuilder.equal(root.get("imeiNumber"),searchValue),
																		  criteriaBuilder.equal(root.get("vehicleRegNo"),searchValue)));
		}
		if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate))  {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("networkDate"),startDate, endDate));
		}
		if (!StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.between(root.get("networkDate"), startDate,DateUtils.getCurrentDateAsString(DateUtils.dd_MM_yyyy_str)));
		}
		if (StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("networkDate"), endDate));
		}
		return predicate;
	}
}