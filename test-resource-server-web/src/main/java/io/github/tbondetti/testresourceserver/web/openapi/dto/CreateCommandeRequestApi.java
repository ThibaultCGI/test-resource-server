package io.github.tbondetti.testresourceserver.web.openapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

import static io.github.tbondetti.testresourceserver.core.constants.CommandeRules.COMMANDE_EMAIL_CLIENT_MAX_LENGTH;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.CommandeOpenApiConstants.EMAIL_CLIENT_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.CommandeOpenApiConstants.EMAIL_CLIENT_EXAMPLE;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.CommandeOpenApiConstants.NUMEROS_PRODUITS_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.CommandeOpenApiConstants.NUMEROS_PRODUITS_EXAMPLE;

public interface CreateCommandeRequestApi {

    @Schema(
            description = EMAIL_CLIENT_DESCRIPTION,
            example = EMAIL_CLIENT_EXAMPLE,
            maxLength = COMMANDE_EMAIL_CLIENT_MAX_LENGTH,
            format = "email",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    String emailClient();

    @Schema(
            description = NUMEROS_PRODUITS_DESCRIPTION,
            example = NUMEROS_PRODUITS_EXAMPLE,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    List<String> numerosProduits();
}
