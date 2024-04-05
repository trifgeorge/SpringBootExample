package com.spring.boot.example.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class SpringUtilsTest {

    @Autowired
    SpringUtils springUtils;

   @Test
    public void testReadProperty(){
       Assertions.assertEquals(springUtils.getProperty("env"),"TEST_ENV");
       Assertions.assertEquals(springUtils.getProperty("INVALID_PROPERTY"),"");
       Assertions.assertEquals(springUtils.getProperty("INVALID_PROPERTY","DEFAULT_VALUE"),"DEFAULT_VALUE");
    }
  
}