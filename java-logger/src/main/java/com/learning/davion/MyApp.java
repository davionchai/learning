package com.learning.davion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyApp {
    private static final Logger logger = LoggerFactory.getLogger(MyApp.class);

    public static void main(String[] args) {
        logger.info("Application started");
        logger.debug("Debugging information");
        logger.error("An error occurred");
        logger.error("An error occurred", new RuntimeException("Test error"));
    }
}
