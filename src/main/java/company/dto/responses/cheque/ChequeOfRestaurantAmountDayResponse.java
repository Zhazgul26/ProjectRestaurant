package company.dto.responses.cheque;

import lombok.Builder;

@Builder
public record ChequeOfRestaurantAmountDayResponse(
        Integer numberOfWaiter,
        Integer numberOfCheque,
        Integer priceAverage

) {
}
