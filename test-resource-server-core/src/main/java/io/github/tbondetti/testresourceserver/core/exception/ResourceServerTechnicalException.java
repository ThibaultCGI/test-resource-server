package io.github.tbondetti.testresourceserver.core.exception;

public class ResourceServerTechnicalException extends ResourceServerException {

    public ResourceServerTechnicalException(
            final ResourceServerErrorCode code,
            final String message
    ) {
        super(code, message);
    }

    public ResourceServerTechnicalException(
            final ResourceServerErrorCode code,
            final String message,
            final Throwable cause
    ) {
        super(code, message, cause);
    }
}
