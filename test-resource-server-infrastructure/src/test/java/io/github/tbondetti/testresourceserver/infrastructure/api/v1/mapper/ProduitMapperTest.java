package io.github.tbondetti.testresourceserver.infrastructure.api.v1.mapper;

import io.github.tbondetti.testresourceserver.core.domain.Produit;
import io.github.tbondetti.testresourceserver.infrastructure.api.v1.response.ProduitResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.github.tbondetti.testresourceserver.infrastructure.api.v1.mapper.ProduitMapper.toResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProduitMapperTest {

    @Test
    void toResponseOk() {

        final String numero = "numero";
        final String nom = "nom";
        final BigDecimal prix = new BigDecimal("12.34");

        final Produit produit = Produit.builder()
                .numero(numero)
                .nom(nom)
                .prix(prix)
                .build();

        final ProduitResponse expected = ProduitResponse.builder()
                .numero(numero)
                .nom(nom)
                .prix(prix)
                .build();

        assertEquals(expected, toResponse(produit));
    }
}
