package blog.backend.post.entity;

import java.util.*;

import javax.xml.stream.events.Comment;

import blog.backend.user.User;
import jakarta.persistence.*;

import lombok.Setter;

@Entity
@Table(name = "post")
public class PostEntity {

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
  // @Id
  // @GeneratedValue(strategy = GenerationType.AUTO)
  // @Column(nullable = false, name = "id_post")
  // private Long idPost;

  // @Column(nullable = false, name = "title")
  // private String title;

  // @Column(nullable = false, name = "subtitle")
  // private String subtitle;

  // @Column(nullable = false, name = "image_one")
  // private String image_one;

  // @Column(nullable = false, name = "image_two")
  // private String image_two;

  // @Column(nullable = false, name = "image_three")
  // private String image_three;

  // @DateTimeFormat(pattern = "dd-MM-yyyy")
  // @Column(nullable = false, name = "date")
  // private LocalDate date;

  // public PostDTO() {

  // }

  // public PostDTO(Long idPost, String title, String subtitle, String image_one,
  // String image_two, String image_three,
  // LocalDate date) {
  // this.idPost = idPost;
  // this.title = title;
  // this.subtitle = subtitle;
  // this.image_one = image_one;
  // this.image_two = image_two;
  // this.image_three = image_three;
  // this.date = date;
  // }

  // public Long getIdPost() {
  // return idPost;
  // }

  // public void setIdPost(Long idPost) {
  // this.idPost = idPost;
  // }

  // public String getTitle() {
  // return title;
  // }

  // public void setTitle(String title) {
  // this.title = title;
  // }

  // public String getSubtitle() {
  // return subtitle;
  // }

  // public void setSubtitle(String subtitle) {
  // this.subtitle = subtitle;
  // }

  // public String getImage_one() {
  // return image_one;
  // }

  // public void setImage_one(String image_one) {
  // this.image_one = image_one;
  // }

  // public String getImage_two() {
  // return image_one;
  // }

  // public void setImage_two(String image_two) {
  // this.image_two = image_two;
  // }

  // public String getImage_three() {
  // return image_three;
  // }

  // public void setImage_three(String image_three) {
  // this.image_three = image_three;
  // }

  // public LocalDate getDate() {
  // return date;
  // }

  // public void setDate(LocalDate date) {
  // this.date = date;
  // }
}
