package blog.backend.post.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import blog.backend.auth.AuthenticationService;
import blog.backend.payloads.PostDto;
import blog.backend.post.entity.Post;
import blog.backend.post.service.PostService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/api/v1/post")
public class PostController {

  @Autowired
  private PostService postService;

  @Autowired
  private AuthenticationService authService;

  @Value("${project-image}")
  String uploadDir;

  @PostMapping("/user/{userId}/category/{categoryId}/post")
  public ResponseEntity<Object> CreatePost(@RequestBody PostDto postDto,
      @PathVariable("user-id") Integer userId,
      @PathVariable("category-id") int categoryId) {
    PostDto post = postService.createPost(postDto, userId, categoryId);
    return new ResponseEntity<>(post, HttpStatus.CREATED);
  }

}
