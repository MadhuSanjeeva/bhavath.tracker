package com.bhavath.tracker.vos;

import java.io.Serializable;

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
public class SmsPacketTypesVO implements Serializable
{
	private static final long serialVersionUID = 1L;
    private String key;
    private String payload;
    private String description;
    private String commandType;
    private String imeiNumber;
}
