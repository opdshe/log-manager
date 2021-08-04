package com.americano.logmanager.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;

public class LogMessage implements Serializable {
	@JsonProperty(value = "topic")
	private String topic;

	@JsonProperty("createAt")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createAt;

	@JsonProperty("value")
	private Object value;

	@Builder
	public LogMessage(String topic, Object value) {
		validate(topic, value);
		this.topic = topic;
		this.value = value;
		this.createAt = LocalDateTime.now();
	}

	private void validate(String topic, Object value) {
		Assert.notNull(topic, "value는 null일 수 없습니다. ");
		Assert.notNull(value, "topic은 null일 수 없습니다. ");
	}
}
