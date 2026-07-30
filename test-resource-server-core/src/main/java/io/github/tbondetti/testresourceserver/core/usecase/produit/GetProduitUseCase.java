package io.github.tbondetti.testresourceserver.core.usecase.produit;

import io.github.tbondetti.testresourceserver.core.domain.Produit;
import io.github.tbondetti.testresourceserver.core.exception.ResourceServerNotFoundException;
import io.github.tbondetti.testresourceserver.core.port.ProduitRepositoryPort;
import lombok.RequiredArgsConstructor;

import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.PRODUIT_NOT_FOUND;

@RequiredArgsConstructor
public class GetProduitUseCase {

    static final String ERROR_PRODUIT_NOT_FOUND = "Aucun produit avec le numéro %s n'est présent dans le référentiel.";

    private final ProduitRepositoryPort produitRepositoryPort;

    public Produit execute(final String numero) {

        return this.produitRepositoryPort.findByNumero(numero).orElseThrow(
                () -> new ResourceServerNotFoundException(PRODUIT_NOT_FOUND, ERROR_PRODUIT_NOT_FOUND.formatted(numero))
        );
    }
}
