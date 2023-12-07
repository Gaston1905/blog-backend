package blog.backend.post.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import blog.backend.payloads.CategoryDto;
import blog.backend.post.service.CategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@ComponentScan(basePackages = "blog.backend.post.controller")
@RequestMapping("/api/v1/categories")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @PostMapping("/create-category")
  public ResponseEntity<Object> createCategory(@Validated @RequestBody CategoryDto categoryDto) {
    categoryDto = categoryService.createCategory(categoryDto);
    return new ResponseEntity<Object>(categoryDto, HttpStatus.CREATED);
  }

  @GetMapping("/{category-name}")
  public ResponseEntity<Object> getCategoryByTitle(@PathVariable("category-name") String categoryName) {
    CategoryDto categoryDto = new CategoryDto();

    categoryDto = categoryService.getCategoryByCategoryName(categoryName);
    return new ResponseEntity<Object>(categoryDto, HttpStatus.OK);
  }

  @PutMapping("/update-category")
  public ResponseEntity<Object> updateCategoryByTitle(@Validated @RequestBody CategoryDto categoryDto) {
    int id = categoryService.getCategoryIdByCategoryName(categoryDto.getCategoryTitle());
    categoryDto = categoryService.updateCategory(id, categoryDto);
    return new ResponseEntity<Object>(categoryDto, HttpStatus.OK);
  }

  @DeleteMapping("/delete-category")
  public ResponseEntity<Object> deleteCategoryByTitle(@Validated @RequestBody CategoryDto categoryDto) {
    int id = categoryService.getCategoryIdByCategoryName(categoryDto.getCategoryTitle());
    categoryService.deleteCategory(id);
    return new ResponseEntity<Object>("Category: " + categoryDto.getCategoryTitle() + " eliminado satisfactoriamente.",
        HttpStatus.OK);
  }

  @GetMapping("/all-categories")
  public ResponseEntity<Object> getAllCategories() {
    List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
    return new ResponseEntity<Object>(categoryDtoList, HttpStatus.OK);
  }

}
