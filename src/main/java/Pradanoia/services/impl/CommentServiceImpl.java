package Pradanoia.services.impl;

import Pradanoia.entities.Comment;
import Pradanoia.entities.Post;
import Pradanoia.entities.User;
import Pradanoia.exceptions.ResourceNotFoundException;
import Pradanoia.payloads.entities.CommentDto;
import Pradanoia.payloads.entities.PostResponse;
import Pradanoia.repositories.CommentRepo;
import Pradanoia.repositories.PostRepo;
import Pradanoia.repositories.UserRepo;
import Pradanoia.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;

    private Comment DtoToComment(CommentDto commentDto) {return modelMapper.map(commentDto, Comment.class);}

    private CommentDto CommentToDto(Comment comment) {return modelMapper.map(comment, CommentDto.class);}

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        Comment comment = this.DtoToComment(commentDto);
        comment.setCreatedAt(new Date());
        comment.setUser(user);
        comment.setPost(post);

        Comment savedComment = this.commentRepo.save(comment);
        return this.CommentToDto(savedComment);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer id) {
        Comment comment = this.commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", id));
        comment.setContent(commentDto.getContent());

        Comment updatedComment = commentRepo.save(comment);
        return this.CommentToDto(updatedComment);
    }

    @Override
    public void deleteComment(Integer id) {
        Comment comment = this.commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", id));
        commentRepo.delete(comment);
    }

    @Override
    public CommentDto getCommentById(Integer id) {
        Comment comment = this.commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", id));
        return this.CommentToDto(comment);
    }

    @Override
    public List<CommentDto> getCommentsByUsers(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        List<Comment> comments = this.commentRepo.findByUser(user);
        return comments.stream().map(comment -> this.CommentToDto(comment)).toList();
    }

    @Override
    public PostResponse getCommentsByPost(Integer postId, Integer pageNum, Integer pageSize) {
        Pageable pg = PageRequest.of(pageNum, pageSize);

        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        Page<Comment> pageComments = this.commentRepo.findByPost(post, pg);
        List<Comment> comments = pageComments.getContent();
        List<CommentDto> commentDtos = comments.stream().map(comment -> this.CommentToDto(comment)).toList();

        PostResponse res = new PostResponse();
        res.setContent(commentDtos);
        res.setPageNum(pageComments.getNumber());
        res.setPageSize(pageComments.getSize());
        res.setTotalElements(pageComments.getTotalElements());
        res.setTotalPages(pageComments.getTotalPages());
        res.setLastPage(pageComments.isLast());

        return res;
    }
}
