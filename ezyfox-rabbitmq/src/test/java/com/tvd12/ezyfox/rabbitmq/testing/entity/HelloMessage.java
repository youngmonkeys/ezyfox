package com.tvd12.ezyfox.rabbitmq.testing.entity;

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
@EzyMessage
@ToString
@EzyObjectBinding
@AllArgsConstructor
@NoArgsConstructor
public class HelloMessage {

	@EzyId
	private long messageId;
	private String content;
	
	public HelloMessage(long messageId) {
		this.messageId = messageId;
		this.content = "Message#" + messageId;
	}
	
}
