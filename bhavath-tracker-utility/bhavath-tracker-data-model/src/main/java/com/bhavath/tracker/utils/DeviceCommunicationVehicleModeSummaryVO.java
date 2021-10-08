package com.bhavath.tracker.utils;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceCommunicationVehicleModeSummaryVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String status;
    
    @Builder.Default
    private BigInteger count = BigInteger.ZERO;
}
