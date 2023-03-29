package company.service.impl;

import company.dto.requests.RestaurantRequest;
import company.dto.responses.SimpleResponse;
import company.dto.responses.restaurant.RestaurantResponse;
import company.entity.Restaurant;
import company.exeption.BadRequestException;
import company.exeption.NotFoundException;
import company.repository.RestaurantRepository;
import company.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
@Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest request) {
            List<Restaurant> all = restaurantRepository.findAll();
            if (all.size() > 0){
                throw new BadRequestException("Only one restaurant will be!!");
            } else if (0 < request.service() && 50 < request.service()) {
                throw new BadRequestException();
            }else {
                Restaurant restaurant = new Restaurant();
                restaurant.setName(request.name());
                restaurant.setLocation(request.location());
                restaurant.setResType(request.resType());
                restaurant.setService(request.service());
                restaurantRepository.save(restaurant);
            }
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message(String.format("Restaurant with name: %s is successfully saved",request.name()))
                    .build();
        }


    @Override
    public List<RestaurantResponse> getAllRestaurant() {
       return restaurantRepository.getAllRestaurant();
    }

    @Override
    public RestaurantResponse getByIdRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Restaurant with ID: %s not found!", id)));
        restaurant.setNumberOfEmployees(restaurant.getUsers().size());
        restaurantRepository.save(restaurant);

        return restaurantRepository.findRestaurantResponseById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Restaurant with ID: %s not found!", id)));
    }

    @Override
    public SimpleResponse deleteRestaurant(Long id) {
        if (!restaurantRepository.existsById(id)) {
          throw new NotFoundException((String.format("Restaurant with ID: %s not found!",id)));
        }
        restaurantRepository.deleteById(id);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Restaurant with ID: %s successfully deleted",id))
                .build();
    }


    @Override
    public SimpleResponse updateRestaurant(Long id, RestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Restaurant with ID: %s not found! ", id)));
        restaurant.setName(request.name());
        restaurant.setLocation(request.location());
        restaurant.setResType(request.resType());
        restaurant.setService(request.service());

        restaurantRepository.save(restaurant);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Restaurant with ID: %s successfully updated",id))
                .build();
        }
}

