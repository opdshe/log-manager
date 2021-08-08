package com.americano.logmanager.gateway.request.success;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RequestSuccessHandler implements MessageHandler {
	private final RequestSuccessHandlePolicy policy;

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		policy.handleMessage(message);
	}
}
