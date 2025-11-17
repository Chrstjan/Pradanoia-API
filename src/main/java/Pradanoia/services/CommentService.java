package Pradanoia.services;

import Pradanoia.payloads.entities.CommentDto;
import Pradanoia.payloads.entities.PostResponse;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId);

    CommentDto updateComment(CommentDto commentDto, Integer id);

    void deleteComment(Integer id);

    CommentDto getCommentById(Integer id);

    List<CommentDto> getCommentsByUsers(Integer userId);

    PostResponse getCommentsByPost(Integer postId, Integer pageNum, Integer pageSize);
}
