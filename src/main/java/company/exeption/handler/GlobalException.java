package company.exeption.handler;

import company.exeption.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handlerNotFoundException(NotFoundException notFoundException){
        return  new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                notFoundException.getClass().getSimpleName(),
                notFoundException.getMessage()
        );
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
     public ExceptionResponse handlerBadRequestException(BadRequestException badRequestException){
        return  new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                badRequestException.getClass().getSimpleName(),
                badRequestException.getMessage()
        );
     }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse notValidException(MethodArgumentNotValidException e){
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                e.getClass().getSimpleName(),
                Objects.requireNonNull(e.getFieldError()).getDefaultMessage()
        );
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handlerNotFoundException(ForbiddenException forbiddenException){
        return  new ExceptionResponse(
                HttpStatus.FORBIDDEN,
                forbiddenException.getClass().getSimpleName(),
                forbiddenException.getMessage()
        );
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handlerBadCredentialException(BadCredentialException badCredentialException){
        return  new ExceptionResponse(
                HttpStatus.FORBIDDEN,
                badCredentialException.getClass().getSimpleName(),
                badCredentialException.getMessage()
        );
    }
}
