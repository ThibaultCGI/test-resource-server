package io.github.tbondetti.testresourceserver.web.openapi.response;

import io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;

import static io.github.tbondetti.testresourceserver.web.openapi.constants.OpenApiConstants.API_ERROR_RESPONSE_CODE_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.OpenApiConstants.API_ERROR_RESPONSE_CODE_EXAMPLE;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.OpenApiConstants.API_ERROR_RESPONSE_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.OpenApiConstants.API_ERROR_RESPONSE_DESCRIPTION_DESCRIPTION;

@Schema(
        description = API_ERROR_RESPONSE_DESCRIPTION
)
public interface ApiErrorResponseApi {

    @Schema(
            description = API_ERROR_RESPONSE_CODE_DESCRIPTION,
            example = API_ERROR_RESPONSE_CODE_EXAMPLE,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    ResourceServerErrorCode code();

    @Schema(
            description = API_ERROR_RESPONSE_DESCRIPTION_DESCRIPTION,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    String description();
}
