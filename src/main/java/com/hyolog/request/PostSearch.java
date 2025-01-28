package com.hyolog.request;

import lombok.Builder;
import lombok.Data;

@Data // Getter + Setter
public class PostSearch {

    private static final int MAX_SIZE = 2000;

    private Integer page;
    private Integer size;

    @Builder
    public PostSearch(Integer page, Integer size) {
        this.page = page == null ? 1 : page;
        this.size = size == null ? 10 : size;
    }

    public long getOffset() {
        return (long) (Math.max(1, page) - 1) * Math.min(size, MAX_SIZE);
    }
}
