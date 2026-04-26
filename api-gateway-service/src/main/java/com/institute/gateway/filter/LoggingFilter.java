package com.institute.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Component
@Slf4j
public class LoggingFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request=exchange.getRequest();
         log.info("REQUEST -> Method : {}, Path : {}",request.getMethod(),request.getPath());
         long starTime=System.currentTimeMillis();
        return chain.filter(exchange).then(
                Mono.fromRunnable(()-> {
                    long endTime=System.currentTimeMillis();
                    log.info("RESPONSE -> StatusCode : {}, Time Taken : {} ms",
                            exchange.getResponse().getStatusCode(),
                            (endTime - starTime));
                })
        );
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
