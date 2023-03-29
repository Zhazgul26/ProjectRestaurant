package company.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Builder
public record CategoryRequest(
        @NotBlank(message = "name cannot be empty!")
        String name
) {
}
