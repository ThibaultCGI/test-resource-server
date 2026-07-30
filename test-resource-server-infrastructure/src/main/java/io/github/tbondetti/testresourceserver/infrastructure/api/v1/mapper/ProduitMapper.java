package io.github.tbondetti.testresourceserver.infrastructure.api.v1.mapper;

import io.github.tbondetti.testresourceserver.core.domain.Produit;
import io.github.tbondetti.testresourceserver.infrastructure.api.v1.response.ProduitResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProduitMapper {

    public static ProduitResponse toResponse(final Produit produit) {
        return ProduitResponse
                .builder()
                .numero(produit.numero())
                .nom(produit.nom())
                .prix(produit.prix())
                .build();
    }
}
