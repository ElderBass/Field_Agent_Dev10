package learn.field_agent.controllers;

import learn.field_agent.domain.Result;
import learn.field_agent.domain.ResultType;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleException(RuntimeException ex) {
        Result result = new Result();
        result.addMessage("Sorry but your request returned no results.", ResultType.NOT_FOUND);
        return ErrorResponse.build(result);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        Result result = new Result();
        result.addMessage("Something went horribly, terribly wrong. That's on us. So sorry.", ResultType.INTERNAL_SERVER);
        return ErrorResponse.build(result);
    }
}
