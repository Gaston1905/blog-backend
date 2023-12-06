package blog.backend.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import blog.backend.post.entity.Category;
import blog.backend.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

  List<Post> findByCategory(Category category);

  Page<Post> findByUserId(int userId, Pageable pageable);

  @Query("select p from Post p where title like %?1% Or content like %?1%")
  List<Post> findBytitleOrContentContains(String keyword);

  Page<Post> findAllByCategoryId(int categoryId, Pageable pageable);
}
