package io.github.tbondetti.testresourceserver.web.api.v1.dto;

import io.github.tbondetti.testresourceserver.web.openapi.dto.CreateCommandeRequestApi;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

import static io.github.tbondetti.testresourceserver.core.constants.CommandeRules.COMMANDE_EMAIL_CLIENT_MAX_LENGTH;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_COMMANDE_EMAIL_CLIENT_INVALID;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_COMMANDE_EMAIL_CLIENT_REQUIRED;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_COMMANDE_EMAIL_CLIENT_TOO_LONG;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_COMMANDE_NUMEROS_PRODUITS_ARE_REQUIRED;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_COMMANDE_NUMEROS_PRODUIT_MUST_NOT_BE_BLANK;

public record CreateCommandeRequest(

        @NotBlank(
                message = ERROR_COMMANDE_EMAIL_CLIENT_REQUIRED
        )
        @Size(
                max = COMMANDE_EMAIL_CLIENT_MAX_LENGTH,
                message = ERROR_COMMANDE_EMAIL_CLIENT_TOO_LONG
        )
        @Email (
                message = ERROR_COMMANDE_EMAIL_CLIENT_INVALID
        )
        String emailClient,

        @NotEmpty(
                message = ERROR_COMMANDE_NUMEROS_PRODUITS_ARE_REQUIRED
        )
        List<@NotBlank(
                message = ERROR_COMMANDE_NUMEROS_PRODUIT_MUST_NOT_BE_BLANK
        ) String> numerosProduits
) implements CreateCommandeRequestApi { }
