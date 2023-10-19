package com.example.reactiveRateLimitingBucket4j.Test;


public class Test {

    private String message;

    public Test() {
    }

    public Test(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "message='" + message + '\'' +
                '}';
    }
}