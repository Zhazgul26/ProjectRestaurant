package company.dto.responses.restaurant;

import lombok.Builder;

@Builder
public record RestaurantAllResponse(
        Long id,
        String name,
        String location
) {
}
