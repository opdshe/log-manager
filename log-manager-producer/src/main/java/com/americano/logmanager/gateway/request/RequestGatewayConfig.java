package com.americano.logmanager.gateway.request;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PropertySource("classpath:application.properties")
@Configuration
public class RequestGatewayConfig {
	@Value("${logmanager.kafka.producer.bootstrap.servers}")
	private List<String> bootstrapServers;

	@Value("${logmanager.kafka.producer.acks:1}")
	private String ack;

	@Value("${logmanager.kafka.producer.compression.type:gzip}")
	private String compressionType;

	@Value("${logmanager.kafka.batch.size.config:50000}")
	private Integer batchSize;

	@Value("${logmanager.kafka.buffer.memory.config:33554432}")
	private Integer memoryConfig;

	@Value("${logmanager.kafka.linger.ms.config:5000}")
	private Integer lingerConfig;

	@Value("${logmanager.kafka.max.block.ms.config:6000}")
	private Integer maxBlockConfig;

	@Value("${logmanager.success.response:false}")
	private Boolean successResponse;

	@Bean
	public MessageChannel messageRequestChannel() {
		return new DirectChannel();
	}

	@Bean
	@ServiceActivator(inputChannel = "messageRequestChannel")
	public MessageHandler kafkaMessageHandler() {
		KafkaProducerMessageHandler<String, String> handler = new KafkaProducerMessageHandler<>(kafkaTemplate());
		//브로커가 종료되어 accumulator에서 대기 후 max.block 시간 초과 시 requestErrorChannel로 결과를 전송
		handler.setSendFailureChannelName("requestErrorChannel");
		//accumulator가 broker로 전송 성공하면 응답을 받는 채널(ack)
		if (successResponse) {
			handler.setSendSuccessChannelName("requestSuccessChannel");
		}
		return handler;
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> properties = new HashMap<>();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		properties.put(ProducerConfig.ACKS_CONFIG, ack);
		properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, compressionType);
		properties.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
		properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, memoryConfig);
		properties.put(ProducerConfig.LINGER_MS_CONFIG, lingerConfig);
		properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, maxBlockConfig);
		return properties;
	}
}
