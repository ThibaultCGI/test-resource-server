package io.github.tbondetti.testresourceserver.infrastructure.api.v1.error;

import io.github.tbondetti.testresourceserver.core.exception.ResourceServerFunctionalException;
import io.github.tbondetti.testresourceserver.core.exception.ResourceServerNotFoundException;
import io.github.tbondetti.testresourceserver.core.exception.ResourceServerTechnicalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResourceServerNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(final ResourceServerNotFoundException e) {
        final ApiErrorResponse body = ApiErrorResponse.builder()
                .code(e.getCode())
                .description(e.getMessage())
                .build();

        return ResponseEntity
                .status(NOT_FOUND)
                .body(body);
    }

    @ExceptionHandler(ResourceServerFunctionalException.class)
    public ResponseEntity<ApiErrorResponse> handleFunctionalException(final ResourceServerFunctionalException e) {
        final ApiErrorResponse body = ApiErrorResponse.builder()
                .code(e.getCode())
                .description(e.getMessage())
                .build();

        return ResponseEntity
                .badRequest()
                .body(body);
    }

    @ExceptionHandler(ResourceServerTechnicalException.class)
    public ResponseEntity<ApiErrorResponse> handleTechnicalException(final ResourceServerTechnicalException e) {
        final ApiErrorResponse body = ApiErrorResponse.builder()
                .code(e.getCode())
                .description(e.getMessage())
                .build();

        return ResponseEntity
                .internalServerError()
                .body(body);
    }
}
