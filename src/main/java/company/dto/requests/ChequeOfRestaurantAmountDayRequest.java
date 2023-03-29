package company.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ChequeOfRestaurantAmountDayRequest(

        @NotBlank(message = "name cannot be empty!")
        String restaurantName,
        LocalDate date) {

}
