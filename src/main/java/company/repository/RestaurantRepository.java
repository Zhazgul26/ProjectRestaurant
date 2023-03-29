package company.repository;


import company.dto.responses.restaurant.RestaurantAllResponse;
import company.dto.responses.restaurant.RestaurantResponse;
import company.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("select new company.dto.responses.restaurant.RestaurantResponse(r.id,r.name,r.location,r.resType,r.numberOfEmployees,r.service) from Restaurant r  ")
    List<RestaurantResponse> getAllRestaurant();


    @Query("select new company.dto.responses.restaurant.RestaurantResponse(r.id,r.name,r.location,r.resType,r.numberOfEmployees,r.service) from Restaurant r  where r.id=:id")
    Optional<RestaurantResponse> findRestaurantResponseById(Long id);


    Optional<Restaurant> findByName(String name);
}