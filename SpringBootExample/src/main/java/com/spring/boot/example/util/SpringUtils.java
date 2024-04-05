package com.spring.boot.example.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils {

    public static final String EMPTY_STRING = "";
    @Autowired
    private Environment environment;

    /**
     * {@link Environment#getProperty(String)}
     *
     * @param key - {@link String}
     * @return Value for {@code key} or {@link SpringUtils#EMPTY_STRING EMPTY_STRING} if no value is found
     */
    public String getProperty(String key) {
        String value = EMPTY_STRING;
        if (StringUtils.isNotEmpty(key)) {
            value = getProperty(key, EMPTY_STRING);
        }
        return value;
    }

    /**
     *
     * {@link Environment#getProperty(String, String)}
     *
     * @param key - {@link String}
     * @param defaultValue - {@link String}
     * @return Value for {@code key} or {@code defaultValue} if no value is found
     */
    public String getProperty(String key, String defaultValue){
        return environment.getProperty(key, defaultValue);
    }
}
