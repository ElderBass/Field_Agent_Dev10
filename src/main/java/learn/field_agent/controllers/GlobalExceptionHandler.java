package learn.field_agent.controllers;

import learn.field_agent.domain.Result;
import learn.field_agent.domain.ResultType;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public <T> ResponseEntity<ErrorResponse> handleException(DataIntegrityViolationException ex) {
        Result result = new Result();
        result.addMessage("Something went wrong accessing our database. Apologies.", ResultType.INTERNAL_SERVER);
        return ErrorResponse.build(result);
    }

    @ExceptionHandler(DataAccessException.class)
    public <T> ResponseEntity<ErrorResponse> handleException(DataAccessException ex) {
        Result result = new Result();
        result.addMessage("Something went wrong accessing our database. Apologies.", ResultType.INTERNAL_SERVER);
        return ErrorResponse.build(result);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException ex) {
        Result result = new Result();
        result.addMessage("Invalid data passed into program. Please check inputs and try again.", ResultType.INVALID);
        return ErrorResponse.build(result);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) throws Exception {
        if (ex instanceof HttpMessageNotReadableException || ex instanceof HttpMediaTypeNotSupportedException) {
            throw ex; // return
        }

        Result result = new Result();
        result.addMessage("Something went horribly, terribly wrong. That's on us. So sorry.", ResultType.INTERNAL_SERVER);
        return ErrorResponse.build(result);
    }
}
