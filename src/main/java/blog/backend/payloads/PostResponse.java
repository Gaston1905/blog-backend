package blog.backend.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

  private List<PostDto> contents;

  private int pageNumber;
  private int pageSize;
  private long totalElements;
  private int totalPages;
  private boolean isLast;
}
