package com.greenfox.poker.aspect;


import com.greenfox.poker.model.StatusError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    return handleExceptionInternal(ex, new StatusError("fail",
        "No authentication token is provided, please refer to the API specification"), headers, status, request);
  }
}
