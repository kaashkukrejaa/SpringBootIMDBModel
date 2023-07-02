package com.example.demo5.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLogger {
    public static Logger getLogger(Class<?> myClass) {
        return LoggerFactory.getLogger(myClass);
    }
}