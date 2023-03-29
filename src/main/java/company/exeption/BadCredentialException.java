package company.exeption;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadCredentialException extends RuntimeException{
    public BadCredentialException() {
    }

    public BadCredentialException(String message) {
        super(message);
    }
}
