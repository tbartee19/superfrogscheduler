package edu.tcu.cs.superfrogscheduler.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String objectName, Integer id) {
        super("Could not find " + objectName + " with Id " + id + " :(");
    }
    @ControllerAdvice
    public static class GlobalExceptionHandler {

        @ExceptionHandler(ObjectNotFoundException.class)
        public ResponseEntity<?> handleObjectNotFoundException(ObjectNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
