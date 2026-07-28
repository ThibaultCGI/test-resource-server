package io.github.tbondetti.testresourceserver.core.port;

import io.github.tbondetti.testresourceserver.core.domain.Commande;

import java.util.Optional;

public interface CommandeRepositoryPort {

    Optional<Commande> findByNumero(final String numero);
}
