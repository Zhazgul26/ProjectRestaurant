package company.dto.responses.menuItem;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record MenuItemResponseSearch (
        String categoryName,
        String subcategoryName,
        String menuItemName,
        String image,
        int price

){


}
