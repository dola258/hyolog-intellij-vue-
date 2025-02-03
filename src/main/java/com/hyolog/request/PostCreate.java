package com.hyolog.request;

import com.hyolog.exception.InvalidRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@ToString
@Setter
@Getter
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요~")
    private String title;

    @NotBlank(message = "내용을 입력해주세요~")
    private String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validate() {
        // 허용하지 않은 단어
        if(title.contains("바보")) {
            throw new InvalidRequest("title", "제목에 부적잘한 단어가 포함되어 있습니다.");
        }

    }
}
