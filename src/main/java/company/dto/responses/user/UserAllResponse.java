package company.dto.responses.user;

import company.entity.enums.Role;
import lombok.Builder;


@Builder
public record UserAllResponse(
        Long id,
        String fullName,
        String email,
        Role role
){
}
