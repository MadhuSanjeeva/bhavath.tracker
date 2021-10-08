package com.bhavath.tracker.data.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(name = "task_execution_seq", sequenceName = "task_execution_seq", initialValue = 1)
@Table(name = "task_execution")
public class TaskExecution 
{
	  @Id
	  @GeneratedValue(generator ="task_execution_seq")
	  private Long id;
	  private String value;
	 
	  @OneToOne
	  @JoinColumn(name = "task_id")
	  private Task task;
	  
	  /*@OneToOne
	  @JoinColumn(name = "prepaid_meter_service_id")
	  private PrepaidMeterService prepaidMeterService;*/
	  
	  private String status;
	  
	  @Column(name = "schedule_date")
	  private Date scheduleDate;
	  
	  @Column(name = "is_immidiate")
	  private boolean isImmidiate;
	  
	  @Column(name = "created_date")
	  private Timestamp createdDate;

	  @Column(name = "modem_response")
	  private String modemResponse;

	  @Column(name = "group_name")
	  private String groupName;
	  
	  @Column(name = "response_query")
	  private String responseQuery;
}
