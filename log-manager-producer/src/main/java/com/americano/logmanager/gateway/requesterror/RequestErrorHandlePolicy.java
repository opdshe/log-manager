package com.americano.logmanager.gateway.requesterror;

import org.springframework.messaging.MessageHandlingException;
import org.springframework.stereotype.Component;

@Component
public interface RequestErrorHandlePolicy {
	void handleMessage(MessageHandlingException failedMessage);
}
