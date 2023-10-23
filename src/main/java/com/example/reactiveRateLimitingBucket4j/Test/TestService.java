package com.example.reactiveRateLimitingBucket4j.Test;

import com.example.reactiveRateLimitingBucket4j.Entity.BucketEntity;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@Slf4j
@RequiredArgsConstructor
public class TestService {

    private final ReactiveRedisOperations<String, BucketEntity> redisOperations;


    //create a bucket for each api
    //each individual user in this case
    public Flux<BucketEntity> resolveBucket() {

//        redisOperations.opsForValue().setIfAbsent("rate-limit",newBucket()).subscribe(System.out::println);

        var arr = redisOperations.keys("rate-limit")
                .flatMap(redisOperations.opsForValue()::get);
        //return a new Bucket if the cache does not contain the limit
        return arr;
    }



}
