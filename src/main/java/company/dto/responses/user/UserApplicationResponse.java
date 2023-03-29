package company.dto.responses.user;

import company.entity.enums.Role;

import java.time.LocalDate;

public record UserApplicationResponse(
        Long id,
        String fullName,
        LocalDate localDate,
        Role role,
        Integer experience,
        String phoneNumber
) {
}
