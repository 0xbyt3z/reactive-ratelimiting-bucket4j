package com.example.reactiveRateLimitingBucket4j.Test;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class TestService {

    private final ReactiveRedisOperations<String, Integer> redisOperations;


    //create a bucket for each api
    //each individual user in this case
    public Bucket resolveBucket() {

        var arr = redisOperations.keys("rate-limit")
                .flatMap(redisOperations.opsForValue()::get);

        //return a new Bucket if the cache does not contain the limit
        return newBucket();
    }

    private Bucket newBucket() {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(5, Refill.intervally(5, Duration.ofSeconds(30))))
                .build();
    }

    public Flux<Long> consumeAToken() {
        return redisOperations.keys("rate-limit").flatMap(redisOperations.opsForValue()::decrement).doOnNext(k -> log.debug("Consumed a token"));
    }

}
