package br.com.learning.springprocessingfile.application.helper.config;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncConfig.class);

	@Value("${spring.async.thread-name-prefix}")
    private String THREAD_NAME_PREFIX;
	
	@Value("${spring.async.queue-capacity}")
	private int QUEUE_CAPACITY;
	
	@Value("${spring.async.max-pool-size}")
	private int MAX_POOL_SIZE;
	
	@Value("${spring.async.core-pool-size}")
	private int CORE_POOL_SIZE;

	
	@Bean (name = "taskExecutor")
    public Executor taskExecutor() {
        
		LOGGER.info(
				"Creating Async Task Executor: CORE_POOL_SIZE={}; MAX_POOL_SIZE={}; QUEUE_CAPACITY={}, THREAD_NAME_PREFIX={}",
				CORE_POOL_SIZE, MAX_POOL_SIZE, QUEUE_CAPACITY, THREAD_NAME_PREFIX);

        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.initialize();
        return executor;
    }
	
	
}
