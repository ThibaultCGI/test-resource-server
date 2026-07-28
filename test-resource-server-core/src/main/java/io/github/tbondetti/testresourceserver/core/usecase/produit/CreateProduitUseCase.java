package io.github.tbondetti.testresourceserver.core.usecase.produit;

import io.github.tbondetti.testresourceserver.core.domain.Produit;
import io.github.tbondetti.testresourceserver.core.exception.ResourceServerFunctionalException;
import io.github.tbondetti.testresourceserver.core.port.NumeroRepositoryPort;
import io.github.tbondetti.testresourceserver.core.port.ProduitRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static io.github.tbondetti.testresourceserver.core.constants.ProduitRules.PRODUIT_NUMERO_LENGTH;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.NUMERO_GENERATION_FAILED;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.normalizeAndValidateNom;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.normalizeAndValidatePrix;

@RequiredArgsConstructor
public class CreateProduitUseCase {
    static final int MAX_NUMERO_GENERATION_ATTEMPTS = 5;

    static final String ERROR_NUMERO_GENERATION_FAILED = "Impossible de générer un numéro de produit après plusieurs tentatives.";

    private final ProduitRepositoryPort produitRepositoryPort;
    private final NumeroRepositoryPort numeroRepositoryPort;

    public Produit execute(
            final String nom,
            final BigDecimal prix
    ){

        final String normalizedNom = normalizeAndValidateNom(nom);
        final BigDecimal normalizedPrix = normalizeAndValidatePrix(prix);

        final Produit produit = Produit.builder()
                .numero(this.generateNumero())
                .nom(normalizedNom)
                .prix(normalizedPrix)
                .build();

        return this.produitRepositoryPort.save(produit);
    }

    String generateNumero() {
        for (int i = 0; i < MAX_NUMERO_GENERATION_ATTEMPTS; i++) {
            final String numero = this.numeroRepositoryPort.generate(PRODUIT_NUMERO_LENGTH);

            if (this.produitRepositoryPort.findByNumero(numero).isEmpty()) {
                return numero;
            }
        }

        throw new ResourceServerFunctionalException(NUMERO_GENERATION_FAILED, ERROR_NUMERO_GENERATION_FAILED);
    }
}
