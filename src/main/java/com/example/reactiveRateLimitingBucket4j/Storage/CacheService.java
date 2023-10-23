package com.example.reactiveRateLimitingBucket4j.Storage;

import com.example.reactiveRateLimitingBucket4j.Entity.BucketEntity;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@Slf4j
@RequiredArgsConstructor
public class CacheService {

    private final ReactiveRedisOperations<String, BucketEntity> redisOperations;

    public Bucket getBucketFromCache(String apikey){
        Mono<BucketEntity> bucketEntity = redisOperations.opsForValue().get("apikey").doOnNext(k->
                {return Bucket.builder().build().}
                );

//        return Bucket.builder()
//                 .addLimit(Bandwidth.classic(bucketEntity.getCapacity(), Refill.intervally(bucketEntity.getTokens(), Duration.ofSeconds(bucketEntity.getInterval()))))
//                 .build();
    }


}
