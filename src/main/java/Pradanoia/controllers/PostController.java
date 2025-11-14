package Pradanoia.controllers;

import Pradanoia.config.AppConstants;
import Pradanoia.payloads.entities.PostDto;
import Pradanoia.payloads.entities.PostResponse;
import Pradanoia.payloads.utils.ApiResponse;
import Pradanoia.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = this.postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer id) {
        PostDto post = this.postService.getPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    //Gets posts by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostsByCategory(
            @PathVariable Integer categoryId,
            @RequestParam(value = "pageNum", defaultValue = AppConstants.PAGE_NUM, required = false) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
    ) {
        PostResponse res = this.postService.getPostsByCategory(categoryId, pageNum, pageSize);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    //Gets posts by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
        List<PostDto> posts = this.postService.getPostsByUsers(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    //Pagination & sorting
    @GetMapping("/postsPage")
    public ResponseEntity<PostResponse> getAllPostsByPage(
            @RequestParam(value = "pageNum", defaultValue = AppConstants.PAGE_NUM, required = false) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        PostResponse res = this.postService.getAllPostsByPage(pageNum, pageSize,sortBy,sortDir);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    //Gets posts by searching with keywords
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords) {
        List<PostDto> res = this.postService.searchPost(keywords);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) {
        PostDto savedPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer id) {
        PostDto updatedPost = this.postService.updatePost(postDto, id);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
        return new ResponseEntity<>(new ApiResponse("Post deleted successfully", true), HttpStatus.OK);
    }
}
