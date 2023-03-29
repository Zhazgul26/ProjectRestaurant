package company.service.impl;

import company.dto.requests.SubCategoryRequest;
import company.dto.responses.SimpleResponse;
import company.dto.responses.subCategory.SubCategoryResponse;
import company.dto.responses.subCategory.SubCategoryResponsesByCategory;
import company.entity.Category;
import company.entity.SubCategory;
import company.exeption.ConflictException;
import company.exeption.NotFoundException;
import company.repository.CategoryRepository;
import company.repository.SubCategoryRepository;
import company.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.categoryRepository = categoryRepository;

    }

    @Override
    public List<SubCategoryResponse> getAllSubCategory() {
        return subCategoryRepository.findAllSubCategory();
    }

    @Override
    public SimpleResponse saveSubCategory(SubCategoryRequest request) {
        if (subCategoryRepository.existsByName(request.name())) {
            throw new ConflictException((String.format("SubCategory with name: %s already exists!", request.name())));
        }

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Category with ID: %s not found", request.categoryId())));

        SubCategory subCategory = SubCategory.builder()
                .name(request.name())
                .category(category)
                .build();
        subCategoryRepository.save(subCategory);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("SubCategory with name %s successfully saved", subCategory.getName()))
                .build();
    }

    @Override
    public SubCategoryResponse findByIdSubCategory(Long id) {
        return subCategoryRepository.findCategoryResponseById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("SubCategory with ID: %s not found!", id)));
    }

    @Override
    public SimpleResponse updateSubCategory(Long id, SubCategoryRequest request) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("SubCategory with ID: %s not found!", id)));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NotFoundException(String.format("SubCategory with ID: %s not found", request.categoryId())));
        subCategory.setName(request.name());
        subCategory.setCategory(category);
        subCategoryRepository.save(subCategory);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("SubCategory with ID : %s successfully updated !", id))
                .build();
    }

    @Override
    public SimpleResponse deleteSubCategory(Long id) {
        if (!subCategoryRepository.existsById(id)) {
            throw new NotFoundException((String.format("SubCategory with ID: %s not found!", id)));
        }
        subCategoryRepository.deleteById(id);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("SubCategory with ID: %s successfully deleted", id))
                .build();
    }

    @Override
    public Map<String, List<SubCategoryResponsesByCategory>> groupingByCategory() {
      return  subCategoryRepository.findAllGrouping().stream().
              collect(Collectors.groupingBy(SubCategoryResponsesByCategory::categoryName));
    }
}
