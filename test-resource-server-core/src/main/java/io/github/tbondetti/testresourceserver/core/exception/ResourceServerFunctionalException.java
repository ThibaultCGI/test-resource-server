package io.github.tbondetti.testresourceserver.core.exception;

public class ResourceServerFunctionalException extends ResourceServerException {

    public ResourceServerFunctionalException(
            final ResourceServerErrorCode code,
            final String message
    ) {
        super(code, message);
    }

    public ResourceServerFunctionalException(
            final ResourceServerErrorCode code,
            final String message,
            final Throwable cause
    ) {
        super(code, message, cause);
    }
}
