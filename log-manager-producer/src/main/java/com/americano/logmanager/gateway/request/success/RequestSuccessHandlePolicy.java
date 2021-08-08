package com.americano.logmanager.gateway.request.success;

import org.springframework.messaging.Message;

public interface RequestSuccessHandlePolicy {
	void handleMessage(Message<?> message);
}
