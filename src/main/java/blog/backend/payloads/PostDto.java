package blog.backend.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

  private int id;
  private String title;

  private String imageName;

  private String description;

  private String content;

  private Date addedDate;

  private CategoryDto category;

  private UserDto user;

  // private Set<CommentDto> comments = new HashSet<>();

  private Long likeCounts;

  private Long dislikeCounts;
}
