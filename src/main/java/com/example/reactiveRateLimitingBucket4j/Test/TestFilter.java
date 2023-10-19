package com.example.reactiveRateLimitingBucket4j.Test;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class TestFilter implements WebFilter {

    private final TestService testService;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange,WebFilterChain webFilterChain) {

        //complete the response and reject the request
        if(true){
            ServerHttpResponse response = serverWebExchange.getResponse();
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.setComplete();

            //create a record of the apikey if not available
            Bucket bucket = this.testService.resolveBucket("Test-Token");
            ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

            if (probe.isConsumed()) {
                response.addCookie(ResponseCookie.from("X-Rate-Limit-Remaining",String.valueOf(probe.getRemainingTokens())).build());
            } else {
                long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
                response.addCookie(ResponseCookie.from("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill)).build());
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        }


        if (Objects.requireNonNull(serverWebExchange.getResponse().getStatusCode()).is2xxSuccessful()) {

            //log to confirm the filter works on request
            log.info(serverWebExchange.getRequest().getPath().toString());

            //log to confirm the filter works on response
            log.info(serverWebExchange.getResponse().getStatusCode().toString());
        }
        return webFilterChain.filter(serverWebExchange);
    }
}