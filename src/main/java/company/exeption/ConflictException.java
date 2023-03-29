package company.exeption;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConflictException extends RuntimeException{
    public ConflictException() {
    }

    public ConflictException(String message) {
        super(message);
    }
}
