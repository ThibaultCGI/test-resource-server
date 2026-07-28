package io.github.tbondetti.testresourceserver.core.port;

import io.github.tbondetti.testresourceserver.core.domain.Produit;

import java.util.Optional;

public interface ProduitRepositoryPort {

    Optional<Produit> findByNumero(final String numero);
    Produit save(final Produit produit);
}
