package company.dto.responses.cheque;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record ChequeRestaurantResponse(
        LocalDate date,
        Integer numberOfCheque,
        Integer totalAmount
) {
}
