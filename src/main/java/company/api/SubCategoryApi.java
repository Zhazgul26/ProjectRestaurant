package company.api;

import company.dto.requests.SubCategoryRequest;
import company.dto.responses.SimpleResponse;
import company.dto.responses.subCategory.SubCategoryResponse;
import company.dto.responses.subCategory.SubCategoryResponsesByCategory;
import company.service.SubCategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subCategory")
public class SubCategoryApi {
    private final SubCategoryService subCategoryService;

    public SubCategoryApi(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public List<SubCategoryResponse> getAllSubCategory() {
        return subCategoryService.getAllSubCategory();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveSubCategory(@RequestBody SubCategoryRequest request) {
        return subCategoryService.saveSubCategory(request);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public SubCategoryResponse findByIdSubCategory(@PathVariable Long id) {
        return subCategoryService.findByIdSubCategory(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse updateSubCategory(@PathVariable Long id, @RequestBody SubCategoryRequest request) {
        return subCategoryService.updateSubCategory(id, request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteSubCategory(@PathVariable Long id) {
        return subCategoryService.deleteSubCategory(id);

    }

    @PreAuthorize("permitAll()")
    @GetMapping("/grouping")
    public Map<String, List<SubCategoryResponsesByCategory>> grouping() {
        return subCategoryService.groupingByCategory();
    }
}