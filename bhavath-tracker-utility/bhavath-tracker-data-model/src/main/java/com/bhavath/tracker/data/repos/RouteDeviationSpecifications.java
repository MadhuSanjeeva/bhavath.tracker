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

import com.bhavath.tracker.data.model.RouteDeviation;
import com.bhavath.tracker.data.model.RouteDeviationView;
import com.bhavath.tracker.utils.DateUtils;

public class RouteDeviationSpecifications implements Specification<RouteDeviation> {
    private static final long serialVersionUID = 1L;
    private String createdDate;
    private String searchValue;

    public RouteDeviationSpecifications(String createdDate,String searchValue) {
        super();
        this.createdDate = createdDate;
        this.searchValue = searchValue;
    }

    public static Sort sortByIdAsc() {
        return new Sort(Sort.Direction.DESC, "id");
    }

    public static Pageable constructPageSpecification(int pageIndex, int pageSize) {
        return PageRequest.of(pageIndex, pageSize, sortByIdAsc());
    }

    public static Pageable constructPageSpecification(int pageIndex, int pageSize, Sort sort) {
        return PageRequest.of(pageIndex, pageSize, sort);
    }

    @Override
    public Predicate toPredicate(Root<RouteDeviation> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();
        if (!StringUtils.isEmpty(searchValue))
		{
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(criteriaBuilder.equal(root.get("imeiNumber"),searchValue)));
		}
        if (!StringUtils.isEmpty(createdDate)) 
		{
			try 
			{
				Timestamp sDate = DateUtils.appendStartTime(createdDate);
				Timestamp eDate = DateUtils.appendEndTime(createdDate);
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