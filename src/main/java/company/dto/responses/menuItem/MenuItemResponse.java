package company.dto.responses.menuItem;

import lombok.Builder;

@Builder
public record MenuItemResponse(
        String name,
        String image,
        Integer price,
        String description,
        Boolean isVegetarian
) {
}
