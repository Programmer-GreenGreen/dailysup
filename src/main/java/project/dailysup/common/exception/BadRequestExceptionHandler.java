package project.dailysup.common.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


import java.time.LocalDateTime;
import java.util.Optional;

@RestControllerAdvice
public class BadRequestExceptionHandler {

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException e, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(LocalDateTime.now(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e, WebRequest request){
        String errorMessage = Optional.ofNullable(e.getBindingResult().getFieldError().getDefaultMessage())
                .orElseGet(()->"입력값에 문제가 있습니다.");
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(LocalDateTime.now(),errorMessage, request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }



}
