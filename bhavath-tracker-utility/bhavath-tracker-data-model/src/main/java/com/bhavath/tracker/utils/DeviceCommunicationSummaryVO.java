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
public class DeviceCommunicationSummaryVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Builder.Default
    private BigInteger total = BigInteger.ZERO;
    
    @Builder.Default
    private BigInteger on = BigInteger.ZERO;
    
    @Builder.Default
    private BigInteger off = BigInteger.ZERO;
    
    private String action;
}
