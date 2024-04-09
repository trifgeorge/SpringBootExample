package com.spring.boot.example.util;

import com.spring.boot.example.DefaultAbstractContextTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SpringUtilsTest extends DefaultAbstractContextTestClass {

    @Autowired
    SpringUtils springUtils;

   @Test
    public void testReadProperty(){
       Assertions.assertEquals(springUtils.getProperty("env"),"TEST_ENV");
       Assertions.assertEquals(springUtils.getProperty("INVALID_PROPERTY"),"");
       Assertions.assertEquals(springUtils.getProperty("INVALID_PROPERTY","DEFAULT_VALUE"),"DEFAULT_VALUE");
    }
  
}