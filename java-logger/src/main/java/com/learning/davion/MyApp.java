package com.learning.davion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;

public class MyApp {
    private static final Logger logger = LoggerFactory.getLogger(MyApp.class);

    public static void main(String[] args) {
        JSONObject data = new JSONObject()
                .put("x", "123.45")
                .put("y", "123.45")
                .put("z", "123.45");
        String extra = new JSONObject()
                .put("data", data)
                .toString();

        // JSONObject test_data = new JSONObject(extra);
        // System.out.println(test_data);
        
        // logger.info("Application started");
        logger.debug("Debugging information", extra);
        // logger.error("An error occurred");
        // logger.error("test throwable as 2nd param", new RuntimeException("Test error"), extra);
        // logger.error("test throwable as 3rd param", extra, new RuntimeException("Test error"));
    }
}
