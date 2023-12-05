package blog.backend.post.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.backend.post.entity.Category;
import blog.backend.post.entity.PostEntity;
import blog.backend.post.repository.CategoryRepository;
import blog.backend.post.repository.PostRepository;
import blog.backend.user.User;
import blog.backend.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;

@Service
public class PostServiceImpl implements PostService {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  public ModelMapper mapper;

  @Override
  public PostEntity createPost(PostEntity postDTO, int userId, int categoryId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createPost'");
  }

  @Override
  public PostEntity getPostById(long postId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getPostById'");
  }

  @Override
  public PostEntity updatePost(PostEntity postDTO, long postId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updatePost'");
  }

  @Override
  public void deletePost(long postId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deletePost'");
  }

}
