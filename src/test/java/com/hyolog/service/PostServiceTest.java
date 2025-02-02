package com.hyolog.service;

import com.hyolog.domain.Post;
import com.hyolog.exception.PostNotFound;
import com.hyolog.repository.PostRepository;
import com.hyolog.request.PostCreate;
import com.hyolog.request.PostEdit;
import com.hyolog.request.PostSearch;
import com.hyolog.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        // 각각의 테스트가 수행되기 전에 실행한다
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();

        // when
        postService.write(postCreate);

        // then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다", post.getTitle());
        assertEquals("내용입니다", post.getContent());

    }

    @Test
    @DisplayName("글 한건 조회")
    void test2() {
        // given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(requestPost);

        // 클라이언트 요구사항
        //      -> json응답에서 title 값 길이를 최대 10글자로 해주세요


        // when
        PostResponse postResponse = postService.get(requestPost.getId());

        // then
        assertNotNull(postResponse);
        assertEquals("foo", postResponse.getTitle());
        assertEquals("bar", postResponse.getContent());
    }

    @Test
    @DisplayName("글 여러건 조회(페이징)")
    void test3() {
        // given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("블로그 제목 - " + i)
                        .content("블로그 내용 - " + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        // when
        // Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id" );
        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .build();

        List<PostResponse> postList = postService.getList(postSearch);

        // then
        assertEquals(10L, postList.size());
        assertEquals(postList.get(0).getTitle(), "블로그 제목 - 30");

    }

    @Test
    @DisplayName("글 제목 수정")
    void test4() {
        // given
        Post post = Post.builder()
                .title("블로그 제목")
                .content("블로그 내용")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                        .title("수정된 제목인데")
                        .build();

        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id="+post.getId()));
        assertEquals("수정된 제목인데", changePost.getTitle());

    }

    @Test
    @DisplayName("글 제목 수정")
    void test5() {
        // given
        Post post = Post.builder()
                .title("블로그 제목")
                .content("블로그 내용")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("수정된 제목인데")
                .build();

        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id="+post.getId()));
        assertEquals("수정된 제목인데", changePost.getTitle());

    }

    @Test
    @DisplayName("글 내용 수정")
    void test6() {
        // given
        Post post = Post.builder()
                .title("블로그 제목")
                .content("블로그 내용")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("블로그 제목")
                .content("쏼라쏼라 블로그")
                .build();

        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id="+post.getId()));
        assertEquals("블로그 제목", changePost.getTitle());
        assertEquals("쏼라쏼라 블로그", changePost.getContent());
    }

    @Test
    @DisplayName("글 삭제")
    void test7() {
        // given
        Post post = Post.builder()
                .title("블로그 제목")
                .content("블로그 내용")
                .build();

        postRepository.save(post);

        // when
        postService.delete(post.getId());

        // then
        Assertions.assertEquals(0, postRepository.count());
    }

    @Test
    @DisplayName("글 한건 조회 - 존재하지 않는 글")
    void test8() {
        // given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(requestPost);

        // expected
        Assertions.assertThrows(PostNotFound.class, () -> {
                postService.get(requestPost.getId() + 1L);
        });
    }

    @Test
    @DisplayName("글 삭제 - 조회하지 않는 글")
    void test9() {
        // given
        Post post = Post.builder()
                .title("블로그 제목")
                .content("블로그 내용")
                .build();

        postRepository.save(post);

        // expected
        Assertions.assertThrows(PostNotFound.class, () -> {
            postService.delete(post.getId() + 1L);
        });
    }

    @Test
    @DisplayName("글 내용 수정 - 존재하지 않는 글")
    void test10() {
        // given
        Post post = Post.builder()
                .title("블로그 제목")
                .content("블로그 내용")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("블로그 제목")
                .content("쏼라쏼라 블로그")
                .build();

        // expected
        Assertions.assertThrows(PostNotFound.class, () -> {
            postService.edit(post.getId() + 1L, postEdit);
        });
    }
}