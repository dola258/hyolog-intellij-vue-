package com.hyolog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyolog.domain.Post;
import com.hyolog.repository.PostRepository;
import com.hyolog.request.PostCreate;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.regex.Matcher;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class rollerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        // 각각의 테스트가 수행되기 전에 실행한다
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청 시 Hello World를 출력한다.")
    void test() throws Exception {

        // given
        PostCreate request = PostCreate.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts") // application/json
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{}"))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청 시 title 값은 필수다.")
    void test2() throws Exception {

        // given
        PostCreate request = PostCreate.builder()
                .title(null)
                .content("내용입니다")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts") // application/json
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400")    )            // ErrorResponse 타입에 맞춰서 변경
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다.")) // ErrorResponse 타입에 맞춰서 변경
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요~")) // ErrorResponse 타입에 맞춰서 변경
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청 시 DB에 값이 저장된다.")
    void test3() throws Exception {

        // given
        PostCreate request = PostCreate.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/posts") // application/json
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        // then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다", post.getTitle());
        assertEquals("내용입니다", post.getContent());
    }

    @Test
    @DisplayName("글 한건 조회")
    void test4() throws Exception {
        // given
        Post requestPost = Post.builder()
                .title("123456789012345")
                .content("bar")
                .build();

        postRepository.save(requestPost);

        // expected(when + then)
        mockMvc.perform(get("/posts/{postId}", requestPost.getId()) // application/json
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(requestPost.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());

    }

    @Test
    @DisplayName("글 여러건 조회")
    void test5() throws Exception {
        // given
        Post requestPost1 = postRepository.save(Post.builder()
                .title("title_1")
                .content("content_1")
                .build());

        Post requestPost2 = postRepository.save(Post.builder()
                .title("title_2")
                .content("content_2")
                .build());

        // expected(when + then)
        mockMvc.perform(get("/posts") // application/json
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2))) // 길이 확인, is는 Matchers.is
                .andExpect(jsonPath("$[0].id").value(requestPost1.getId()))         // 첫번째 오브젝트의 아이디
                .andExpect(jsonPath("$[0].title").value("title_1"))     // 첫번째 오브젝트의 title
                .andExpect(jsonPath("$[0].content").value("content_1")) // 첫번째 오브젝트의 content
                .andExpect(jsonPath("$[1].id").value(requestPost2.getId()))         // 두번째 오브젝트의 아이디
                .andExpect(jsonPath("$[1].title").value("title_2"))     // 두번째 오브젝트의 title
                .andExpect(jsonPath("$[1].content").value("content_2")) // 두번째 오브젝트의 content
                .andDo(print());

    }
}