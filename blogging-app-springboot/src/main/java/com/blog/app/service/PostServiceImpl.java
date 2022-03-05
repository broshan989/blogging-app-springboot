package com.blog.app.service;

import com.blog.app.dto.PostDto;
import com.blog.app.entity.Post;
import com.blog.app.exception.ResourceNotFoundException;
import com.blog.app.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = Post.builder()
                .title(postDto.getTitle())
                .description(postDto.getDescription())
                .content(postDto.getContent())
                .build();

        Post newPost = postRepository.save(post);

        PostDto postDto1 = convertPostToDto(newPost);

        return postDto1;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        List<PostDto> dtoList = postList.stream().map(post -> convertPostToDto(post)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post with id: "+id+" is not found"));
        return convertPostToDto(post);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post with id: "+id+" is not found"));
        Post editedPost = Post.builder().id(id)
                .content(postDto.getContent())
                .description(postDto.getDescription())
                .title(postDto.getTitle())
                .build();
        postRepository.save(editedPost); //save the updated post
        return convertPostToDto(editedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post exist = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource does not exist"));
        postRepository.delete(exist);
    }


    //convert Post to PostDto
    public PostDto convertPostToDto(Post post){
        return PostDto.builder()
                .id(post.getId())
                .content(post.getContent())
                .description(post.getDescription())
                .title(post.getTitle())
                .build();
    }
}
