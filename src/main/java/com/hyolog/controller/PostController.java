package com.hyolog.controller;

import com.hyolog.domain.Post;
import com.hyolog.request.PostCreate;
import com.hyolog.request.PostEdit;
import com.hyolog.request.PostSearch;
import com.hyolog.response.PostResponse;
import com.hyolog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /*
    *   /posts -> 글 전체 조회(검색 + 페이징)
    *   /posts/{postId} -> 글 한건 조회
    * */

    // 단건 조회 API
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable long postId) {
        // 응답클래스를 분리하세요(서비스 정책에 맞게)
        return postService.get(postId);
    }

    // 글이 너무 많은 경우 -> 비용이 너무 많이 든다
    // 글 100,000,000를 DB에서 조회하는 경우 -> DB가 뻗을 수 있다
    // DB -> 애플리케이션 서버로 전달하는 시간, 트래픽비용 등이 많이 발생할 수 있다

    // 다건 조회 API
    @GetMapping("/posts")
    public List<PostResponse> get(@ModelAttribute PostSearch postSearch) {
        // 응답클래스를 분리하세요(서비스 정책에 맞게)
        return postService.getList(postSearch);
    }

    // 게시글 수정
    @PatchMapping("/posts/{postId}")
    public PostResponse edit(@PathVariable long postId, @RequestBody @Valid PostEdit postEdit) {
        return postService.edit(postId, postEdit);
    }


}
