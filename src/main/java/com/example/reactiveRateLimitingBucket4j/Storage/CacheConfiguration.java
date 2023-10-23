package com.example.reactiveRateLimitingBucket4j.Storage;

import com.example.reactiveRateLimitingBucket4j.Entity.BucketEntity;
import io.github.bucket4j.Bucket;
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
    ReactiveRedisOperations<String, BucketEntity> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<BucketEntity> serializer = new Jackson2JsonRedisSerializer<>(BucketEntity.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, BucketEntity> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, BucketEntity> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

}