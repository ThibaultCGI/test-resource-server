package io.github.tbondetti.testresourceserver.core.usecase.commande;

import io.github.tbondetti.testresourceserver.core.domain.Commande;
import io.github.tbondetti.testresourceserver.core.exception.ResourceServerNotFoundException;
import io.github.tbondetti.testresourceserver.core.port.CommandeRepositoryPort;
import lombok.RequiredArgsConstructor;

import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.COMMANDE_NOT_FOUND;

@RequiredArgsConstructor
public class GetCommandeUseCase {

    static final String ERROR_COMMANDE_NOT_FOUND = "Aucune commande avec le numéro %s n'est présente dans le référentiel.";

    private final CommandeRepositoryPort commandeRepositoryPort;

    public Commande execute(final String numero) {

        return this.commandeRepositoryPort.findByNumero(numero).orElseThrow(
                () -> new ResourceServerNotFoundException(COMMANDE_NOT_FOUND, ERROR_COMMANDE_NOT_FOUND.formatted(numero))
        );
    }
}
