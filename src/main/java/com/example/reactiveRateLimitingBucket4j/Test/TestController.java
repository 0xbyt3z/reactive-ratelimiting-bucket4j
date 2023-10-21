package com.example.reactiveRateLimitingBucket4j.Test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {
    private final ReactiveRedisOperations<String, Integer> redisOperations;
    private final TestService testService;

    @GetMapping("/create")
    public Flux<Boolean> create() {
        return redisOperations.opsForValue().set("rate-limit", 5).flux();
    }


    @GetMapping("/limit")
    public Flux<Integer> limit() {
        return redisOperations.keys("rate-limit")
                .flatMap(redisOperations.opsForValue()::get);

    }

    @GetMapping("/consume")
    public Flux<Long> consume() {
        return testService.consumeAToken();
    }
}