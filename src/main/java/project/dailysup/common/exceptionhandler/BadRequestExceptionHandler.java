package project.dailysup.common.exceptionhandler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import project.dailysup.common.exception.BadRequestException;
import project.dailysup.common.exception.ExceptionResponse;


import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class BadRequestExceptionHandler {

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(
                                                        BadRequestException e,
                                                        WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(e, request, false);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(
                                                        MethodArgumentNotValidException e,
                                                        WebRequest request){

        String errorMessage = Optional.ofNullable(e.getBindingResult().getFieldError().getDefaultMessage())
                .orElseGet(()->"입력값에 문제가 있습니다.");
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(errorMessage, request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotAllowedException(
                                                            HttpRequestMethodNotSupportedException e,
                                                            WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(e, request, false);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }



}
