package Pradanoia.payloads.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private Integer id;

    private String title;
    private String content;
    private Date createdAt;

    private CategoryDto category;
    private UserDto user;
}
