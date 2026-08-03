package io.github.tbondetti.testresourceserver.web.api.v1.error;

import io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode;
import io.github.tbondetti.testresourceserver.core.exception.ResourceServerFunctionalException;
import io.github.tbondetti.testresourceserver.core.exception.ResourceServerNotFoundException;
import io.github.tbondetti.testresourceserver.core.exception.ResourceServerTechnicalException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Set;

import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.FORMAT_DONNEE_INCORRECT;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.PRODUIT_NOM_REQUIRED;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.VALIDATION_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class ApiExceptionHandlerTest {

    @InjectMocks
    private ApiExceptionHandler subject;

    @Test
    void handleConstraintViolationExceptionOk() {
        final String message = "Le nom du produit est obligatoire.";

        final ConstraintViolation<?> violation = mock(ConstraintViolation.class);

        when(violation.getMessage()).thenReturn(message);

        final ConstraintViolationException exception = new ConstraintViolationException(Set.of(violation));

        final ResponseEntity<ApiErrorResponse> actual = this.subject.handleConstraintViolationException(exception);

        assertEquals(BAD_REQUEST.value(), actual.getStatusCode().value());

        final ApiErrorResponse body = ApiErrorResponse.builder()
                .code(VALIDATION_ERROR)
                .description(message)
                .build()
                ;

        assertEquals(body, actual.getBody());
    }

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
                .build()
                ;

        assertEquals(body, actual.getBody());
    }

    @Test
    void handleMethodArgumentNotValidExceptionWithNoFieldErrorOk() {
        final BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "request");

        final MethodParameter parameter = mock(MethodParameter.class);
        final MethodArgumentNotValidException exception = new MethodArgumentNotValidException(parameter, bindingResult);

        final ResponseEntity<ApiErrorResponse> actual = this.subject.handleMethodArgumentNotValidException(exception);

        assertEquals(BAD_REQUEST.value(), actual.getStatusCode().value());

        final ApiErrorResponse body = ApiErrorResponse.builder()
                .code(VALIDATION_ERROR)
                .description(FORMAT_DONNEE_INCORRECT)
                .build();

        assertEquals(body, actual.getBody());
    }

    @Test
    void handleMethodArgumentNotValidExceptionOk() {
        final String message = "Le nom du produit est obligatoire.";

        final BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "request");

        bindingResult.addError(new FieldError("request", "nom", message));

        final MethodParameter parameter = mock(MethodParameter.class);
        final MethodArgumentNotValidException exception = new MethodArgumentNotValidException(parameter, bindingResult);

        final ResponseEntity<ApiErrorResponse> actual = this.subject.handleMethodArgumentNotValidException(exception);

        assertEquals(BAD_REQUEST.value(), actual.getStatusCode().value());

        final ApiErrorResponse body = ApiErrorResponse.builder()
                .code(VALIDATION_ERROR)
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