package com.americano.logmanager;

import com.americano.logmanager.gateway.request.RequestGateway;
import com.americano.logmanager.gateway.request.RequestGatewayConfig;
import com.americano.logmanager.gateway.request.error.ErrorGatewayConfig;
import com.americano.logmanager.gateway.request.error.FileWritingPolicy;
import com.americano.logmanager.gateway.request.error.RequestErrorHandlePolicy;
import com.americano.logmanager.gateway.request.error.RequestErrorHandler;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.test.context.event.RecordApplicationEvents;

import static org.junit.jupiter.api.Assertions.assertThrows;

@EnableIntegration
@RecordApplicationEvents
@IntegrationComponentScan(basePackages = "com.americano.logmanager")
@SpringBootTest(classes = {RequestGateway.class, LogMessageProducer.class, RequestGatewayConfig.class
		, ErrorGatewayConfig.class, RequestErrorHandlePolicy.class, RequestErrorHandler.class, FileWritingPolicy.class},
		properties = "application.properties")
public class LogMessageProducerTest {

	@Autowired
	private LogMessageProducer logMessageProducer;

	@Autowired
	private RequestGateway requestGateway;

	@Autowired
	private RequestErrorHandler errorHandler;

	@Test
	void topic_혹은_value가_null_이면_예외_발생해야_함() {
		//when & then
		assertThrows(IllegalArgumentException.class, () -> logMessageProducer.send(null, "not null"));
		assertThrows(IllegalArgumentException.class, () -> logMessageProducer.send("not null", null));
	}

	@Test
	void test() {
		logMessageProducer.send("test", new DummyObject("test object"));

	}

	private static class DummyObject {
		@JsonProperty("content")
		private String content;

		public DummyObject(String content) {
			this.content = content;
		}
	}
}