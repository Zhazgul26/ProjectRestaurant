package company.api;

import company.dto.requests.MenuItemRequest;
import company.dto.responses.SimpleResponse;
import company.dto.responses.menuItem.MenuItemResponse;
import company.dto.responses.menuItem.MenuItemResponseSearch;
import company.entity.MenuItem;
import company.service.MenuItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menuItem")
public class MenuItemApi {
    private final MenuItemService menuItemService;

    public MenuItemApi(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }
    @PreAuthorize("permitAll()")
    @GetMapping
    List<MenuItem> getAllMenu(){
        return menuItemService.getAllMenu();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping
   public SimpleResponse saveMenu(@RequestBody MenuItemRequest request){
        return menuItemService.saveMenu(request);

   }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @GetMapping("/{id}")
    public MenuItemResponse findByIdMenu(@PathVariable Long id){
        return menuItemService.findByIdMenu(id);

    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{id}")
  public SimpleResponse updateMenu(@PathVariable Long id, @RequestBody MenuItemRequest request){
        return menuItemService.updateMenu(id, request);

  }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("{id}")
  public SimpleResponse deleteMenu(@PathVariable Long id){
        return menuItemService.deleteMenu(id);


}
@GetMapping("/sort")
    public List<MenuItem> search(@RequestParam String sort){
        return menuItemService.sortAscAndDesc(sort);
}
@GetMapping("/search")
    public List<MenuItemResponseSearch> searches(@RequestParam String keyWord){
        return menuItemService.search(keyWord);
}
@GetMapping("/isVegetarian")
    List<MenuItem> inIsVegetarian(@RequestParam Boolean isFalse){
        return menuItemService.isVegetarian(isFalse);
}
 }

