package com.github.evgenylizogubov.publicvoting.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalRequestDataException.class)
    public ResponseEntity<String> handleIllegalRequestDataException(IllegalRequestDataException ex) {
        log.error(ex.getMessage(), ex); // Уточнить про выбрасывание исключений в контроллере для их логирования
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handlerNotFoundException(NotFoundException ex) {
        log.error(ex.getMessage(), ex); // Уточнить про выбрасывание исключений в контроллере для их логирования
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
