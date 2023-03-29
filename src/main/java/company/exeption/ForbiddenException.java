package company.exeption;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForbiddenException extends RuntimeException{
    public ForbiddenException() {
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
