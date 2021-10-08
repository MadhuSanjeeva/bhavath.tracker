package com.bhavath.tracker.vos;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SendSmsVO implements Serializable
{
	private static final long serialVersionUID = 1L;
    private String sender;
    private String content;
    private String inNumber;
    private String email;
    private String credits;
}
