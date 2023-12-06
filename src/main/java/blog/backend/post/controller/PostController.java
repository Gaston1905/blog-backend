package blog.backend.post.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// import blog.backend.auth.AuthenticationService;
import blog.backend.payloads.PostDto;
import blog.backend.payloads.PostResponse;
import blog.backend.post.service.PostService;
import blog.backend.utils.Constants;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/api/v1/post")
public class PostController {

  @Autowired
  private PostService postService;

  // @Autowired
  // private AuthenticationService authService;

  @Value("${project-image}")
  String uploadDir;

  @PostMapping("/user/{userId}/category/{categoryId}/post")
  public ResponseEntity<Object> CreatePost(@RequestBody PostDto postDto,
      @PathVariable("user-id") Integer userId,
      @PathVariable("category-id") int categoryId) {
    PostDto post = postService.createPost(postDto, userId, categoryId);
    return new ResponseEntity<>(post, HttpStatus.CREATED);
  }

  @GetMapping("/user/{user-id}/posts")
  public ResponseEntity<Object> getPostsByUser(@PathVariable("user-id") Integer userId,
      @RequestParam(value = "pageNo", defaultValue = Constants.PAGE_NUMBER, required = false) Integer pageNumber,
      @RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) Integer pageSize,
      @RequestParam(value = "sortBy", defaultValue = Constants.SORT_BY, required = false) String sortBy,
      @RequestParam(value = "sortDir", defaultValue = Constants.SORT_ORDER, required = false) String sortDir) {
    PostResponse postResponse = postService.getPostByUser(userId, pageNumber, pageSize, sortBy, sortDir);
    return new ResponseEntity<>(postResponse, HttpStatus.OK);
  }

  @GetMapping("/category/{category-id}/posts")
  public ResponseEntity<Object> getPostByCategory(@PathVariable("category-id") Integer categoryId) {
    List<PostDto> postResponse = postService.getPostByCategory(categoryId);
    return new ResponseEntity<>(postResponse, HttpStatus.OK);
  }

}
