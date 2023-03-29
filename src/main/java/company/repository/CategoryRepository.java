package company.repository;

import company.dto.responses.category.CategoryResponse;
import company.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select new company.dto.responses.category.CategoryResponse(c.name) from Category  c where c.id = :id")
    Optional<CategoryResponse> findCategoryResponseById(Long id);}