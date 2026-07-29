package io.github.tbondetti.testresourceserver.core.exception;

import org.junit.jupiter.api.Test;

import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.PRODUIT_NOM_REQUIRED;
import static org.junit.jupiter.api.Assertions.*;

class ResourceServerTechnicalExceptionTest {

    @Test
    void constructorOk() {
        final String message = "message";
        final ResourceServerErrorCode code = PRODUIT_NOM_REQUIRED;
        final ResourceServerTechnicalException exception1 = new ResourceServerTechnicalException(code, message);

        assertSame(code, exception1.getCode());
        assertSame(message, exception1.getMessage());

        final ResourceServerTechnicalException exception2 = new ResourceServerTechnicalException(code, message, exception1);
        assertSame(code, exception2.getCode());
        assertSame(message, exception2.getMessage());
        assertSame(exception1, exception2.getCause());
    }
}