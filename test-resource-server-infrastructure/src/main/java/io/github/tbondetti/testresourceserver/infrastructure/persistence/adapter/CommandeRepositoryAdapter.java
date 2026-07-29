package io.github.tbondetti.testresourceserver.infrastructure.persistence.adapter;

import io.github.tbondetti.testresourceserver.core.domain.Commande;
import io.github.tbondetti.testresourceserver.core.port.CommandeRepositoryPort;

import java.util.Optional;

import static io.github.tbondetti.testresourceserver.infrastructure.utils.DomainGeneratorUtils.generateRandomCommande;

public class CommandeRepositoryAdapter implements CommandeRepositoryPort {

    static final int MAX_NUMERO_LENGTH = 5;

    @Override
    public Optional<Commande> findByNumero(final String numero) {
        if (numero.length() > MAX_NUMERO_LENGTH) {
            return Optional.empty();
        }

        return Optional.of(generateRandomCommande(numero));
    }

    @Override
    public Commande save(final Commande commande) {
        return commande;
    }



}
