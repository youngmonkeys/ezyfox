package com.tvd12.ezyfox.kafka.testing.entity;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.message.annotation.EzyMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EzyMessage
@NoArgsConstructor
@AllArgsConstructor
@EzyObjectBinding
public class KafkaChatMessage {

	@EzyId
	private long messageId;
	private String messageContent;
	
}
