package company.api;

import company.dto.requests.CategoryRequest;
import company.dto.responses.SimpleResponse;
import company.dto.responses.category.CategoryResponse;
import company.entity.Category;
import company.service.CategoryService;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryApi {
    private final  CategoryService categoryService ;

    public CategoryApi(CategoryService categoryService) {
        this.categoryService = categoryService;

    }


    @PreAuthorize("permitAll()")
    @GetMapping
   public List<Category> getAllCategory(){
        return categoryService.getAllCategory();
   }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
   public SimpleResponse saveCategory(@RequestBody CategoryRequest request){
        return categoryService.saveCategory(request);
   }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
   public CategoryResponse findByIdCategory(@PathVariable Long id){
        return categoryService.findByIdCategory(id);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
   public SimpleResponse updateCategory(@PathVariable Long id, @RequestBody CategoryRequest request){
        return categoryService.updateCategory(id, request);

   }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public  SimpleResponse deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }


}
