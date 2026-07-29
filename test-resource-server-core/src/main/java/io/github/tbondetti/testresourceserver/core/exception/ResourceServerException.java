package io.github.tbondetti.testresourceserver.core.exception;

import lombok.Getter;

public class ResourceServerException extends RuntimeException {

    @Getter
    private final ResourceServerErrorCode code;

    public ResourceServerException(final ResourceServerErrorCode code,
                               final String message
    ) {
        this.code = code;
        super(message);
    }

    public ResourceServerException(final ResourceServerErrorCode code,
                               final String message,
                               final Throwable cause
    ) {
        this.code = code;
        super(message, cause);
    }
}
