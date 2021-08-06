package com.americano.logmanager;

import com.americano.logmanager.gateway.request.RequestGateway;
import com.americano.logmanager.gateway.request.RequestGatewayConfig;
import com.americano.logmanager.gateway.requesterror.ErrorGatewayConfig;
import com.americano.logmanager.gateway.requesterror.FileWritingPolicy;
import com.americano.logmanager.gateway.requesterror.RequestErrorHandlePolicy;
import com.americano.logmanager.gateway.requesterror.RequestErrorHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

import static org.junit.jupiter.api.Assertions.assertThrows;

@IntegrationComponentScan(basePackages = "com.americano.logmanager")
@EnableIntegration
@SpringBootTest(classes = {RequestGateway.class, LogMessageProducer.class, RequestGatewayConfig.class
		, ErrorGatewayConfig.class, RequestErrorHandlePolicy.class, RequestErrorHandler.class, FileWritingPolicy.class})
public class LogMessageProducerTest {

	@Autowired
	private LogMessageProducer logMessageProducer;

	@Autowired
	private RequestGateway requestGateway;

	@Test
	void topic_혹은_value가_null_이면_예외_발생해야_함() {
		//when & then
		assertThrows(IllegalArgumentException.class, () -> logMessageProducer.send(null, "not null"));
		assertThrows(IllegalArgumentException.class, () -> logMessageProducer.send("not null", null));
	}
}