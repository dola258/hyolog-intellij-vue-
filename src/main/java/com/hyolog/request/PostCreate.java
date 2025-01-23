package com.hyolog.request;

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
}
