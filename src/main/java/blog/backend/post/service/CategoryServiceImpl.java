package blog.backend.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import blog.backend.exceptions.CategoryAlreadyExists;
import blog.backend.exceptions.CategoryNotFound;
import blog.backend.exceptions.UserNotFound;
import blog.backend.payloads.CategoryDto;
import blog.backend.post.entity.Category;
import blog.backend.post.repository.CategoryRepository;

public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private Map<String, CategoryDto> categoryMap;

  @Override
  public CategoryDto createCategory(CategoryDto categoryDto) {
    try {
      CategoryDto savedCategory = getCategoryByCategoryName(categoryDto.getCategoryTitle());
      if (savedCategory != null) {
        throw new CategoryAlreadyExists("Categoría ya existente.");
      } else {
        throw new CategoryNotFound("Categoría no encontrada!!");
      }
    } catch (CategoryNotFound e) {
      Category category = dtoToCategory(categoryDto);
      categoryRepository.save(category);
      return categoryToDto(category);
    }
  }

  @Override
  public CategoryDto updateCategory(int id, CategoryDto categoryDto) {
    Category category = categoryRepository.findById(id).get();
    // category.setCategoryDescription(categoryDto.getCategoryDescription());
    categoryRepository.save(category);
    return categoryToDto(category);
  }

  @Override
  public CategoryDto getCategoryByCategoryName(String categoryName) {

    Category category = categoryRepository.findByCategoryTitle(categoryName);
    if (category != null) {
      return categoryToDto(category);
    } else {
      throw new CategoryNotFound("Categoría no encontrada!!");
    }
  }

  @Override
  public List<CategoryDto> getAllCategories() {
    List<CategoryDto> categories = new ArrayList<>();
    if (categoryMap.size() > 0) {
      for (Map.Entry<String, CategoryDto> category : categoryMap.entrySet()) {
        categories.add(category.getValue());
      }
      return categories;
    } else {
      categories = categoryRepository.findAll().stream().map(this::categoryToDto).collect(Collectors.toList());
      return categories;
    }
  }

  @Override
  public void deleteCategory(int id) {
    categoryRepository.deleteById(id);
  }

  @Override
  public int getCategoryIdByCategoryName(String categoryName) {
    Category category = categoryRepository.findByCategoryTitle(categoryName);
    if (category != null) {
      return 1;
    } else {
      throw new UserNotFound("Usuario no encontrado!!");
    }
  }

  private Category dtoToCategory(CategoryDto categoryDto) {
    return modelMapper.map(categoryDto, Category.class);
  }

  private CategoryDto categoryToDto(Category category) {
    return modelMapper.map(category, CategoryDto.class);
  }
}
