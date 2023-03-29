package company.dto.responses.user;

import lombok.*;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponse implements AbstractApplicationClass{
    private HttpStatus status;
    private  String applicationStatus;
    private UserResponse user;


    public ApplicationResponse(HttpStatus ok, String format) {

    }
}
