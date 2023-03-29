package company.dto.requests;


import jakarta.validation.constraints.Positive;

import java.util.List;

public record ChequeRequest(
        Long userId,
        @Positive(message = "name cannot be empty!")
        List<String> menuItemName
) {
}
