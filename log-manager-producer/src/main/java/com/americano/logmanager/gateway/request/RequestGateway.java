package com.americano.logmanager.gateway.request;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@MessagingGateway(defaultRequestChannel = "messageRequestChannel")
public interface RequestGateway {
	void send(@Header(KafkaHeaders.TOPIC) String topic, Object value);
}
