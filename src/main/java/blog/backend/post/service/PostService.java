package blog.backend.post.service;

import blog.backend.post.entity.PostEntity;

public interface PostService {

  public PostEntity createPost(PostEntity postDTO, int userId, int categoryId);

  public PostEntity getPostById(long postId);

  public PostEntity updatePost(PostEntity postDTO, long postId);

  public void deletePost(long postId);
}
