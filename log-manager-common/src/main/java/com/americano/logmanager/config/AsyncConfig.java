package com.americano.logmanager.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Configuration
@EnableAsync
public class AsyncConfig {
	@Value("${logmanager.async.core.pool.size:5}")
	private Integer corePoolSize;

	@Value("${logmanager.async.max.pool.size:10}")
	private Integer maxPoolSize;

	@Value("${logmanager.async.queue.capacity:2147483647}")
	private Integer queueCapacity;

	@Bean
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setBeanName("asyncTaskExecutor");
		taskExecutor.setCorePoolSize(corePoolSize);
		taskExecutor.setMaxPoolSize(maxPoolSize);
		taskExecutor.setQueueCapacity(queueCapacity);
		taskExecutor.initialize();
		logConfigs();
		return taskExecutor;
	}

	private void logConfigs() {
		log.info("core-pool-size=" + corePoolSize + ", max-pool-size=" + maxPoolSize + ", queue-capacity=" + queueCapacity);
	}
}
