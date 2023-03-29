package company.service;

import company.dto.requests.RestaurantRequest;
import company.dto.responses.SimpleResponse;
import company.dto.responses.restaurant.RestaurantAllResponse;
import company.dto.responses.restaurant.RestaurantResponse;

import java.util.List;


public interface RestaurantService {



    SimpleResponse saveRestaurant(RestaurantRequest request);

    List<RestaurantResponse> getAllRestaurant();
    RestaurantResponse getByIdRestaurant(Long id);

    SimpleResponse deleteRestaurant(Long id);
    SimpleResponse updateRestaurant(Long id,RestaurantRequest request);
}
