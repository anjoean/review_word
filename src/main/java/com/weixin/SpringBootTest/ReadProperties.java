package com.weixin.SpringBootTest;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yan.yuchen111@ztesoft.weixin
 * @create 2019/1/14
 * @since 1.0.0
 */
@Component
public class ReadProperties {
    @Value("${com.didispace.blog.name}")
    private String name;
    @Value("${com.didispace.blog.title}")
    private String title;

    public ReadProperties(){
        this.name=name;
        this.title=title;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
