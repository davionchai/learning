package com.learning.davion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class MyApp {
    private static final Logger logger = LoggerFactory.getLogger(MyApp.class);

    public static void main(String[] args) {
        Map<String, Object> data = new HashMap<>();
        data.put("x", "123.45");
        data.put("y", "123.45");
        data.put("z", "123.45");
        Map<String, Object> extra = new HashMap<>();
        extra.put("data", data);
        String extraDto = "{}";

        try {
            extraDto = new ObjectMapper().writeValueAsString(extra);
        } catch (JsonProcessingException err) {
            logger.error("converting extra to dto failed");
        }

        logger.info("Application started");
        logger.debug("Debugging information", extraDto);
        logger.error("An error occurred");
        logger.error("test throwable as 2nd param", new RuntimeException("Test error"), extraDto);
        logger.error("test throwable as 3rd param", extraDto, new RuntimeException("Test error"));
    }
}
