package com.americano.logmanager.gateway.request.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RequestErrorHandler implements MessageHandler {
	private final RequestErrorHandlePolicy policy;

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		MessageHandlingException failedMessage = (MessageHandlingException) message.getPayload();
		policy.handleMessage(failedMessage);
	}
}
