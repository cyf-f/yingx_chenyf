package com.baizhi.cyf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@tk.mybatis.spring.annotation.MapperScan("com.baizhi.cyf.dao")
@org.mybatis.spring.annotation.MapperScan("com.baizhi.cyf.dao")
@SpringBootApplication
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
}
