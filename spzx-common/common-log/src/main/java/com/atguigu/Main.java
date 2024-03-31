package com.atguigu;


import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.spring.annotation.MapperScan;

@RestController
@MapperScan("com.atguigu.log.mapper")
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}