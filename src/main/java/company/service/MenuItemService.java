package company.service;

import company.dto.requests.MenuItemRequest;
import company.dto.responses.SimpleResponse;
import company.dto.responses.menuItem.MenuItemResponse;
import company.dto.responses.menuItem.MenuItemResponseSearch;
import company.entity.MenuItem;

import java.util.List;

public interface MenuItemService {

    SimpleResponse saveMenu(MenuItemRequest request);

    List<MenuItem> getAllMenu();

    MenuItemResponse findByIdMenu(Long id);

    SimpleResponse updateMenu(Long id,MenuItemRequest request);
    SimpleResponse deleteMenu(Long id);
    List<MenuItemResponseSearch> search(String keyWord);
     List<MenuItem> sortAscAndDesc(String sort);
     List<MenuItem> isVegetarian(Boolean isFalse);

}
