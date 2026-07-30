package io.github.tbondetti.testresourceserver.infrastructure.api.v1.error;

import io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode;
import io.github.tbondetti.testresourceserver.infrastructure.openapi.response.ApiErrorResponseApi;
import lombok.Builder;

@Builder
public record ApiErrorResponse (
        ResourceServerErrorCode code,
        String description
) implements ApiErrorResponseApi { }