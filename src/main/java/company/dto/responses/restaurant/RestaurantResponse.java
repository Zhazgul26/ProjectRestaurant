package company.dto.responses.restaurant;

import lombok.Builder;

@Builder
public record RestaurantResponse(
        Long id,
        String name,
        String location,
        String resType,
        int numberOfEmployees,
        Integer service
) {
}
