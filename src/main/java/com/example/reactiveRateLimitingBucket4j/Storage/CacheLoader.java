package com.example.reactiveRateLimitingBucket4j.Storage;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CacheLoader {
    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, Integer> coffeeOps;


//    @PostConstruct
//    public void loadData() {
//        factory.getReactiveConnection().serverCommands().flushAll().thenMany(
//                        Flux.just("Jet Black Redis", "Darth Redis", "Black Alert Redis")
//                                .map(name -> new CacheEntity(UUID.randomUUID().toString(), name))
//                                .flatMap(coffee -> coffeeOps.opsForValue().set(coffee.getId(), coffee)))
//                .thenMany(coffeeOps.keys("*")
//                        .flatMap(coffeeOps.opsForValue()::get))
//                .subscribe(System.out::println);
//    }
}