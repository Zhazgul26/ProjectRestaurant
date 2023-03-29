package company.dto.requests;

import company.entity.enums.Role;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserRequest(

        @NotBlank(message = "First-Name cannot be empty!")
        String firstName,
        @NotBlank(message = "Last-Name cannot be empty!")
        String lastName,
        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,
        @NotBlank(message = "email cannot be empty!")
        @Email(message = "Invalid email!")
        String email,
        @Size(min = 5, max = 100, message = "Password must be at least 4 characters!")
        @NotBlank(message = "password cannot be empty!")
        String password,

        @NotBlank(message = "phoneNumber cannot be empty!")
        String phoneNumber,
        @NotNull(message = "role cannot be empty!")
        Role role,
        @Positive(message = "experience cannot be empty!")
        Integer experience,
        @Positive(message = "restaurantId cannot be empty!")
        Long restaurantId
) {
}
