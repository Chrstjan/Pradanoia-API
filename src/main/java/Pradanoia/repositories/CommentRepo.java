package Pradanoia.repositories;

import Pradanoia.entities.Comment;
import Pradanoia.entities.Post;
import Pradanoia.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    List<Comment> findByUser(User user);

    Page<Comment> findByPost(Post post, Pageable pageable);
}
