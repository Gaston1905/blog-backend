package blog.backend.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import blog.backend.post.entity.Category;
import blog.backend.post.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {

  List<PostEntity> findByCategory(Category category);

  Page<PostEntity> findByUserId(int userId, Pageable pageable);

  @Query("select p from Post p where title like %?1% Or content like %?1%")
  List<PostEntity> findBytitleOrContentContains(String keyword);

  Page<PostEntity> findAllByCategoryId(int categoryId, Pageable pageable);
}
