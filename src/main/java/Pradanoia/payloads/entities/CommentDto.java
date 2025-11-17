package Pradanoia.payloads.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
    private Integer id;

    private String content;
    private Date createdAt;

    private UserDto user;
    private PostDto post;
}
