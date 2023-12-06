package blog.backend.post.entity;

import java.util.*;

import javax.xml.stream.events.Comment;

import blog.backend.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "post")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_id")
  private int id;

  @Column(name = "post_title")
  private String title;

  private String imageName;

  private String description;

  @Column(columnDefinition = "longtext")
  private String content;

  private Date addedDate;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<Comment> comments = new HashSet<>();

  private Long likeCounts = 0L;

  private Long dislikeCounts = 0L;

}
