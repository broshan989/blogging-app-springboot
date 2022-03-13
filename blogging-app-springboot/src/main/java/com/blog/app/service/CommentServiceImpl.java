package com.blog.app.service;

import com.blog.app.dto.CommentDto;
import com.blog.app.entity.Comment;
import com.blog.app.entity.Post;
import com.blog.app.exception.BlogApiException;
import com.blog.app.exception.ResourceNotFoundException;
import com.blog.app.repository.CommentRepository;
import com.blog.app.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private final CommentRepository commentRepository;

    @Autowired
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }


    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = convertToComment(commentDto);
        System.out.println("in comment service");
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("..."));
        comment.setPost(post); //set post to comment entity
        Comment newComment = commentRepository.save(comment); //save comment
        return convertToCommentDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> commentList = commentRepository.findByPostId(postId);
        List<CommentDto> commentDtoList = commentList.stream().map(c -> convertToCommentDto(c)).collect(Collectors.toList());
        return commentDtoList;
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {

        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post is not present"));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }

        return convertToCommentDto(comment);
    }

    @Override
    public CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto ) {

        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post is not present"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        commentRepository.save(comment);

        return convertToCommentDto(comment);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment doest not belong to post id");
        }

        commentRepository.delete(comment);
    }

    //convert comment dto to entity
    public Comment convertToComment(CommentDto commentDto){
        return Comment.builder()
                .id(commentDto.getId())
                .body(commentDto.getBody())
                .name(commentDto.getName())
                .email(commentDto.getEmail())
                .build();
    }

    //convert comment entity to dto
    public CommentDto convertToCommentDto(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .name(comment.getName())
                .email(comment.getEmail())
                .build();
    }
}
