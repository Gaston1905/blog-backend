package blog.backend.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import blog.backend.post.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

  Category findByCategoryTitle(String categoryName);

}
