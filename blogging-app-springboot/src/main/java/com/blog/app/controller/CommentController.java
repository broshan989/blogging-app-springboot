package com.blog.app.controller;

import com.blog.app.dto.CommentDto;
import com.blog.app.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //rest endpoint for adding comment
    @PostMapping("/post/{id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "id") long postId, @RequestBody CommentDto commentDto){
        System.out.println("in comment controller");
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(postId, commentDto));
    }

    //rest endpoint to get all comments of the post
    @GetMapping("/post/{id}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(name = "id") long postId){
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    //rest endpoint to get comment by id
    @GetMapping("/post/{id}/comments/{cid}")
    public ResponseEntity<CommentDto> getCommentsByPostId(@PathVariable(name = "id") long postId,@PathVariable(name = "cid") long commentId){
        return ResponseEntity.ok(commentService.getCommentById(postId,commentId));
    }

    //rest endpoint for updating comment
    @PutMapping("/post/{postId}/comments/{cid}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable(value = "postId") long postId, @PathVariable(name = "cid") long commentId, @RequestBody CommentDto commentDto){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(commentService.updateCommentById(postId, commentId, commentDto));
    }

    //rest endpoint to delete comment
    @DeleteMapping("/post/{postId}/comments/{cid}")
    public ResponseEntity<String> deleteCommentById(@PathVariable(name = "postId") long postId, @PathVariable(name = "cid") long commentId){
        commentService.deleteCommentById(postId, commentId);
        return ResponseEntity.status(HttpStatus.OK).body("Comment has been deleted");
    }

}
