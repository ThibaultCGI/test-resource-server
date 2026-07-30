package io.github.tbondetti.testresourceserver.infrastructure.api.v1.error;

import io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode;
import lombok.Builder;

@Builder
public record ApiErrorResponse (
        ResourceServerErrorCode code,
        String description
) { }