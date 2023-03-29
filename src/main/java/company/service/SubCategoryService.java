package company.service;

import company.dto.requests.SubCategoryRequest;
import company.dto.responses.SimpleResponse;
import company.dto.responses.subCategory.SubCategoryResponse;
import company.dto.responses.subCategory.SubCategoryResponsesByCategory;

import java.util.List;
import java.util.Map;

public interface SubCategoryService {

    List<SubCategoryResponse> getAllSubCategory();

    SimpleResponse saveSubCategory(SubCategoryRequest request);

    SubCategoryResponse findByIdSubCategory(Long id);

    SimpleResponse updateSubCategory(Long id,SubCategoryRequest request);

    SimpleResponse deleteSubCategory(Long id);

    Map<String,List<SubCategoryResponsesByCategory>> groupingByCategory();
}
