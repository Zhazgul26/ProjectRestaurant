package company.dto.requests;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.time.LocalDate;


@Builder
public record StopListRequest(
        @Positive(message = "reason cannot be empty!")
        String reason,
        @Past(message = "Date of birth must be in the past")
        LocalDate date,
        @Positive(message = "menuId cannot be empty!")
        Long menuItemId
) {
}
