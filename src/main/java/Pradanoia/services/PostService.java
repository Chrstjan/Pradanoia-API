package Pradanoia.services;

import Pradanoia.payloads.entities.PostDto;
import Pradanoia.payloads.entities.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer id);

    void deletePost(Integer id);

    List<PostDto> getAllPosts();

    PostDto getPostById(Integer id);

    PostResponse getPostsByCategory(Integer categoryId, Integer pageNum,Integer pageSize);

    List<PostDto> getPostsByUsers(Integer userId);

    List<PostDto> searchPost(String keyword);

    PostResponse getAllPostsByPage(Integer pageNum, Integer pageSize, String sortBy, String sortDir);
}
