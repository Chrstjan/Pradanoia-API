package Pradanoia.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 120, nullable = false)
    private String title;

    @Column(name = "slug")
    private String slug;

    @Column(name = "content", length = 10000, nullable = false)
    private String content;

    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "id")
    private Category category;

    @ManyToOne
    private UserInfo author;
}
