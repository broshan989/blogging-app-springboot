package com.blog.app.service;

import com.blog.app.dto.PaginatedPostData;
import com.blog.app.dto.PostDto;

import java.util.List;

public interface PostService {
    public PostDto createPost(PostDto post);
    public List<PostDto> getAllPosts();
    public PaginatedPostData getAllPostsByPagination(int pageNo, int pageSize, String sortBy);
    public PostDto getPostById(long id);
    public PostDto updatePost(long id, PostDto postDto);
    public void deletePostById(long id);
}
