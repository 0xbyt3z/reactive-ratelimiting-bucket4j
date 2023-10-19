package com.example.reactiveRateLimitingBucket4j.Test;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class TestService {


    //in memory cache
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    //create a bucket for each api
    //each individual user in this case
    public Bucket resolveBucket(String apiKey) {

        //look if the bucket exists
        //if not create a new value
//        https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentHashMap.html
        return cache.computeIfAbsent(apiKey, this::newBucket);
    }


    private Bucket newBucket(String apiKey) {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1))))
                .build();
    }
}
