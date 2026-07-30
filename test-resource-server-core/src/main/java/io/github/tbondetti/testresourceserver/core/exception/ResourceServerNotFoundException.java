package io.github.tbondetti.testresourceserver.core.exception;

@SuppressWarnings("java:S110")
public class ResourceServerNotFoundException extends ResourceServerFunctionalException {

    public ResourceServerNotFoundException(
            final ResourceServerErrorCode code,
            final String message
    ) {
        super(code, message);
    }

    public ResourceServerNotFoundException(
            final ResourceServerErrorCode code,
            final String message,
            final Throwable cause
    ) {
        super(code, message, cause);
    }
}
