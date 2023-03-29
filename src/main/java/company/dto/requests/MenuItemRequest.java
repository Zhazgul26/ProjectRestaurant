package company.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;


public record MenuItemRequest(
        @Positive(message = "name cannot be empty!")
        String name,
        @Positive(message = "name cannot be empty!")
        String image,
        @Min(value = 1,message = "Price cannot be negative!")
        Integer price,
        String description,
        Boolean isVegetarian,
        Long restaurantId,
        Long subCategoryId
) {
}
