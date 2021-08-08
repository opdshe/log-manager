package com.americano.logmanager.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogMessageTest {
	private static final String TEST_TOPIC = "test";

	@Test
	void 생성자중_하나라도_null이면_LogMessage_생성되면_안됨() {
		assertThrows(IllegalArgumentException.class, () -> {
			LogMessage.builder()
					.value(null)
					.topic(TEST_TOPIC)
					.build();
		});
	}
}