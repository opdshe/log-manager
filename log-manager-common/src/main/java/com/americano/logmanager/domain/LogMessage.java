package com.americano.logmanager.domain;

import com.americano.logmanager.util.serializer.LocalDateTimeDeserializer;
import com.americano.logmanager.util.serializer.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Setter;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
public class LogMessage implements Serializable {
	@JsonProperty(value = "topic")
	private String topic;

	@JsonProperty("createAt")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
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
