package company.repository;


import company.dto.responses.subCategory.SubCategoryResponse;
import company.dto.responses.subCategory.SubCategoryResponsesByCategory;
import company.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    boolean existsByName(String name);
    @Query("select new company.dto.responses.subCategory.SubCategoryResponse(s.name)from SubCategory s")
    List<SubCategoryResponse> findAllSubCategory();


    @Query("select new company.dto.responses.subCategory.SubCategoryResponse(c.name) from SubCategory  c")
    Optional<SubCategoryResponse> findCategoryResponseById(Long id);


    @Query("select new company.dto.responses.subCategory.SubCategoryResponsesByCategory(c.name,s.name) from Category c join c.subCategories s")
    List<SubCategoryResponsesByCategory> findAllGrouping();

}
