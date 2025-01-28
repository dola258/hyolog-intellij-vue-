package com.hyolog.repository;

import com.hyolog.domain.Post;
import com.hyolog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
