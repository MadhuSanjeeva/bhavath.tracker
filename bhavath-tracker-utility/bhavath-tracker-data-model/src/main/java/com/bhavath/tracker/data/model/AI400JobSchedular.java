package com.bhavath.tracker.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@SequenceGenerator(name = "ai_400_job_schedular_seq", sequenceName = "ai_400_job_schedular_seq", initialValue = 1)
@Entity
@Table(name = "ai_400_job_schedular")
public class AI400JobSchedular  implements Serializable 
{

  private static final long serialVersionUID = -30595648297874805L;

  @Id
  @GeneratedValue(generator = "ai_400_job_schedular_seq")
  @Column(name = "id")
  private Long id;

  @Column(name = "cron_expression")
  private String cronExp;

  @Column(name = "scheduler_status")
  private String status;

  @Column(name = "job_type")
  private String jobType;
  
  @Column(name="description")
  private String description;

}
