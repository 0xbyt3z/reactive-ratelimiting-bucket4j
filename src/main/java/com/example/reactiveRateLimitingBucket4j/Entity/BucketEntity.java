package com.example.reactiveRateLimitingBucket4j.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.Bucket;
import lombok.*;

import java.beans.ConstructorProperties;
import java.time.Duration;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BucketEntity {

    @JsonProperty("apikey")
    private String apiKey;

    @JsonProperty("capacity")
    private Long capacity;
    @JsonProperty("tokens")
    private Long tokens;
    @JsonProperty("interval")
    private Long interval;
    @JsonProperty("remaining")
    private Long remaining;
}