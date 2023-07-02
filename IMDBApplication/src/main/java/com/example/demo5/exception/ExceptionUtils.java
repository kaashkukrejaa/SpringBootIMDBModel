package com.example.demo5.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import java.util.function.Supplier;

public class ExceptionUtils {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionUtils.class);

    public static <T> T handleDataAccessException(DataAccessException e, Supplier<T> supplier) {
        logger.error("An error occurred while accessing the database", e);
        throw new RuntimeException("An error occurred while accessing the database", e);
    }
}
