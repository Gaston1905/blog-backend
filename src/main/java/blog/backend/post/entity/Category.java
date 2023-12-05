package blog.backend.post.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private int id;

  @Column(name = "title")
  private String categoryTitle;

  @Column(name = "description")
  private String categoryDescription;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<PostEntity> posts = new ArrayList<>();

}
