package com.americano.logmanager.gateway.request.success;

import com.americano.logmanager.domain.LogMessage;
import com.americano.logmanager.util.JsonConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggingPolicy implements RequestSuccessHandlePolicy {

	@Override
	public void handleMessage(Message<?> message) {
		log.info(message.toString());
		LogMessage successMessage = (LogMessage) message.getPayload();
		String jsonMessage = JsonConverter.toJson(successMessage);
		String topic = successMessage.getTopic();
		RecordMetadata metadata = (RecordMetadata) message.getHeaders().get(KafkaHeaders.RECORD_METADATA);
		int partition = metadata.partition();
		logMessage(jsonMessage, topic, partition);
	}

	public void logMessage(String jsonMessage, String topic, int partition) {
		log.info("success to send data. topic=" + topic + ", partition=" + partition + ", data=" + jsonMessage);
	}
}
