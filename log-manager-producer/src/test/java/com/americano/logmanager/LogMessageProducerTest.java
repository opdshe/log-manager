package com.americano.logmanager;

import com.americano.logmanager.config.AsyncConfig;
import com.americano.logmanager.gateway.request.RequestGateway;
import com.americano.logmanager.gateway.request.RequestGatewayConfig;
import com.americano.logmanager.gateway.request.error.ErrorGatewayConfig;
import com.americano.logmanager.gateway.request.error.FileWritingPolicy;
import com.americano.logmanager.gateway.request.error.RequestErrorHandlePolicy;
import com.americano.logmanager.gateway.request.error.RequestErrorHandler;
import com.americano.logmanager.gateway.request.success.RequestSuccessHandlePolicy;
import com.americano.logmanager.gateway.request.success.RequestSuccessHandler;
import com.americano.logmanager.gateway.request.success.SuccessGatewayConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageHandler;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@EnableIntegration
@IntegrationComponentScan(basePackages = "com.americano.logmanager")
@SpringBootTest(classes = {RequestGateway.class, LogMessageProducer.class, RequestGatewayConfig.class
		, ErrorGatewayConfig.class, RequestErrorHandlePolicy.class, RequestErrorHandler.class, FileWritingPolicy.class,
		SuccessGatewayConfig.class, RequestSuccessHandler.class, RequestSuccessHandlePolicy.class,
		AsyncConfig.class})
public class LogMessageProducerTest {
	private static final String TEST_TOPIC = "test";

	@Autowired
	private LogMessageProducer logMessageProducer;

	@Autowired
	private RequestGateway requestGateway;

	@MockBean
	private RequestErrorHandler requestErrorHandler;

	@MockBean
	private RequestSuccessHandler requestSuccessHandler;

	@MockBean(name = "kafkaMessageHandler")
	private MessageHandler kafkaMessageHandler;

	/**
	 * 테스트 실행 시 application.properties에 적절한 bootstrap-server 입력해두어야함
	 */

	@Test
	void 데이터_전송_성공시_에러_대응로직_실행되면_안됨() throws ExecutionException, InterruptedException {
		//given
		DummyObject dummyObject = new DummyObject("dummy");

		//when
		logMessageProducer.send(TEST_TOPIC, dummyObject);

		//then
		Mockito.verify(requestErrorHandler, Mockito.never()).handleMessage(any());
	}


	@Test
	void 데이터_전송중_예외발생시_대응로직_실행되어야함() throws ExecutionException, InterruptedException {
		//mocking
		Mockito.doThrow(new RuntimeException()).when(kafkaMessageHandler).handleMessage(any());

		//given
		DummyObject dummyObject = new DummyObject("dummy");

		//when
		logMessageProducer.send(TEST_TOPIC, dummyObject);
		Thread.sleep(250);

		//then
		Mockito.verify(requestErrorHandler, Mockito.atLeastOnce()).handleMessage(any());
		Mockito.verify(requestSuccessHandler, Mockito.never()).handleMessage(any());
	}

	@Test
	void topic_혹은_value가_null_이면_예외_발생해야_함() {
		//when & then
		assertThrows(IllegalArgumentException.class, () -> logMessageProducer.send(null, "not null"));
		assertThrows(IllegalArgumentException.class, () -> logMessageProducer.send("not null", null));
	}

	private static class DummyObject {
		@JsonProperty("content")
		private String content;

		public DummyObject(String content) {
			this.content = content;
		}
	}
}