package org.example.aispringboot.controller;

import org.example.aispringboot.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Test {
    @GetMapping("/test")
    public Result<String> test() {
        return Result.ok();
    }

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.ok("你好，这是我的第一个 Spring Boot 接口");
    }

    @GetMapping("/version")
    public Result<String> version() {
        return Result.ok("当前版本：v1.0");
    }

}


