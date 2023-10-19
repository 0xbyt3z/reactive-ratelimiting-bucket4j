package com.example.reactiveRateLimitingBucket4j.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class TestFilter implements WebFilter {

    @Autowired
//    private StatsService statsService;

    @Override
    public Mono<Void> filter(
            ServerWebExchange serverWebExchange,
            WebFilterChain webFilterChain) {

//        statsService.incrementRequestCounter();

        if (Objects.requireNonNull(
                        serverWebExchange.getResponse().getStatusCode())
                .is2xxSuccessful()) {
//            statsService.incrementSuccessCounter();
        }

        return webFilterChain.filter(serverWebExchange);
    }
}