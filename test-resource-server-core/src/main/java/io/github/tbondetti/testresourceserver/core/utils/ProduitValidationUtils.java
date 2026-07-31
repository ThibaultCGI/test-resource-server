package io.github.tbondetti.testresourceserver.core.utils;

import io.github.tbondetti.testresourceserver.core.exception.ResourceServerFunctionalException;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

import static io.github.tbondetti.testresourceserver.core.constants.ProduitRules.PRODUIT_NOM_MAX_LENGTH;
import static io.github.tbondetti.testresourceserver.core.constants.ProduitRules.PRODUIT_NOM_MIN_LENGTH;
import static io.github.tbondetti.testresourceserver.core.constants.ProduitRules.PRODUIT_PRIX_MAX_SCALE;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_PRODUIT_NOM_REQUIRED;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_PRODUIT_NOM_TOO_LONG;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_PRODUIT_NOM_TOO_SHORT;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_PRODUIT_PRIX_HAS_TOO_MANY_DECIMALS;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_PRODUIT_PRIX_MUST_BE_POSITIVE;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_PRODUIT_PRIX_MUST_NOT_BE_NULL;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.PRODUIT_NOM_REQUIRED;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.PRODUIT_NOM_TOO_LONG;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.PRODUIT_NOM_TOO_SHORT;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.PRODUIT_PRIX_HAS_TOO_MANY_DECIMALS;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.PRODUIT_PRIX_MUST_BE_POSITIVE;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.PRODUIT_PRIX_MUST_NOT_BE_NULL;
import static java.math.RoundingMode.DOWN;
import static java.util.Objects.isNull;

@UtilityClass
public class ProduitValidationUtils {

    public static String normalizeAndValidateNom(final String nom) {
        final String normalizedNom = normalizeNom(nom);

        validateNom(normalizedNom);

        return normalizedNom;
    }

    public static void validateNom(final String nom) {
        if (isNull(nom) || nom.isBlank()) {
            throw new ResourceServerFunctionalException(PRODUIT_NOM_REQUIRED, ERROR_PRODUIT_NOM_REQUIRED);
        }

        if (nom.length() < PRODUIT_NOM_MIN_LENGTH) {
            throw new ResourceServerFunctionalException(PRODUIT_NOM_TOO_SHORT, ERROR_PRODUIT_NOM_TOO_SHORT);
        }

        if (nom.length() > PRODUIT_NOM_MAX_LENGTH) {
            throw new ResourceServerFunctionalException(PRODUIT_NOM_TOO_LONG, ERROR_PRODUIT_NOM_TOO_LONG);
        }
    }

    public static String normalizeNom(final String nom) {
        if (isNull(nom)) {
            return "";
        }

        return nom.trim();
    }

    public static BigDecimal normalizeAndValidatePrix(final BigDecimal prix) {
        final BigDecimal normalizedPrix = normalizePrix(prix);

        validatePrix(normalizedPrix);

        return normalizedPrix;
    }

    public static void validatePrix(final BigDecimal prix) {
        if (isNull(prix)) {
            throw new ResourceServerFunctionalException(PRODUIT_PRIX_MUST_NOT_BE_NULL, ERROR_PRODUIT_PRIX_MUST_NOT_BE_NULL);
        }

        if (prix.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResourceServerFunctionalException(
                    PRODUIT_PRIX_MUST_BE_POSITIVE, ERROR_PRODUIT_PRIX_MUST_BE_POSITIVE
            );
        }

        if (prix.scale() > PRODUIT_PRIX_MAX_SCALE) {
            throw new ResourceServerFunctionalException(
                    PRODUIT_PRIX_HAS_TOO_MANY_DECIMALS, ERROR_PRODUIT_PRIX_HAS_TOO_MANY_DECIMALS
            );
        }
    }

    public static BigDecimal normalizePrix(final BigDecimal prix) {
        if (isNull(prix)) {
            return null;
        }

        return prix.setScale(PRODUIT_PRIX_MAX_SCALE, DOWN);
    }
}
