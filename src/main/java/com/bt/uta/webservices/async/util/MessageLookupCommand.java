package com.bt.uta.webservices.async.util;

import javax.servlet.http.HttpServletRequest;

import com.bt.uta.webservices.async.dto.DataLayerRequest;
import com.bt.uta.webservices.async.dto.DataLayerResponse;
import com.bt.uta.webservices.async.services.MessageRepository;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * Hystrix is a library for implementing the Circuit Breaker pattern - protecting a back-end service
 * from overload by throttling and going 'open-circuit' (reject requests) when the backlog of requests
 * gets too big or the service starts responding with failures *
 */
public class MessageLookupCommand extends HystrixCommand<DataLayerResponse> {
    public static final Setter CONFIG =
            Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("webservice")).andCommandKey(HystrixCommandKey.Factory.asKey("name-lookup"))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationThreadInterruptOnTimeout(false).withCircuitBreakerEnabled(false).withRequestLogEnabled(true).withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE).withFallbackIsolationSemaphoreMaxConcurrentRequests(200))
                    .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withMaxQueueSize(100).withCoreSize(100));
    private MessageRepository messageRepository;
    private final HttpServletRequest req;

    public MessageLookupCommand(MessageRepository messageRepository, HttpServletRequest req) {
        super(CONFIG);
        this.messageRepository = messageRepository;
        this.req = req;
    }

    @Override
    protected String getCacheKey() {
        return DataLayerRequest.from(req.getParameterMap()).name;
    }

    protected DataLayerResponse run() throws Exception {
        return messageRepository.findMessageFor(DataLayerRequest.from(req.getParameterMap()));
    }
}
