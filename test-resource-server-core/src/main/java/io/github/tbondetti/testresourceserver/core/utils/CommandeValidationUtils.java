package io.github.tbondetti.testresourceserver.core.utils;

import io.github.tbondetti.testresourceserver.core.exception.ResourceServerFunctionalException;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.github.tbondetti.testresourceserver.core.constants.CommandeRules.COMMANDE_EMAIL_CLIENT_MAX_LENGTH;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_COMMANDE_EMAIL_CLIENT_INVALID;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_COMMANDE_EMAIL_CLIENT_REQUIRED;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_COMMANDE_EMAIL_CLIENT_TOO_LONG;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_COMMANDE_NUMEROS_PRODUITS_ARE_REQUIRED;
import static io.github.tbondetti.testresourceserver.core.constants.ValidationErrorMessages.ERROR_PRODUITS_NOT_FOUND;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.COMMANDE_EMAIL_CLIENT_INVALID;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.COMMANDE_EMAIL_CLIENT_REQUIRED;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.COMMANDE_EMAIL_CLIENT_TOO_LONG;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.COMMANDE_NUMEROS_PRODUITS_ARE_REQUIRED;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.PRODUIT_NOT_FOUND;
import static java.util.Locale.ROOT;
import static java.util.Objects.isNull;

@UtilityClass
public class CommandeValidationUtils {

    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public static String normalizeAndValidateEmailClient(final String emailClient) {
        final String normalizedEmailClient = normalizeEmailClient(emailClient);

        validateEmailClient(normalizedEmailClient);

        return normalizedEmailClient;
    }

    public static void validateEmailClient(final String emailClient) {
        if (isNull(emailClient) || emailClient.isBlank()) {
            throw new ResourceServerFunctionalException(
                    COMMANDE_EMAIL_CLIENT_REQUIRED, ERROR_COMMANDE_EMAIL_CLIENT_REQUIRED
            );
        }

        if (emailClient.length() > COMMANDE_EMAIL_CLIENT_MAX_LENGTH) {
            throw new ResourceServerFunctionalException(
                    COMMANDE_EMAIL_CLIENT_TOO_LONG, ERROR_COMMANDE_EMAIL_CLIENT_TOO_LONG
            );
        }

        if (!EMAIL_PATTERN.matcher(emailClient).matches()) {
            throw new ResourceServerFunctionalException(
                    COMMANDE_EMAIL_CLIENT_INVALID, ERROR_COMMANDE_EMAIL_CLIENT_INVALID
            );
        }
    }

    public static String normalizeEmailClient(final String emailClient) {
        if (isNull(emailClient)) {
            return null;
        }

        return emailClient.trim().toLowerCase(ROOT);
    }

    public static String normalizeNumeroProduit(final String numeroProduit) {
        if (isNull(numeroProduit)) {
            return null;
        }

        return numeroProduit.trim();
    }

    public static List<String> normalizeNumerosProduits(final Collection<String> numerosProduits) {
        if (numerosProduits == null) {
            return List.of();
        }

        return numerosProduits.stream()
                .map(CommandeValidationUtils::normalizeNumeroProduit)
                .filter(Objects::nonNull)
                .filter(numero -> !numero.isBlank())
                .distinct()
                .toList()
                ;
    }

    public static void validateNumerosProduits(
            final Collection<String> existingNumerosProduits,
            final Collection<String> numerosProduits
    ) {

        if (numerosProduits == null || numerosProduits.isEmpty()) {
            throw new ResourceServerFunctionalException(
                    COMMANDE_NUMEROS_PRODUITS_ARE_REQUIRED, ERROR_COMMANDE_NUMEROS_PRODUITS_ARE_REQUIRED
            );
        }

        final Set<String> numerosNotFound = numerosProduits.stream()
                .filter(numero -> !existingNumerosProduits.contains(numero))
                .collect(Collectors.toSet())
                ;

        if (!numerosNotFound.isEmpty()) {
            throw new ResourceServerFunctionalException(PRODUIT_NOT_FOUND, ERROR_PRODUITS_NOT_FOUND.formatted(numerosNotFound));
        }
    }
}