package io.github.tbondetti.testresourceserver.infrastructure.persistence.adapter;

import io.github.tbondetti.testresourceserver.core.domain.Produit;
import io.github.tbondetti.testresourceserver.core.port.ProduitRepositoryPort;
import io.github.tbondetti.testresourceserver.infrastructure.utils.DomainGeneratorUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static io.github.tbondetti.testresourceserver.infrastructure.utils.DomainGeneratorUtils.generateRandomProduit;

public class ProduitRepositoryAdapter implements ProduitRepositoryPort {

    static final int MAX_EXISTING_PRODUCTS = 5;
    static final int MAX_NUMERO_LENGTH = 5;


    @Override
    public Optional<Produit> findByNumero(final String numero) {
        if (numero.length() > MAX_NUMERO_LENGTH) {
            return Optional.empty();
        }

        return Optional.of(generateRandomProduit(numero));
    }

    @Override
    public Produit save(final Produit produit) {
        return produit;
    }

    @Override
    public List<Produit> findAllByNumeroIn(final Collection<String> numeros) {
        if (numeros.size() > MAX_EXISTING_PRODUCTS) {
            return numeros.stream()
                    .limit(MAX_EXISTING_PRODUCTS - 1L)
                    .map(DomainGeneratorUtils::generateRandomProduit)
                    .toList();
        }
        return numeros
                .stream()
                .map(DomainGeneratorUtils::generateRandomProduit)
                .toList();
    }


}
