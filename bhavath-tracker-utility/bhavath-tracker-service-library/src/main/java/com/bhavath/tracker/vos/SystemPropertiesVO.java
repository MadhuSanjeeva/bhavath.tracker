package com.bhavath.tracker.vos;

import java.sql.Timestamp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SystemPropertiesVO {
	
	private Long id;
	
	private String propertyName;
	
	private String propertyValue;

	private Timestamp createdDate;

	private Timestamp updatedDate;
}
