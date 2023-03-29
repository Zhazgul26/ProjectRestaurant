package company.dto.responses.menuItem;


public record MenuItemAllResponse(
        Long id,
        String categoryName,
        String subCategoryName,
        String name,
        Integer price

) {
}
