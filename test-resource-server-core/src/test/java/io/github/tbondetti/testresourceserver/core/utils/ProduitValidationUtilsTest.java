package io.github.tbondetti.testresourceserver.core.utils;

import io.github.tbondetti.testresourceserver.core.exception.ResourceServerFunctionalException;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.math.BigDecimal;

import static io.github.tbondetti.testresourceserver.core.constants.ProduitRules.PRODUIT_NOM_MAX_LENGTH;
import static io.github.tbondetti.testresourceserver.core.constants.ProduitRules.PRODUIT_NOM_MIN_LENGTH;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.PRODUIT_NOM_REQUIRED;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.PRODUIT_NOM_TOO_LONG;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.PRODUIT_NOM_TOO_SHORT;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.PRODUIT_PRIX_HAS_TOO_MANY_DECIMALS;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.PRODUIT_PRIX_MUST_BE_POSITIVE;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.PRODUIT_PRIX_MUST_NOT_BE_NULL;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.ERROR_PRODUIT_NOM_REQUIRED;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.ERROR_PRODUIT_NOM_TOO_LONG;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.ERROR_PRODUIT_NOM_TOO_SHORT;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.ERROR_PRODUIT_PRIX_HAS_TOO_MANY_DECIMALS;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.ERROR_PRODUIT_PRIX_MUST_BE_POSITIVE;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.ERROR_PRODUIT_PRIX_MUST_NOT_BE_NULL;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.normalizeAndValidateNom;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.normalizeAndValidatePrix;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.normalizeNom;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.normalizePrix;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.validateNom;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.validatePrix;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

class ProduitValidationUtilsTest {

    @Test
    void normalizeNomOkWhenNull() {
        assertEquals("", normalizeNom(null));
    }

    @Test
    void normalizeNomOk() {
        assertEquals("nom", normalizeNom("   nom   "));
    }

    @Test
    void validateNomKoWhenNull() {
        final ResourceServerFunctionalException exception = assertThrows(
                ResourceServerFunctionalException.class,
                () -> validateNom(null)
        );

        assertSame(PRODUIT_NOM_REQUIRED, exception.getCode());
        assertSame(ERROR_PRODUIT_NOM_REQUIRED, exception.getMessage());
    }

    @Test
    void validateNomKoWhenBlank() {
        final ResourceServerFunctionalException exception = assertThrows(
                ResourceServerFunctionalException.class,
                () -> validateNom("   ")
        );

        assertSame(PRODUIT_NOM_REQUIRED, exception.getCode());
        assertSame(ERROR_PRODUIT_NOM_REQUIRED, exception.getMessage());
    }

    @Test
    void validateNomKoWhenTooShort() {
        final String nom = "a".repeat(PRODUIT_NOM_MIN_LENGTH - 1);

        final ResourceServerFunctionalException exception = assertThrows(
                ResourceServerFunctionalException.class,
                () -> validateNom(nom)
        );

        assertSame(PRODUIT_NOM_TOO_SHORT, exception.getCode());

        assertEquals(
                ERROR_PRODUIT_NOM_TOO_SHORT.formatted(PRODUIT_NOM_MIN_LENGTH),
                exception.getMessage()
        );
    }

    @Test
    void validateNomKoWhenTooLong() {
        final String nom = "a".repeat(PRODUIT_NOM_MAX_LENGTH + 1);

        final ResourceServerFunctionalException exception = assertThrows(
                ResourceServerFunctionalException.class,
                () -> validateNom(nom)
        );

        assertSame(PRODUIT_NOM_TOO_LONG, exception.getCode());
        assertEquals(
                ERROR_PRODUIT_NOM_TOO_LONG.formatted(PRODUIT_NOM_MAX_LENGTH),
                exception.getMessage()
        );
    }

    @Test
    void validateNomOk() {
        validateNom("a".repeat(PRODUIT_NOM_MAX_LENGTH));
    }

    @Test
    void normalizeAndValidateNomOk() {
        final String nom = "nom";
        try (final MockedStatic<ProduitValidationUtils> utilities = mockStatic(ProduitValidationUtils.class)) {
            final String normalizedNom = "normalizedNom";
            utilities.when(() -> normalizeNom(nom)).thenReturn(normalizedNom);

            utilities.when(() -> normalizeAndValidateNom(nom)).thenCallRealMethod();

            assertSame(normalizedNom, normalizeAndValidateNom(nom));

            utilities.verify(() -> normalizeNom(nom), times(1));
            utilities.verify(() -> validateNom(normalizedNom), times(1));
        }
    }

    @Test
    void normalizePrixOkWhenNull() {
        assertNull(normalizePrix(null));
    }

    @Test
    void normalizePrixOk() {
        assertEquals(new BigDecimal("12.99"), normalizePrix(new BigDecimal("12.999")));
    }

    @Test
    void validatePrixKoWhenNull() {
        final ResourceServerFunctionalException exception = assertThrows(
                ResourceServerFunctionalException.class,
                () -> validatePrix(null)
        );

        assertSame(PRODUIT_PRIX_MUST_NOT_BE_NULL, exception.getCode());
        assertSame(ERROR_PRODUIT_PRIX_MUST_NOT_BE_NULL, exception.getMessage());
    }

    @Test
    void validatePrixKoWhenZero() {
        final ResourceServerFunctionalException exception = assertThrows(
                ResourceServerFunctionalException.class,
                () -> validatePrix(ZERO)
        );

        assertSame(PRODUIT_PRIX_MUST_BE_POSITIVE, exception.getCode());
        assertSame(ERROR_PRODUIT_PRIX_MUST_BE_POSITIVE, exception.getMessage());
    }

    @Test
    void validatePrixKoWhenNegative() {
        final ResourceServerFunctionalException exception = assertThrows(
                ResourceServerFunctionalException.class,
                () -> validatePrix(new BigDecimal("-10"))
        );

        assertSame(PRODUIT_PRIX_MUST_BE_POSITIVE, exception.getCode());
        assertSame(ERROR_PRODUIT_PRIX_MUST_BE_POSITIVE, exception.getMessage());
    }

    @Test
    void validatePrixKoWhenTooManyDecimals() {
        final ResourceServerFunctionalException exception = assertThrows(
                ResourceServerFunctionalException.class,
                () -> validatePrix(new BigDecimal("12.999"))
        );

        assertSame(PRODUIT_PRIX_HAS_TOO_MANY_DECIMALS, exception.getCode());
        assertSame(ERROR_PRODUIT_PRIX_HAS_TOO_MANY_DECIMALS, exception.getMessage());
    }

    @Test
    void validatePrixOk() {
        validatePrix(new BigDecimal("12.99"));
    }

    @Test
    void normalizeAndValidatePrixOk() {
        final BigDecimal prix =  new BigDecimal("12.99");
        try (final MockedStatic<ProduitValidationUtils> utilities = mockStatic(ProduitValidationUtils.class)) {
            final BigDecimal normalizedPrix = new BigDecimal("123456");
            utilities.when(() -> normalizePrix(prix)).thenReturn(normalizedPrix);

            utilities.when(() -> normalizeAndValidatePrix(prix)).thenCallRealMethod();

            assertSame(normalizedPrix, normalizeAndValidatePrix(prix));

            utilities.verify(() -> normalizePrix(prix), times(1));
            utilities.verify(() -> validatePrix(normalizedPrix), times(1));
        }
    }
}