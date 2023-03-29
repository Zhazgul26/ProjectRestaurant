package company.api;

import company.dto.requests.RestaurantRequest;
import company.dto.responses.SimpleResponse;
import company.dto.responses.restaurant.RestaurantAllResponse;
import company.dto.responses.restaurant.RestaurantResponse;
import company.service.RestaurantService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantApi {

    private final RestaurantService restaurantService;
    public RestaurantApi(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public RestaurantResponse getByIdRestaurant(@PathVariable Long id){
        return restaurantService.getByIdRestaurant(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveRestaurant(@RequestBody RestaurantRequest request){
        return restaurantService.saveRestaurant(request);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<RestaurantResponse> findAllRestaurant(){
        return restaurantService.getAllRestaurant();
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping ("/{id}")
    public SimpleResponse updateRestaurant (@PathVariable Long id,@RequestBody RestaurantRequest request){
        return restaurantService.updateRestaurant(id, request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteRestaurant(@PathVariable Long id){
        return restaurantService.deleteRestaurant(id);
    }


}
