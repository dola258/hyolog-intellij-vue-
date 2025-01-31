package com.hyolog.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PostEdit {


    @NotBlank(message = "타이틀을 입력해주세요~")
    private String title;

    @NotBlank(message = "내용을 입력해주세요~")
    private String content;

    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
