package com.americano.logmanager.gateway.requesterror;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RequestErrorHandler implements MessageHandler {
	@Autowired
	private RequestErrorHandlePolicy policy;

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		MessageHandlingException failedMessage = (MessageHandlingException) message.getPayload();
		policy.handleMessage(failedMessage);
	}
}
