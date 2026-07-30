package io.github.tbondetti.testresourceserver.infrastructure.openapi.api;

import io.github.tbondetti.testresourceserver.infrastructure.api.v1.dto.CreateCommandeRequest;
import io.github.tbondetti.testresourceserver.infrastructure.api.v1.error.ApiErrorResponse;
import io.github.tbondetti.testresourceserver.infrastructure.api.v1.response.CommandeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import static io.github.tbondetti.testresourceserver.infrastructure.constants.Scopes.COMMANDE_READ;
import static io.github.tbondetti.testresourceserver.infrastructure.constants.Scopes.COMMANDE_WRITE;
import static io.github.tbondetti.testresourceserver.infrastructure.openapi.constants.CommandeOpenApiConstants.CREATE_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.infrastructure.openapi.constants.CommandeOpenApiConstants.CREATE_REQUEST_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.infrastructure.openapi.constants.CommandeOpenApiConstants.CREATE_SUMMARY;
import static io.github.tbondetti.testresourceserver.infrastructure.openapi.constants.CommandeOpenApiConstants.GET_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.infrastructure.openapi.constants.CommandeOpenApiConstants.GET_SUMMARY;
import static io.github.tbondetti.testresourceserver.infrastructure.openapi.constants.CommandeOpenApiConstants.NUMERO_PARAMETER_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.infrastructure.openapi.constants.CommandeOpenApiConstants.NUMERO_PARAMETER_EXAMPLE;
import static io.github.tbondetti.testresourceserver.infrastructure.openapi.constants.CommandeOpenApiConstants.RESPONSE_200_FOUND;
import static io.github.tbondetti.testresourceserver.infrastructure.openapi.constants.CommandeOpenApiConstants.RESPONSE_201_CREATED;
import static io.github.tbondetti.testresourceserver.infrastructure.openapi.constants.CommandeOpenApiConstants.RESPONSE_404_NOT_FOUND;
import static io.github.tbondetti.testresourceserver.infrastructure.openapi.constants.CommandeOpenApiConstants.TAG;
import static io.github.tbondetti.testresourceserver.infrastructure.openapi.constants.CommandeOpenApiConstants.TAG_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.infrastructure.openapi.constants.OpenApiConstants.RESPONSE_400_BAD_REQUEST;
import static io.github.tbondetti.testresourceserver.infrastructure.openapi.constants.OpenApiConstants.RESPONSE_401_UNAUTHORIZED;
import static io.github.tbondetti.testresourceserver.infrastructure.openapi.constants.OpenApiConstants.RESPONSE_403_FORBIDDEN;
import static io.github.tbondetti.testresourceserver.infrastructure.openapi.constants.OpenApiConstants.RESPONSE_500_INTERNAL_SERVER_ERROR;
import static io.github.tbondetti.testresourceserver.infrastructure.openapi.constants.OpenApiConstants.SECURITY_SCHEME_NAME;

@Tag(
        name = TAG,
        description = TAG_DESCRIPTION
)
public interface CommandeApi {

    @Operation(
            summary = GET_SUMMARY,
            description = GET_DESCRIPTION
    )
    @SecurityRequirement(
            name = SECURITY_SCHEME_NAME,
            scopes = { COMMANDE_READ, COMMANDE_WRITE }
    )
    @ApiResponse(
            responseCode = "200",
            description = RESPONSE_200_FOUND
    )
    @ApiResponse(
            responseCode = "401",
            description = RESPONSE_401_UNAUTHORIZED,
            content = @Content(
                    schema = @Schema(
                            implementation = ApiErrorResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = RESPONSE_403_FORBIDDEN,
            content = @Content(
                    schema = @Schema(
                            implementation = ApiErrorResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = RESPONSE_404_NOT_FOUND,
            content = @Content(
                    schema = @Schema(
                            implementation = ApiErrorResponse.class
                    )
            )
    )
    CommandeResponse getCommande(
            @Parameter(
                    description = NUMERO_PARAMETER_DESCRIPTION,
                    example = NUMERO_PARAMETER_EXAMPLE,
                    required = true
            )
            final String numero
    );

    @Operation(
            summary = CREATE_SUMMARY,
            description = CREATE_DESCRIPTION
    )
    @SecurityRequirement(
            name = SECURITY_SCHEME_NAME,
            scopes = COMMANDE_WRITE
    )
    @ApiResponse(
            responseCode = "201",
            description = RESPONSE_201_CREATED
    )
    @ApiResponse(
            responseCode = "400",
            description = RESPONSE_400_BAD_REQUEST,
            content = @Content(
                    schema = @Schema(
                            implementation = ApiErrorResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "401",
            description = RESPONSE_401_UNAUTHORIZED,
            content = @Content(
                    schema = @Schema(
                            implementation = ApiErrorResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = RESPONSE_403_FORBIDDEN,
            content = @Content(
                    schema = @Schema(
                            implementation = ApiErrorResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = RESPONSE_500_INTERNAL_SERVER_ERROR,
            content = @Content(
                    schema = @Schema(
                            implementation = ApiErrorResponse.class
                    )
            )
    )
    CommandeResponse createCommande(
            @RequestBody(
                    description = CREATE_REQUEST_DESCRIPTION,
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = CreateCommandeRequest.class
                            )
                    )
            )
            final CreateCommandeRequest request
    );
}
