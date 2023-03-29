package company.service;

import company.dto.requests.CategoryRequest;
import company.dto.responses.SimpleResponse;
import company.dto.responses.category.CategoryResponse;
import company.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    SimpleResponse saveCategory(CategoryRequest request);

    CategoryResponse findByIdCategory(Long id);

    SimpleResponse updateCategory(Long id,CategoryRequest request);

    SimpleResponse deleteCategory(Long id);

}
