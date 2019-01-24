package com.tvd12.ezyfox.rabbitmq.message;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EzyRabbitSimpleMessageConfig 
		implements EzyRabbitMessageConfig, Serializable {
	private static final long serialVersionUID = -2082999913957502894L;
	
	protected Class<?> bodyType;
	
}
