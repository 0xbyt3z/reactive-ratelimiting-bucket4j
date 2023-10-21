package com.example.reactiveRateLimitingBucket4j.Storage;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class CacheController {
    private final ReactiveRedisOperations<String, Integer> redisOperations;

    @GetMapping("/cache")
    public Flux<Integer> all() {
        return redisOperations.keys("*")
                .flatMap(redisOperations.opsForValue()::get);
    }
}