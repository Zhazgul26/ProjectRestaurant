package company.dto.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record SubCategoryRequest(
        @NotBlank(message = "name cannot be empty!")
        String name,
        @Positive(message = "categoryId cannot be empty!")
        Long categoryId
) {
}
