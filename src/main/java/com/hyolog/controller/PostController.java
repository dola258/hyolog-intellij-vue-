package com.hyolog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PostController {

    // http Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT

    // 글 등록 (POST Method)
    @PostMapping("/posts")
    public String post(@RequestParam String title, @RequestParam String content) {
        //System.out.println("title={}, content={}", title, content);
        log.info("title={}, content={}", title, content);
        return "Hello World";
    }
}
