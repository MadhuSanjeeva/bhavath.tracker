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

import com.bhavath.tracker.data.model.SystemProperties;
import com.bhavath.tracker.utils.DateUtils;

public class SystemPropertiesSpecifications implements Specification<SystemProperties> 
{
	private static final long serialVersionUID = 1L;
	private String searchValue;
	private String searchDate;
	
	
 
	

	public SystemPropertiesSpecifications(String searchValue,String searchDate)
	{
		super();
		this.searchValue = searchValue;
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
	public Predicate toPredicate(Root<SystemProperties> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) 
	{
		Predicate predicate = criteriaBuilder.conjunction();
		if (!StringUtils.isEmpty(searchValue))
		{
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.or(criteriaBuilder.equal(root.get("propertyName"),searchValue),criteriaBuilder.equal(root.get("propertyValue"),searchValue)));
		}
		 
		if (!StringUtils.isEmpty(searchDate))
		{
			try {
				Timestamp sDate = DateUtils.appendStartTime(searchDate);
				Timestamp eDate = DateUtils.appendEndTime(searchDate);
				predicate = criteriaBuilder.and(predicate,criteriaBuilder.between(root.get("createdDate"),sDate,eDate));

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return predicate;
	}
}