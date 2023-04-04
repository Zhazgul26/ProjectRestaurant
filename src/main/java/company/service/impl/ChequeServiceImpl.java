package company.service.impl;

import company.dto.requests.ChequeOneDayWaiterTotalAmountRequest;
import company.dto.requests.ChequeRequest;
import company.dto.responses.SimpleResponse;
import company.dto.responses.cheque.ChequeResponse;
import company.dto.responses.cheque.ChequeWaiterResponse;
import company.dto.responses.menuItem.MenuItemResponse;
import company.entity.Cheque;
import company.entity.MenuItem;
import company.entity.Restaurant;
import company.entity.User;
import company.entity.enums.Role;
import company.exeption.BadRequestException;
import company.exeption.NotFoundException;
import company.repository.*;
import company.service.ChequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final StopListRepository stopListRepository;

    @Autowired
    public ChequeServiceImpl(ChequeRepository chequeRepository, UserRepository userRepository, MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository,
                             StopListRepository stopListRepository) {
        this.chequeRepository = chequeRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;

        this.stopListRepository = stopListRepository;
    }


    @Override
    public List<ChequeResponse> getAllCheque() {
        List<Cheque> chequeList = chequeRepository.findAll();
        List<ChequeResponse> chequeResponseList = new ArrayList<>();
        for (Cheque cheque : chequeList) {
            int service = cheque.getPriceAverage() * cheque.getUser().getRestaurant().getService() / 100;
            int grandTotal = service + cheque.getPriceAverage();

            ChequeResponse build = ChequeResponse.builder()
                    .service(cheque.getUser().getRestaurant().getService())
                    .priceAverage(BigDecimal.valueOf(cheque.getPriceAverage()))
                    .waiterFullName(cheque.getUser().getFirstName().concat(" ").concat(cheque.getUser().getLastName()))
                    .grandTotal(BigDecimal.valueOf(grandTotal))
                    .menuItems(convert(cheque.getMenuItems()))
                    .build();
            chequeResponseList.add(build);
        }
        return chequeResponseList;
    }

    @Override
    public SimpleResponse saveCheque(ChequeRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException(String.format("User with ID: %s not found!", request.userId())));
        int price = 0;
        Cheque cheque = new Cheque();
        List<MenuItem> menuItems = new ArrayList<>();

        for (String menuItemName : request.menuItemName()) {
            MenuItem menuItem = menuItemRepository.findByName(menuItemName)
                    .orElseThrow(() -> new NotFoundException(String.format("MenuItemName with %s fot found", request.menuItemName())));

            if (stopListRepository.count(LocalDate.now(), menuItem.getId()) > 0) {
                throw new BadRequestException("Today there are no such dishes");
            } else {
                price += menuItem.getPrice();
                menuItem.addCheque(cheque);
            }
            if (user.getRole().equals(Role.WAITER)) {
                cheque.setPriceAverage(price);
                cheque.setUser(user);
                user.addCheque(cheque);
                cheque.setMenuItems(menuItems);
                cheque.setCreatedAt(LocalDate.now());
                chequeRepository.save(cheque);
            } else {
                throw new BadRequestException("Given your waiter doesn't working here,You give error admin");
            }
        }
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Cheque with ID: %s successfully saved", cheque.getId()))
                .build();
    }

    @Override
    public ChequeResponse findByIdCheque(Long id) {
        int averagePrice = 0;
        Cheque cheque = chequeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Cheque with ID: %s not found!", id)));
        for (MenuItem menu : cheque.getMenuItems()) {
            averagePrice += menu.getPrice();

        }
        int service = averagePrice * cheque.getUser().getRestaurant().getService() / 100;
        int grandTotal = service + averagePrice;
        return ChequeResponse.builder()
                .service(cheque.getUser().getRestaurant().getService())
                .priceAverage(BigDecimal.valueOf(averagePrice))
                .waiterFullName(cheque.getUser().getFirstName().concat(cheque.getUser().getLastName()))
                .grandTotal(BigDecimal.valueOf(grandTotal))
                .menuItems(convert(cheque.getMenuItems()))
                .build();

    }

    @Override
    public SimpleResponse updateCheque(Long id, ChequeRequest request) {

        Cheque cheque = chequeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Cheque with ID: %s not found!", id)));

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException(String.format("Cheque with ID: %s not found!", request.userId())));

        List<MenuItem> menuItemList = new ArrayList<>();
        for (String oldMenuITemName : request.menuItemName()) {
            MenuItem menuItem = menuItemRepository.findByName(oldMenuITemName).orElseThrow();

            menuItem.addCheque(cheque);
            menuItemList.add(menuItem);
        }
        for (MenuItem oldMenuItem : menuItemList) {
            for (MenuItem men : cheque.getMenuItems()) {
                if (!men.getName().equalsIgnoreCase(oldMenuItem.getName())) {
                    MenuItem menuItem = menuItemRepository.findByName(men.getName()).orElseThrow();
                    menuItem.addCheque(cheque);
                    menuItemList.add(menuItem);
                }

            }

        }
        cheque.setUser(user);
        user.addCheque(cheque);
        cheque.setMenuItems(menuItemList);
        chequeRepository.save(cheque);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Cheque with ID: %s successfully updated !", id))
                .build();
    }

    @Override
    public SimpleResponse deleteCheque(Long id) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow();
        cheque.getMenuItems().forEach(menuItem -> menuItem.setCheques(null));
        chequeRepository.delete(cheque);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("MenuItem with ID: %s successfully deleted", id))
                .build();
    }

    @Override
    public SimpleResponse totalSumCheque(ChequeOneDayWaiterTotalAmountRequest request) {

        Restaurant restaurant = restaurantRepository.findByName(request.restaurantName())
                .orElseThrow(() -> new NotFoundException(String.format("Restaurant with name: %s is not found!",
                        request.restaurantName())));
        int numberOfWaiters = 0;
        int numberOfCheque = 0;
        int totalAmount = 0;

        for (User userWaiter : restaurant.getUsers()) {
            if (userWaiter.getRole().equals(Role.WAITER)) {
                for (Cheque waiterCh : userWaiter.getCheques()) {
                    if (waiterCh.getCreatedAt().isEqual(request.date())) {
                        int restaurantService = waiterCh.getPriceAverage() * restaurant.getService() / 100;
                        totalAmount += restaurantService + waiterCh.getPriceAverage();
                        numberOfCheque++;
                    }

                }
                numberOfWaiters++;
            }
        }
        int priceAverage = totalAmount / numberOfCheque;

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Number of waiters: %d \n Number of cheques:  %d \nPrice average: %d",
                        numberOfWaiters, numberOfCheque, priceAverage))
                .build();

    }


    @Override
    public ChequeWaiterResponse summa(ChequeOneDayWaiterTotalAmountRequest request) {
        User user = userRepository.findById(request.waiterId())
                .orElseThrow(() -> new NotFoundException(String.format("User with ID: %s not found!",
                        request.waiterId())));

        int chequeCount = 0;
        int averagePrice = 0;
        int totalAmount = 0;

        if (user.getRole().equals(Role.WAITER)) {
            for (Cheque cheque : user.getCheques()) {
                if (cheque.getCreatedAt().equals(request.date())) {
                    int service = cheque.getPriceAverage() * user.getRestaurant().getService() / 100;
                    totalAmount += service + cheque.getPriceAverage();
                    chequeCount++;
                    averagePrice += cheque.getPriceAverage();

                }
            }
        }
        return ChequeWaiterResponse.builder()
                .averagePrice(averagePrice)
                .date(request.date())
                .numberOfCheques(chequeCount)
                .service(user.getRestaurant().getService())
                .total(totalAmount)
                .waiterFullName(user.getFirstName() + " " + user.getLastName())
                .build();

    }

    private MenuItemResponse convert(MenuItem menuItem) {
        return MenuItemResponse.builder()
                .name(menuItem.getName())
                .price(menuItem.getPrice())
                .image(menuItem.getImage())
                .description(menuItem.getDescription())
                .isVegetarian(menuItem.getIsVegetarian())
                .build();
    }

    private List<MenuItemResponse> convert(List<MenuItem> menuItems) {
        List<MenuItemResponse> menuItemResponses = new ArrayList<>();
        for (MenuItem menuItem : menuItems) {
            menuItemResponses.add(convert(menuItem));
        }
        return menuItemResponses;
    }
}




