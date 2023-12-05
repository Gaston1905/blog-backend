package blog.backend.post.service;

import java.util.List;

import blog.backend.payloads.CategoryDto;

public interface CategoryService {

  CategoryDto createCategory(CategoryDto categoryDto);

  CategoryDto updateCategory(int id, CategoryDto categoryDto);

  CategoryDto getCategoryByCategoryName(String categoryName);

  List<CategoryDto> getAllCategories();

  void deleteCategory(int id);

  int getCategoryIdByCategoryName(String categoryName);

}
