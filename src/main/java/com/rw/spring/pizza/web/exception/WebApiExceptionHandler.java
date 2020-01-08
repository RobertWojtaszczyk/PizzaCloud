package com.rw.spring.pizza.web.exception;

import com.rw.spring.pizza.web.resource.error.ApiError;
import com.rw.spring.pizza.web.resource.error.ApiResourcePropertyError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
public class WebApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> globalErrors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(error -> error.getObjectName() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        List<ApiResourcePropertyError> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(this::toResourcePropertyError)
                .collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
                .message("Validation error")
                .status(ex.getParameter().hasParameterAnnotation(RequestBody.class) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.BAD_REQUEST)
                .details(Stream.concat(globalErrors.stream(), fieldErrors.stream()).collect(Collectors.toList()))
                .build();

        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {

        List<ApiResourcePropertyError> errors = ex.getConstraintViolations()
                .stream()
                .map(this::toResourcePropertyError)
                .collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
                .message("Validation error")
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .details(errors)
                .build();

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

    private ApiResourcePropertyError toResourcePropertyError(ConstraintViolation violation) {

        return ApiResourcePropertyError.builder()
                .property(violation.getPropertyPath().toString())
                .message(violation.getMessage())
                .invalidValue(violation.getInvalidValue())
                .build();
    }

    private ApiResourcePropertyError toResourcePropertyError(FieldError fieldError) {

        return ApiResourcePropertyError.builder()
                .property(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .invalidValue(fieldError.getRejectedValue())
                .build();
    }
}
