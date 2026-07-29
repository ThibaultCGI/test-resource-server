package io.github.tbondetti.testresourceserver.core.utils;

import io.github.tbondetti.testresourceserver.core.exception.ResourceServerFunctionalException;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static io.github.tbondetti.testresourceserver.core.constants.CommandeRules.COMMANDE_EMAIL_CLIENT_MAX_LENGTH;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.COMMANDE_EMAIL_CLIENT_INVALID;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.COMMANDE_EMAIL_CLIENT_REQUIRED;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.COMMANDE_EMAIL_CLIENT_TOO_LONG;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.COMMANDE_NUMEROS_PRODUITS_ARE_REQUIRED;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.PRODUIT_NOT_FOUND;
import static io.github.tbondetti.testresourceserver.core.utils.CommandeValidationUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

class CommandeValidationUtilsTest {

    @Test
    void normalizeEmailClientOkWhenNull() {
        assertNull(normalizeEmailClient(null));
    }

    @Test
    void normalizeEmailClientOk() {
        assertEquals("test@test.fr", normalizeEmailClient("  TEST@TEST.FR  "));
    }

    @Test
    void validateEmailClientKoWhenNull() {
        final ResourceServerFunctionalException exception = assertThrows(
                ResourceServerFunctionalException.class,
                () -> validateEmailClient(null)
        );

        assertSame(COMMANDE_EMAIL_CLIENT_REQUIRED, exception.getCode());
        assertSame(ERROR_COMMANDE_EMAIL_CLIENT_REQUIRED, exception.getMessage());
    }

    @Test
    void validateEmailClientKoWhenBlank() {
        final ResourceServerFunctionalException exception = assertThrows(
                ResourceServerFunctionalException.class,
                () -> validateEmailClient("  ")
        );

        assertSame(COMMANDE_EMAIL_CLIENT_REQUIRED, exception.getCode());
        assertSame(ERROR_COMMANDE_EMAIL_CLIENT_REQUIRED, exception.getMessage());
    }

    @Test
    void validateEmailClientKoWhenTooLong() {
        final String email = "a".repeat(COMMANDE_EMAIL_CLIENT_MAX_LENGTH + 1);

        final ResourceServerFunctionalException exception = assertThrows(
                ResourceServerFunctionalException.class,
                () -> validateEmailClient(email)
        );

        assertSame(COMMANDE_EMAIL_CLIENT_TOO_LONG, exception.getCode());
        assertEquals(
                ERROR_COMMANDE_EMAIL_CLIENT_TOO_LONG.formatted(COMMANDE_EMAIL_CLIENT_MAX_LENGTH),
                exception.getMessage()
        );
    }

    @Test
    void validateEmailClientKoWhenInvalid() {

        final ResourceServerFunctionalException exception = assertThrows(
                ResourceServerFunctionalException.class,
                () -> validateEmailClient("invalid")
        );

        assertSame(COMMANDE_EMAIL_CLIENT_INVALID, exception.getCode());
        assertSame(ERROR_COMMANDE_EMAIL_CLIENT_INVALID, exception.getMessage());
    }

    @Test
    void validateEmailClientOk() {
        validateEmailClient("test@test.fr");
    }

    @Test
    void normalizeAndValidateEmailClientOk() {
        final String email = "email";

        try (final MockedStatic<CommandeValidationUtils> utilities = mockStatic(CommandeValidationUtils.class)) {
            final String normalizedEmailClient = "normalizedEmailClient";
            utilities.when(() -> normalizeEmailClient(email)).thenReturn(normalizedEmailClient);

            utilities.when(() -> normalizeAndValidateEmailClient(email)).thenCallRealMethod();

            assertSame(normalizedEmailClient, normalizeAndValidateEmailClient(email));

            utilities.verify(() -> normalizeEmailClient(email), times(1));
            utilities.verify(() -> validateEmailClient(normalizedEmailClient), times(1));
        }
    }

    @Test
    void normalizeNumeroProduitOkWhenNull() {
        assertNull(normalizeNumeroProduit(null));
    }

    @Test
    void normalizeNumeroProduitOk() {
        assertEquals("P1", normalizeNumeroProduit("  P1  "));
    }

    @Test
    void normalizeNumerosProduitsOkWhenNull() {
        assertEquals(List.of(), normalizeNumerosProduits(null));
    }

    @Test
    void normalizeNumerosProduitsOk() {
        assertEquals(
                List.of("P1", "P2"),
                normalizeNumerosProduits(new ArrayList<>(){{
                    add(" P1 ");
                    add("P1");
                    add(null);
                    add("   ");
                    add("P2");
                }})
        );
    }

    @Test
    void validateNumerosProduitsKoWhenNull() {

        final ResourceServerFunctionalException exception = assertThrows(
                ResourceServerFunctionalException.class,
                () -> validateNumerosProduits(List.of(), null)
        );

        assertSame(COMMANDE_NUMEROS_PRODUITS_ARE_REQUIRED, exception.getCode());
        assertSame(ERROR_COMMANDE_NUMEROS_PRODUITS_ARE_REQUIRED, exception.getMessage());
    }

    @Test
    void validateNumerosProduitsKoWhenEmpty() {

        final ResourceServerFunctionalException exception = assertThrows(
                ResourceServerFunctionalException.class,
                () -> validateNumerosProduits(List.of(), List.of())
        );

        assertSame(COMMANDE_NUMEROS_PRODUITS_ARE_REQUIRED, exception.getCode());
        assertSame(ERROR_COMMANDE_NUMEROS_PRODUITS_ARE_REQUIRED, exception.getMessage());
    }

    @Test
    void validateNumerosProduitsKoWhenProductNotFound() {
        final List<String> existingNumerosProduits = List.of("P1");
        final List<String> numerosProduits = List.of("P1", "P2");

        final ResourceServerFunctionalException exception = assertThrows(
                ResourceServerFunctionalException.class,
                () -> validateNumerosProduits(
                        existingNumerosProduits,
                        numerosProduits
                )
        );

        assertSame(PRODUIT_NOT_FOUND, exception.getCode());
        assertEquals(ERROR_PRODUITS_NOT_FOUND.formatted(List.of("P2")), exception.getMessage());
    }

    @Test
    void validateNumerosProduitsOk() {
        validateNumerosProduits(List.of("P1", "P2"), List.of("P2", "P1"));
    }
}