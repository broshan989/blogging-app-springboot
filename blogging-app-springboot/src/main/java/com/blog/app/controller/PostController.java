package com.blog.app.controller;

import com.blog.app.dto.PostDto;
import com.blog.app.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //rest api for create post
    @PostMapping()
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.createPost(postDto));
    }

    //rest api for get all the posts
    @GetMapping("/get-all")
    public ResponseEntity<List<PostDto>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    //rest endpoint for get post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    //rest end point for deleting the post
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id){
        postService.deletePostById(id);
        return ResponseEntity.ok("Post has been deleted");
    }

    //rest endpoint for updating the post
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable(name = "id") long id, @RequestBody PostDto postDto){
        return ResponseEntity.ok(postService.updatePost(id,postDto));
    }

}
