package company.service.impl;

import company.dto.requests.CategoryRequest;
import company.dto.responses.SimpleResponse;
import company.dto.responses.category.CategoryResponse;

import company.entity.Category;
import company.exeption.BadRequestException;
import company.exeption.NotFoundException;
import company.repository.CategoryRepository;
import company.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategory() {
       return categoryRepository.findAll();
    }

    @Override
    public SimpleResponse saveCategory(CategoryRequest request) {
        Category category = Category.builder()
                .name(request.name())
                .build();
        categoryRepository.save(category);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Category with name: %s successfully saved!",category.getName()))
                .build();
    }

    @Override
    public CategoryResponse findByIdCategory(Long id) {
        return categoryRepository.findCategoryResponseById(id)
                .orElseThrow(()-> new NotFoundException(
                        String.format("Category with ID: %s not found!", id)));
    }

    @Override
    public SimpleResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(
                        String.format("Category with ID: %s not found!", id)));

        category.setName(request.name());
        categoryRepository.save(category);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Category with ID : %s successfully updated !", id))
                .build();
    }

    @Override
    public SimpleResponse deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)){
          throw new BadRequestException((String.format("Category with ID: %s not found!",id)));
        }
        categoryRepository.deleteById(id);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Category with ID: %s successfully deleted",id))
                .build();
    }
}
