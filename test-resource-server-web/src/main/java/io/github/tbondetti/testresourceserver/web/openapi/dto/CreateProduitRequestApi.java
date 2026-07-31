package io.github.tbondetti.testresourceserver.web.openapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

import static io.github.tbondetti.testresourceserver.core.constants.ProduitRules.PRODUIT_NOM_MAX_LENGTH;
import static io.github.tbondetti.testresourceserver.core.constants.ProduitRules.PRODUIT_NOM_MIN_LENGTH;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.ProduitOpenApiConstants.NOM_PRODUIT_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.ProduitOpenApiConstants.NOM_PRODUIT_EXAMPLE;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.ProduitOpenApiConstants.PRIX_PRODUIT_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.ProduitOpenApiConstants.PRIX_PRODUIT_EXAMPLE;

public interface CreateProduitRequestApi {

    @Schema(
            description = NOM_PRODUIT_DESCRIPTION,
            example = NOM_PRODUIT_EXAMPLE,
            minLength = PRODUIT_NOM_MIN_LENGTH,
            maxLength = PRODUIT_NOM_MAX_LENGTH,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    String nom();

    @Schema(
            description = PRIX_PRODUIT_DESCRIPTION,
            example = PRIX_PRODUIT_EXAMPLE,
            multipleOf = 0.01,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    BigDecimal prix();
}
