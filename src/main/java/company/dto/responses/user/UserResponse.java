package company.dto.responses.user;

import company.entity.enums.Role;

import java.time.LocalDate;

public record UserResponse (
        Long id,
        String fullName,
        LocalDate dateOfBirth,
        String email,
        String phoneNumber,
        Role role,
        Integer experience
) {
}
