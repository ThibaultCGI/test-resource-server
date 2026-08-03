package io.github.tbondetti.testresourceserver.web.api.v1.dto;

import io.github.tbondetti.testresourceserver.web.openapi.dto.CreateProduitRequestApi;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

import static io.github.tbondetti.testresourceserver.core.constants.ProduitRules.PRODUIT_NOM_MAX_LENGTH;
import static io.github.tbondetti.testresourceserver.core.constants.ProduitRules.PRODUIT_NOM_MIN_LENGTH;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_PRODUIT_NOM_LENGTH;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_PRODUIT_NOM_REQUIRED;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_PRODUIT_PRIX_FORMAT_INCORRECT;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_PRODUIT_PRIX_MUST_BE_POSITIVE;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_PRODUIT_PRIX_MUST_NOT_BE_NULL;

public record CreateProduitRequest(
        @NotBlank(
                message = ERROR_PRODUIT_NOM_REQUIRED
        )
        @Size(
                message = ERROR_PRODUIT_NOM_LENGTH,
                min = PRODUIT_NOM_MIN_LENGTH,
                max = PRODUIT_NOM_MAX_LENGTH
        )
        String nom,

        @NotNull(
                message = ERROR_PRODUIT_PRIX_MUST_NOT_BE_NULL
        )
        @Positive(
                message = ERROR_PRODUIT_PRIX_MUST_BE_POSITIVE
        )
        @Digits(
                message = ERROR_PRODUIT_PRIX_FORMAT_INCORRECT,
                integer = 8,
                fraction = 2
        )
        BigDecimal prix
) implements CreateProduitRequestApi { }
