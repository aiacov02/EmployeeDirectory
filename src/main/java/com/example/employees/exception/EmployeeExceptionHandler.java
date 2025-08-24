package com.example.employees.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeExceptionHandler {

   @ExceptionHandler
    public ResponseEntity<EmployeeErrorResponse> handleEmployeeNotFoundException(EmployeeNotFoundException employeeNotFoundException) {
        return new ResponseEntity<>(new EmployeeErrorResponse(HttpStatus.NOT_FOUND.value(), employeeNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler
    public ResponseEntity<EmployeeErrorResponse> handleEmployeeException(Exception exception) {
       return new ResponseEntity<>(new EmployeeErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage()), HttpStatus.BAD_REQUEST);
   }

}
