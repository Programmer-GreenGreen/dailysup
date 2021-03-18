package project.dailysup.common.exceptionhandler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import project.dailysup.common.exception.ExceptionResponse;
import project.dailysup.common.exception.FailedFileSaveException;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class InternalErrorHandler {

    @ExceptionHandler(value = FailedFileSaveException.class)
    public ResponseEntity<?> handleFailedFileSaveException(FailedFileSaveException e, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(e, request,false);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
