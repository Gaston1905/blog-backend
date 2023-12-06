package blog.backend.post.service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import blog.backend.payloads.PostDto;
import blog.backend.payloads.PostResponse;
import io.jsonwebtoken.io.IOException;

public interface PostService {

  PostDto createPost(PostDto postDto, int userId, int categoryId);

  PostDto updatePost(PostDto postDto, int id);

  void deletePost(PostDto postDto) throws IOException, java.io.IOException;

  PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

  PostDto getPostById(int postId);

  List<PostDto> getPostByCategory(int categoryId);

  PostResponse getPostByUser(int userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

  List<PostDto> searchPost(String keyword);

  PostDto uploadImage(String filename, MultipartFile file, int id) throws IOException, java.io.IOException;

  InputStream getImage(Integer id) throws FileNotFoundException;

  void storeLikeCounts(Integer postId, Long likeCounts, Long dislikeCounts);
}
