package com.example.reactiveRateLimitingBucket4j.Storage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//this is what followed to create these
//https://spring.io/guides/gs/spring-data-reactive-redis/

@Configuration
public class CacheConfiguration {
    @Bean
    ReactiveRedisOperations<String, Integer> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Integer> serializer = new Jackson2JsonRedisSerializer<>(Integer.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Integer> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Integer> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

}