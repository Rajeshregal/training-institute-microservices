package com.institute.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class HeaderValidationFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        String clientId =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst("X-Client-Id");

        if (clientId == null || clientId.isBlank()) {

            log.error("Missing X-Client-Id header");

            exchange.getResponse()
                    .setStatusCode(HttpStatus.BAD_REQUEST);

            return exchange.getResponse().setComplete();
        }

        log.info("Client ID: {}", clientId);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -2;
    }
}