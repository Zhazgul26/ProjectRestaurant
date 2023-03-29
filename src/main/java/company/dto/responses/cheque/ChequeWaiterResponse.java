package company.dto.responses.cheque;

import lombok.Builder;

import java.time.LocalDate;


@Builder
public record ChequeWaiterResponse(
        String waiterFullName,
        LocalDate date,
        Integer numberOfCheques,
        Integer averagePrice,
        Integer service,
        Integer total
) {
}
