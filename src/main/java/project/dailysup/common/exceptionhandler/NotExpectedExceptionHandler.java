package project.dailysup.common.exceptionhandler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import project.dailysup.common.exception.ExceptionResponse;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class NotExpectedExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleException(Exception e, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(LocalDateTime.now(), "Server Error occurred", request.getDescription(false));
        log.error("Unhandled Error occur, message: {} ",e.getMessage());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
