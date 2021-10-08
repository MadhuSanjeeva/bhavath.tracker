package com.bhavath.tracker.vos;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SIMDetailsVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Timestamp createdDate;
	private String telecomProvider;
	private String mobileNumber;
	private String status;
	private String imeiNumber;
	private String imsiNumber;
}
