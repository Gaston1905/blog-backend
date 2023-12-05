package blog.backend.post.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import blog.backend.post.entity.PostEntity;
import blog.backend.post.service.PostService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/post")
public class PostController {

  @Autowired
  private PostService postService;

  @PreAuthorize("permitAll()")
  @GetMapping("/getAll")
  public List<PostEntity> getAllPost() {
    return ((PostController) postService).getAllPost();
  }

  @PreAuthorize("IsAuthenticated()")
  @PostMapping("/create/{userId}/{categoryId}")
  public PostEntity createPost(@RequestBody PostEntity postDTO, @PathVariable("userId") int userId,
      @PathVariable("categoryId") int categoryId) {

    return postService.createPost(postDTO, userId, categoryId);
  }

  @PreAuthorize("IsAuthenticated()")
  @GetMapping("get/{postId}")
  public PostEntity getPostById(@PathVariable("postId") long postId) {
    return postService.getPostById(postId);
  }

  @PreAuthorize("IsAuthenticated()")
  @PutMapping("/update/{postId}")
  public PostEntity updatePost(@RequestBody PostEntity postDTO, @PathVariable("postId") long postId) {
    return postService.updatePost(postDTO, postId);
  }

  @PreAuthorize("IsAuthenticated()")
  @DeleteMapping("/delete/{postId}")
  public void deletePost(@PathVariable("postId") long postId) {
    postService.deletePost(postId);
  }
}
