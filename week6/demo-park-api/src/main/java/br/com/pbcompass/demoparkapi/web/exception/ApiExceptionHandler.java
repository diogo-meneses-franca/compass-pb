package br.com.pbcompass.demoparkapi.web.exception;

import br.com.pbcompass.demoparkapi.exception.UsernameUniqueViolationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(
            MethodArgumentNotValidException e,
            HttpServletRequest request) {
        log.error("Api Error - ", e);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage()));
    }

    @ExceptionHandler(UsernameUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> usernameUniqueViolationException(
            UsernameUniqueViolationException e,
            HttpServletRequest request) {
        log.error("Api Error - ", e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessage(request, HttpStatus.CONFLICT, e.getMessage()));

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException e,
                                                                HttpServletRequest request) {
        log.error("Api Error - ", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(request, HttpStatus.NOT_FOUND, e.getMessage()));
    }
}
