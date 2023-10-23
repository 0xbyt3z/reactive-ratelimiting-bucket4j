package com.example.reactiveRateLimitingBucket4j.Test;

import com.example.reactiveRateLimitingBucket4j.Entity.BucketEntity;
import com.example.reactiveRateLimitingBucket4j.Storage.CacheService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {
    private final ReactiveRedisOperations<String, BucketEntity> redisOperations;
    private final TestService testService;
    private final CacheService cacheService;

    @GetMapping("/create")
    public Flux<Boolean> create() {
        BucketEntity bucketEntity = new BucketEntity("A001",5L, 5L,1L, 0L);

        return redisOperations.opsForValue().set("rate-limit", bucketEntity).flux();
    }
    @GetMapping("/get")
    public Mono<Bucket> get() {
        return Mono.just(cacheService.getBucketFromCache("A001"));
    }



    @GetMapping("/limit")
    public Flux<BucketEntity> limit() {
        return redisOperations.keys("rate-limit")
                .flatMap(redisOperations.opsForValue()::get);

    }
}