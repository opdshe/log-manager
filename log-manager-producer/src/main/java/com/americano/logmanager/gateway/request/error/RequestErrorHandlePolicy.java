package com.americano.logmanager.gateway.request.error;

import org.springframework.messaging.MessageHandlingException;

public interface RequestErrorHandlePolicy {
	void handleMessage(MessageHandlingException failedMessage);
}
