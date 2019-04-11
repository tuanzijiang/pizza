package edu.ecnu.scsse.pizza.bussiness.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Rollback(value = true)
public class TestApplication{
    @Before
    public void init() {
        System.out.println("---------------开始测试-----------------");
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void contextLoads() {
    }

    @After
    public void after() {
        System.out.println("---------------测试结束-----------------");
    }
}
