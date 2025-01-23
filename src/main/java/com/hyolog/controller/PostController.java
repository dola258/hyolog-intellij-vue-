package com.hyolog.controller;

import com.hyolog.request.PostCreate;
import com.hyolog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 글 등록 (POST Method)
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        // case1. 저장한 데이터 Entity -> response로 응답
        // case2. 저장한 데이터의 primary_id만 -> response로 응답
        //        Client에서는 수신한 id를 글 조회 API를 통해서 데이터를 수신받음
        // case3. 응답 필요 없음 -> void로 작성(현재)
        postService.write(request);

    }
}
