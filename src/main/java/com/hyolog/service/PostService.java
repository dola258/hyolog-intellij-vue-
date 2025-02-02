package com.hyolog.service;

import com.hyolog.domain.Post;
import com.hyolog.domain.PostEditor;
import com.hyolog.exception.PostNotFound;
import com.hyolog.repository.PostRepository;
import com.hyolog.request.PostCreate;
import com.hyolog.request.PostEdit;
import com.hyolog.request.PostSearch;
import com.hyolog.response.PostResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 게시글 작성
    public void write(PostCreate postCreate) {
        // postCreate -> Entity
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    // 단건 조회
    public PostResponse get(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new); // () -> new PostNotFound()

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    // 다건 조회
    public List<PostResponse> getList(PostSearch postSearch) {
        // 수동
        // Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id" ));

        return postRepository.getList(postSearch).stream()
                // .map(post -> new PostResponse(post))
                .map(PostResponse::new) // post를 PostResponse로 컨버팅
                .collect(Collectors.toList());

    }

    // 게시글 수정
    @Transactional
    public PostResponse edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        PostEditor postEditor = editorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.edit(postEditor);

        return new PostResponse(post);
    }

    // 게시글 삭제
    public void delete(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        postRepository.delete(post);
    }

}
