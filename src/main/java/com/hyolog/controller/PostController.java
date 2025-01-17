package com.hyolog.controller;

import com.hyolog.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class PostController {

    // http Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT

    // 글 등록 (POST Method)
    @PostMapping("/posts")
    public String post(PostCreate params) {
        //System.out.println("title={}, content={}", title, content);
        log.info("params: {}", params.toString());
        return "Hello World";
    }
}
