package company.service.impl;

import company.dto.requests.MenuItemRequest;
import company.dto.responses.SimpleResponse;
import company.dto.responses.menuItem.MenuItemResponse;
import company.dto.responses.menuItem.MenuItemResponseSearch;
import company.entity.MenuItem;
import company.entity.Restaurant;
import company.entity.StopList;
import company.entity.SubCategory;
import company.exeption.BadRequestException;
import company.exeption.ConflictException;
import company.exeption.NotFoundException;
import company.repository.MenuItemRepository;
import company.repository.RestaurantRepository;
import company.repository.StopListRepository;
import company.repository.SubCategoryRepository;
import company.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final StopListRepository stopListRepository;

    @Autowired
    public MenuItemServiceImpl(MenuItemRepository menuItemRepository,
                               RestaurantRepository restaurantRepository, SubCategoryRepository subCategoryRepository,
                               StopListRepository stopListRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.stopListRepository = stopListRepository;
    }


    @Override
    public SimpleResponse saveMenu(MenuItemRequest request) {
        if (menuItemRepository.existsByName(request.name())) {
         throw  new ConflictException((String.format("MenuItem with name: %s already exists!", request.name())));
        }
        if (request.price() < 0) {
           throw new BadRequestException("Price not cannot be negative!");
        }

        Restaurant restaurant = restaurantRepository.findById(request.restaurantId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Restaurant with ID: %s not found", request.restaurantId())));

        SubCategory subCategory = subCategoryRepository.findById(request.subCategoryId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("SubCategory with ID: %s not found", request.subCategoryId())));

        MenuItem menuItem = MenuItem.builder()
                .name(request.name())
                .image(request.image())
                .price(request.price())
                .description(request.description())
                .isVegetarian(request.isVegetarian())
                .restaurant(restaurant)
                .subCategory(subCategory)
                .build();
        menuItemRepository.save(menuItem);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("MenuItem with name: %s successfully saved", menuItem.getName()))
                .build();
    }

    @Override
    public List<MenuItem> getAllMenu() {
        List<MenuItem> menuItems = menuItemRepository.findAll();
        List<StopList> stopLists = stopListRepository.findByDate(LocalDate.now());
        for (StopList stoplist:stopLists) {
            menuItems.remove(stoplist.getMenuItem());
        }
        return menuItems;
    }

    @Override
    public MenuItemResponse findByIdMenu(Long id) {
        return menuItemRepository.findMenuItemById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("SubCategory with ID: %s not found!", id)));
    }

    @Override
    public SimpleResponse updateMenu(Long id, MenuItemRequest request) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("MenuItem with ID: %s not found!", id)));

        Restaurant restaurant = restaurantRepository.findById(request.restaurantId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Restaurant with  ID: %s not found!", request.restaurantId())));

        menuItem.setName(request.name());
        menuItem.setImage(request.image());
        menuItem.setPrice(request.price());
        menuItem.setDescription(request.description());
        menuItem.setIsVegetarian(request.isVegetarian());

        menuItem.setRestaurant(restaurant);

        menuItemRepository.save(menuItem);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("MenuItem with ID: %s successfully updated !", id))
                .build();

    }

    @Override
    public SimpleResponse deleteMenu(Long id) {
        if (!menuItemRepository.existsById(id)) {
            return SimpleResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message(String.format("MenuItem with ID: %s not found!", id))
                    .build();
        }
        menuItemRepository.deleteById(id);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("MenuItem with ID: %s successfully deleted", id))
                .build();
    }

    @Override
    public List<MenuItemResponseSearch> search(String keyWord) {
        return menuItemRepository.search(keyWord);
    }

    @Override
    public List<MenuItem> sortAscAndDesc(String sort) {
        if (sort.equalsIgnoreCase("asc")){
           return menuItemRepository.getAllByOrderByPriceAsc();
        }else if (sort.equalsIgnoreCase("desc")){
          return   menuItemRepository.getAllByOrderByPriceDesc();

        }else
        return new ArrayList<>();
    }

    @Override
    public List<MenuItem> isVegetarian(Boolean isFalse) {
        return menuItemRepository.findMenuItemByIsVegetarian(isFalse);
    }


}
