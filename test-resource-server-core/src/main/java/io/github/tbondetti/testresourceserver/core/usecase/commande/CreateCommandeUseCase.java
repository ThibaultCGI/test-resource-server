package io.github.tbondetti.testresourceserver.core.usecase.commande;

import io.github.tbondetti.testresourceserver.core.domain.Commande;
import io.github.tbondetti.testresourceserver.core.domain.Produit;
import io.github.tbondetti.testresourceserver.core.generator.NumeroGenerator;
import io.github.tbondetti.testresourceserver.core.port.CommandeRepositoryPort;
import io.github.tbondetti.testresourceserver.core.port.ProduitRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

import static io.github.tbondetti.testresourceserver.core.constants.CommandeRules.COMMANDE_NUMERO_LENGTH;
import static io.github.tbondetti.testresourceserver.core.utils.CommandeValidationUtils.normalizeAndValidateEmailClient;
import static io.github.tbondetti.testresourceserver.core.utils.CommandeValidationUtils.normalizeNumerosProduits;
import static io.github.tbondetti.testresourceserver.core.utils.CommandeValidationUtils.validateNumerosProduits;

@RequiredArgsConstructor
public class CreateCommandeUseCase {

    private final CommandeRepositoryPort commandeRepositoryPort;
    private final ProduitRepositoryPort produitRepositoryPort;
    private final NumeroGenerator numeroGenerator;

    public Commande execute(
            final String emailClient,
            final List<String> numerosProduits
    ) {
        final String normalizedEmailClient = normalizeAndValidateEmailClient(emailClient);
        final List<String> normalizedNumerosProduits = normalizeNumerosProduits(numerosProduits);

        final List<Produit> produits = this.produitRepositoryPort.findAllByNumeroIn(normalizedNumerosProduits);
        final List<String> existingNumerosProduits = produits.stream().map(Produit::numero).toList();

        validateNumerosProduits(existingNumerosProduits, normalizedNumerosProduits);

        final BigDecimal montant = produits.stream().map(Produit::prix).reduce(BigDecimal.ZERO, BigDecimal::add);

        final Commande commande = Commande.builder()
                .numero(this.generateNumeroCommande())
                .emailClient(normalizedEmailClient)
                .montant(montant)
                .numerosProduits(existingNumerosProduits)
                .build();

        return this.commandeRepositoryPort.save(commande);
    }

    String generateNumeroCommande() {
        final Predicate<String> existsNumeroCommande = numero -> this.commandeRepositoryPort.findByNumero(numero).isPresent();

        return this.numeroGenerator.generateNumero(
                COMMANDE_NUMERO_LENGTH,
                existsNumeroCommande
        );
    }

}
