package company.dto.requests;

import jakarta.validation.constraints.NotBlank;


public record RestaurantRequest(
        @NotBlank(message = "name cannot be empty!")
        String name,
        String location,
        String resType,
        Integer service
) {
}
