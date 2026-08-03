package io.github.tbondetti.testresourceserver.web.openapi.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

import static io.github.tbondetti.testresourceserver.core.constants.CommandeRules.COMMANDE_EMAIL_CLIENT_MAX_LENGTH;
import static io.github.tbondetti.testresourceserver.core.constants.CommandeRules.COMMANDE_NUMERO_LENGTH;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.CommandeOpenApiConstants.EMAIL_CLIENT_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.CommandeOpenApiConstants.EMAIL_CLIENT_EXAMPLE;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.CommandeOpenApiConstants.MONTANT_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.CommandeOpenApiConstants.MONTANT_EXAMPLE;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.CommandeOpenApiConstants.NUMEROS_PRODUITS_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.CommandeOpenApiConstants.NUMEROS_PRODUITS_EXAMPLE;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.CommandeOpenApiConstants.NUMERO_PARAMETER_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.CommandeOpenApiConstants.NUMERO_PARAMETER_EXAMPLE;

public interface CommandeResponseApi {

    @Schema(
            description = NUMERO_PARAMETER_DESCRIPTION,
            example = NUMERO_PARAMETER_EXAMPLE,
            minLength = COMMANDE_NUMERO_LENGTH,
            maxLength = COMMANDE_NUMERO_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    String numero();

    @Schema(
            description = EMAIL_CLIENT_DESCRIPTION,
            example = EMAIL_CLIENT_EXAMPLE,
            maxLength = COMMANDE_EMAIL_CLIENT_MAX_LENGTH,
            format = "email",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    String emailClient();

    @Schema(
            description = MONTANT_DESCRIPTION,
            example = MONTANT_EXAMPLE,
            multipleOf = 0.01,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    BigDecimal montant();

    @Schema(
            description = NUMEROS_PRODUITS_DESCRIPTION,
            example = NUMEROS_PRODUITS_EXAMPLE,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    List<String> numerosProduits();
}
