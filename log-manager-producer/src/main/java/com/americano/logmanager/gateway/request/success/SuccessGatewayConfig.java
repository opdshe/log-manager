package com.americano.logmanager.gateway.request.success;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@RequiredArgsConstructor
@Configuration
public class SuccessGatewayConfig {
	private final RequestSuccessHandler successHandler;

	@Bean
	public MessageChannel requestSuccessChannel() {
		return new DirectChannel();
	}

	@Bean
	@ServiceActivator(inputChannel = "requestSuccessChannel")
	public MessageHandler successHandler() {
		return successHandler;
	}
}
