package com.company.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static reactor.netty.http.HttpConnectionLiveness.log;

@Component

public class RequestLoggingFilter implements GlobalFilter, Ordered {
        private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        long startTime = System.currentTimeMillis();

        String path = exchange.getRequest()
                .getURI().getPath();

        String method = exchange.getRequest()
                .getMethod().name();

        log.info("Incoming Request: {} {}", method, path);

        return chain.filter(exchange)
                .doFinally(signal -> {

                    long duration =
                            System.currentTimeMillis() - startTime;

                    var status = exchange.getResponse()
                            .getStatusCode();

                    log.info(
                            "Request Completed: {} {} | Status: {} | Time: {} ms",
                            method, path, status, duration
                    );
                });
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
