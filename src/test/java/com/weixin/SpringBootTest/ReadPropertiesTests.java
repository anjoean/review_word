package com.weixin.SpringBootTest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Assert;

/**
 * @author yan.yuchen111@ztesoft.com
 * @create 2019/1/14
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)


@SpringBootTest
public class ReadPropertiesTests {
    @Autowired
    private ReadProperties readProperties;


    @Test
    public void getHello() throws Exception {
        Assert.assertEquals(readProperties.getName(), "程序猿DD");
        Assert.assertEquals(readProperties.getTitle(), "Spring Boot教程");
        Assert.assertEquals(readProperties.getTitle(), "Spring Boot教程1");

    }

    @Test
    public void xxx()throws Exception{

        System.out.println("---");
    }
}
