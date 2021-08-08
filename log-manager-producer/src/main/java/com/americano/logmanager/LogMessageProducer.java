package com.americano.logmanager;

import com.americano.logmanager.domain.LogMessage;
import com.americano.logmanager.gateway.request.RequestGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LogMessageProducer {
	private final RequestGateway requestGateway;

	public void send(String topic, Object value) {
		LogMessage logMessage = generateLogMessage(topic, value);
		requestGateway.send(topic, logMessage);
	}

	private static LogMessage generateLogMessage(String topic, Object value) {
		return LogMessage.builder()
				.topic(topic)
				.value(value)
				.build();
	}
}
