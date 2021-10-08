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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.bhavath.tracker.data.model.VehicleDetails;
import com.bhavath.tracker.utils.DateUtils;

public class VehicleDetailsSpecifications implements Specification<VehicleDetails> {
    private static final long serialVersionUID = 1L;
    private String searchValue;
    private String searchDate;
    private String columnOrder;
    private String columnName;

    public VehicleDetailsSpecifications(String searchValue, String searchDate, String columnName, String columnOrder) {
        super();
        this.searchValue = searchValue;
        this.searchDate = searchDate;
        this.columnName = columnName;
        this.columnOrder = columnOrder;
    }

    /*public static Sort sortByIdAsc() {
        //return new Sort(Sort.Direction.DESC, "id");
    }*/

    public static Pageable constructPageSpecification(int pageIndex, int pageSize,String columnName, String columnOrder) {
    	System.out.println("inside construct");
    	Direction direction = null;
    	if(columnOrder.equals("DESC")) {
    		direction = Sort.Direction.DESC;
    	}
    	if(columnOrder.equals("ASC")) {
    		direction = Sort.Direction.ASC;
    	}
        return PageRequest.of(pageIndex, pageSize,new Sort(direction, columnName));
    }

    @Override
    public Predicate toPredicate(Root<VehicleDetails> root, CriteriaQuery<?> cq, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();
        if (!StringUtils.isEmpty(searchValue)) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("vehicleRegNumber"), searchValue));
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