package com.example.reactiveRateLimitingBucket4j.Storage;

import com.example.reactiveRateLimitingBucket4j.Entity.BucketEntity;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class CacheController {
    private final ReactiveRedisOperations<String, BucketEntity> redisOperations;

    @GetMapping("/cache")
    public Flux<BucketEntity> all() {
        return redisOperations.keys("*")
                .flatMap(redisOperations.opsForValue()::get);
    }
}