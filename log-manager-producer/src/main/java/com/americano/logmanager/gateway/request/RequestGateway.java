package com.americano.logmanager.gateway.request;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
@MessagingGateway(defaultRequestChannel = "messageRequestChannel", errorChannel = "requestErrorChannel")
public interface RequestGateway {
	@Async
	Future send(@Header(KafkaHeaders.TOPIC) String topic, Object value);
}
