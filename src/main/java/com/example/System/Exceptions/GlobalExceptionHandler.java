package com.example.System.Exceptions;

import io.jsonwebtoken.JwtException;
import org.apache.coyote.BadRequestException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiException> handleUsernameNotFoundException(UsernameNotFoundException e) {
        ApiException apiException = new ApiException("Username not found: "+e.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiException> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        ApiException apiException = new ApiException("Access denied", HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(apiException, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiException> handleBadCredentialsException(BadCredentialsException e) {
        ApiException apiException = new ApiException("Bad credentials", HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiException> handleBadRequestException(BadRequestException e) {
        ApiException apiException = new ApiException("Bad request", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiException> handleJwtException(JwtException e) {
        ApiException apiException = new ApiException("Jwt token invalid or expired", HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiException> handleAuthenticationException(AuthenticationException e) {
        ApiException apiException = new ApiException("Authentication error", HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ApiException> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException e) {
        ApiException apiException = new ApiException("Authentication credentials not found", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

}
