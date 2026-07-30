package io.github.tbondetti.testresourceserver.infrastructure.api.v1.error;

import io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode;
import io.github.tbondetti.testresourceserver.core.exception.ResourceServerFunctionalException;
import io.github.tbondetti.testresourceserver.core.exception.ResourceServerNotFoundException;
import io.github.tbondetti.testresourceserver.core.exception.ResourceServerTechnicalException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.PRODUIT_NOM_REQUIRED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class ApiExceptionHandlerTest {

    @InjectMocks
    private ApiExceptionHandler subject;

    @Test
    void handleNotFoundExceptionOk() {
        final ResourceServerErrorCode code = PRODUIT_NOM_REQUIRED;
        final String message = "Produit introuvable";

        final ResourceServerNotFoundException exception = new ResourceServerNotFoundException(code, message);

        final ResponseEntity<ApiErrorResponse> actual = this.subject.handleNotFoundException(exception);

        assertEquals(NOT_FOUND.value(), actual.getStatusCode().value());

        final ApiErrorResponse body = ApiErrorResponse.builder()
                .code(code)
                .description(message)
                .build();

        assertEquals(body, actual.getBody());
    }

    @Test
    void handleAuthServerExceptionOk() {
        final ResourceServerErrorCode code = PRODUIT_NOM_REQUIRED;
        final String message = "Produit introuvable";

        final ResourceServerFunctionalException exception = new ResourceServerFunctionalException(code, message);

        final ResponseEntity<ApiErrorResponse> actual = this.subject.handleFunctionalException(exception);

        assertEquals(BAD_REQUEST.value(), actual.getStatusCode().value());

        final ApiErrorResponse body = ApiErrorResponse.builder()
                .code(code)
                .description(message)
                .build();

        assertEquals(body, actual.getBody());
    }

    @Test
    void handleTechnicalExceptionOk() {
        final ResourceServerErrorCode code = PRODUIT_NOM_REQUIRED;
        final String message = "Produit introuvable";

        final ResourceServerTechnicalException exception = new ResourceServerTechnicalException(code, message);

        final ResponseEntity<ApiErrorResponse> actual = this.subject.handleTechnicalException(exception);

        assertEquals(INTERNAL_SERVER_ERROR.value(), actual.getStatusCode().value());

        final ApiErrorResponse body = ApiErrorResponse.builder()
                .code(code)
                .description(message)
                .build();

        assertEquals(body, actual.getBody());
    }
}