package com.exemplo.aeronaves.api;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AeronavesExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> notFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> badRequest(MethodArgumentNotValidException exception) {
        Map<String, String> campos = new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(x -> campos.put(x.getField(), x.getDefaultMessage()));

        return ResponseEntity.badRequest()
                .body(Map.of("erro", "Validação falhou", "campos", campos));
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> invalidPayload(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(Map.of(
                "erro", "Payload inválido. Verifique campos: "
        ));
    }
}