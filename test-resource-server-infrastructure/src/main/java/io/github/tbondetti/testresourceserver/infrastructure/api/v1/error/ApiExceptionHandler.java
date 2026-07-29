package io.github.tbondetti.testresourceserver.infrastructure.api.v1.error;

import io.github.tbondetti.testresourceserver.core.exception.ResourceServerFunctionalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResourceServerFunctionalException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthServerException(final ResourceServerFunctionalException e) {
        final ApiErrorResponse body = ApiErrorResponse.builder()
                .code(e.getCode())
                .description(e.getMessage())
                .build();

        return ResponseEntity
                .badRequest()
                .body(body);
    }
}
