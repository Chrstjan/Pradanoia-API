package Pradanoia.controllers;

import Pradanoia.config.AppConstants;
import Pradanoia.payloads.entities.CommentDto;
import Pradanoia.payloads.entities.PostResponse;
import Pradanoia.payloads.utils.ApiResponse;
import Pradanoia.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Integer id) {
        CommentDto comment = this.commentService.getCommentById(id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByUser(@PathVariable Integer userId) {
        List<CommentDto> comments = this.commentService.getCommentsByUsers(userId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<PostResponse> getCommentsByPost(
            @PathVariable Integer postId,
            @RequestParam(value = "pageNum", defaultValue = AppConstants.PAGE_NUM, required = false) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
    ) {
        PostResponse res = this.commentService.getCommentsByPost(postId, pageNum, pageSize);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/{userId}/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto, @PathVariable Integer userId, @PathVariable Integer postId) {
        CommentDto savedComment = this.commentService.createComment(commentDto, userId, postId);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@Valid @RequestBody CommentDto commentDto, @PathVariable Integer id) {
        CommentDto updatedComment = this.commentService.updateComment(commentDto, id);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(new ApiResponse("Comment deleted successfully", true), HttpStatus.OK);
    }
}
