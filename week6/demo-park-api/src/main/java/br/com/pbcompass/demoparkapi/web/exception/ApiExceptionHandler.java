package br.com.pbcompass.demoparkapi.web.exception;

import br.com.pbcompass.demoparkapi.exception.CpfUniqueViolationException;
import br.com.pbcompass.demoparkapi.exception.InvalidEntryDataException;
import br.com.pbcompass.demoparkapi.exception.ParkingSpaceUniqueCodeViolationException;
import br.com.pbcompass.demoparkapi.exception.UsernameUniqueViolationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDeniedException(
            AccessDeniedException e,
            HttpServletRequest request) {
        log.error("Api Error - ", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(
            MethodArgumentNotValidException e,
            HttpServletRequest request) {
        log.error("Api Error - ", e);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity"));
    }

    @ExceptionHandler({UsernameUniqueViolationException.class, CpfUniqueViolationException.class, ParkingSpaceUniqueCodeViolationException.class})
    public ResponseEntity<ErrorMessage> uniqueViolationException(
            RuntimeException e,
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

    @ExceptionHandler(InvalidEntryDataException.class)
    public ResponseEntity<ErrorMessage> invalidEntryDataException(InvalidEntryDataException e,
                                                         HttpServletRequest request){
        log.error("Api Error - ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> runtimeException(RuntimeException e, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(request, HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong, please try again"));
    }
}
