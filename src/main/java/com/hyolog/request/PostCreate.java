package com.hyolog.request;

import lombok.ToString;

@ToString
public class PostCreate {

    public String title;
    public String content;

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
