package org.soumyadev.crowdfundinnovativeworld.ExceptionHandling;

import io.micrometer.core.instrument.config.validate.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.soumyadev.crowdfundinnovativeworld.Constants.ErrorMessage;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@Slf4j
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        CustomExceptionResponse exceptionResponse = new CustomExceptionResponse(new Date(), ex.getMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(final ValidationException exception) {
        CustomExceptionResponse exceptionResponse = new CustomExceptionResponse(new Date(), exception.getMessage(),
                ErrorMessage.VALIDATION_FAILED);
        log.error(ErrorMessage.EXCEPTION_OCCURED+ exception.getLocalizedMessage(), exception);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<Object> handleUserNotFoundException(final UserAlreadyExists exception) {
        CustomExceptionResponse exceptionResponse = new CustomExceptionResponse(new Date(), exception.getLocalizedMessage(),
                ErrorMessage.USER_ALREADY_EXISTS);
        log.error(ErrorMessage.EXCEPTION_OCCURED+ exception.getLocalizedMessage(), exception);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<Object> handleUserValidationException(final UserValidationException exception) {
        CustomExceptionResponse exceptionResponse = new CustomExceptionResponse(new Date(), exception.getLocalizedMessage(),
                ErrorMessage.USER_VALIDATION_FAILED);
        log.error(ErrorMessage.EXCEPTION_OCCURED+ exception.getLocalizedMessage(), exception);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        CustomExceptionResponse exceptionResponse = new CustomExceptionResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage());
        log.error(ErrorMessage.EXCEPTION_OCCURED+ ex.getLocalizedMessage(), ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }




}

