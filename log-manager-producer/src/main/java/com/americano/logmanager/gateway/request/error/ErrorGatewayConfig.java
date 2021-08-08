package com.americano.logmanager.gateway.request.error;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@RequiredArgsConstructor
@Configuration
public class ErrorGatewayConfig {
	private final RequestErrorHandler requestErrorHandler;

	@Bean
	public MessageChannel requestErrorChannel() {
		return new DirectChannel();
	}

	@Bean
	@ServiceActivator(inputChannel = "requestErrorChannel")
	public MessageHandler errorHandler() {
		return requestErrorHandler;
	}
}
