package io.github.tbondetti.testresourceserver.core.usecase.produit;

import io.github.tbondetti.testresourceserver.core.domain.Produit;
import io.github.tbondetti.testresourceserver.core.generator.NumeroGenerator;
import io.github.tbondetti.testresourceserver.core.port.ProduitRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.function.Predicate;

import static io.github.tbondetti.testresourceserver.core.constants.ProduitRules.PRODUIT_NUMERO_LENGTH;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.normalizeAndValidateNom;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.normalizeAndValidatePrix;

@RequiredArgsConstructor
public class CreateProduitUseCase {
    private final ProduitRepositoryPort produitRepositoryPort;
    private final NumeroGenerator numeroGenerator;

    public Produit execute(
            final String nom,
            final BigDecimal prix
    ){

        final String normalizedNom = normalizeAndValidateNom(nom);
        final BigDecimal normalizedPrix = normalizeAndValidatePrix(prix);

        final Produit produit = Produit.builder()
                .numero(this.generateNumeroProduit())
                .nom(normalizedNom)
                .prix(normalizedPrix)
                .build();

        return this.produitRepositoryPort.save(produit);
    }

    String generateNumeroProduit() {
        final Predicate<String> existsNumeroProduit = numero -> this.produitRepositoryPort.findByNumero(numero).isPresent();

        return this.numeroGenerator.generateNumero(
                PRODUIT_NUMERO_LENGTH,
                existsNumeroProduit
        );
    }
}
