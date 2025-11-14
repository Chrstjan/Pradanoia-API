package Pradanoia.services.impl;

import Pradanoia.entities.Category;
import Pradanoia.entities.Post;
import Pradanoia.entities.User;
import Pradanoia.exceptions.ResourceNotFoundException;
import Pradanoia.payloads.entities.PostDto;
import Pradanoia.payloads.entities.PostResponse;
import Pradanoia.repositories.CategoryRepo;
import Pradanoia.repositories.PostRepo;
import Pradanoia.repositories.UserRepo;
import Pradanoia.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    private Post DtoToPost(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }

    private PostDto PostToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

        Post post = this.DtoToPost(postDto);
        post.setCreatedAt(new Date());
        post.setAuthor(user);
        post.setCategory(category);

        Post savedPost = this.postRepo.save(post);
        return this.PostToDto(savedPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer id) {
        Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepo.save(post);
        return this.PostToDto(updatedPost);
    }

    @Override
    public void deletePost(Integer id) {
        Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
        postRepo.delete(post);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = this.postRepo.findAll();

        return posts.stream().map(post -> this.PostToDto(post)).toList();
    }

    @Override
    public PostDto getPostById(Integer id) {
        Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
        return this.PostToDto(post);
    }

    @Override
    public PostResponse getPostsByCategory(Integer categoryId, Integer pageNum, Integer pageSize) {
        Pageable pg = PageRequest.of(pageNum, pageSize);

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

        Page<Post> pagePosts = this.postRepo.findByCategory(category, pg);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> postDtos = posts.stream().map(post -> this.PostToDto(post)).toList();

        PostResponse res = new PostResponse();
        res.setContent(postDtos);
        res.setPageNum(pagePosts.getNumber());
        res.setPageSize(pagePosts.getSize());
        res.setTotalElements(pagePosts.getTotalElements());
        res.setTotalPages(pagePosts.getTotalPages());
        res.setLastPage(pagePosts.isLast());

        return res;
    }

    @Override
    public List<PostDto> getPostsByUsers(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        List<Post> posts = this.postRepo.findByUser(user);
        return posts.stream().map(post -> this.PostToDto(post)).toList();
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        return posts.stream().map(post -> this.PostToDto(post)).toList();
    }

    @Override
    public PostResponse getAllPostsByPage(Integer pageNum, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = null;
        if (sortDir.equalsIgnoreCase("acs")) {
            sort = Sort.by(sortBy).ascending();
        }
        else {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pg = PageRequest.of(pageNum, pageSize, sort);

        Page<Post> pagePosts = this.postRepo.findAll(pg);
        List<Post> posts = pagePosts.getContent();

        List<PostDto> postDtos = posts.stream().map(post -> this.PostToDto(post)).toList();

        PostResponse res = new PostResponse();
        res.setContent(postDtos);
        res.setPageNum(pagePosts.getNumber());
        res.setPageSize(pagePosts.getSize());
        res.setTotalElements(pagePosts.getTotalElements());
        res.setTotalPages(pagePosts.getTotalPages());
        res.setLastPage(pagePosts.isLast());

        return res;
    }
}
