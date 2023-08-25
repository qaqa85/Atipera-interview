package com.atipera.interview.common.rest;

import com.atipera.interview.github.exceptions.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import lombok.*;
import org.springframework.core.*;
import org.springframework.core.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
class GlobalExceptionHandler {
    private final ObjectMapper objectMapper;

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<ErrorResponse> handleRestException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(NotAcceptableApplicationException.class)
    ResponseEntity<byte[]> handleRestException(NotAcceptableApplicationException ex) throws JsonProcessingException {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage());
        try {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(objectMapper.writeValueAsBytes(errorResponse));
        } catch (JsonProcessingException e) {
            e.addSuppressed(ex);
            throw e;
        }
    }
}
