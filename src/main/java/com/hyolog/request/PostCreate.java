package com.hyolog.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요~")
    private String title;

    @NotBlank(message = "내용을 입력해주세요~")
    private String content;

}
