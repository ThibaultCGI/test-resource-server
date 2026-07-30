package io.github.tbondetti.testresourceserver.infrastructure.api.v1.mapper;

import io.github.tbondetti.testresourceserver.core.domain.Commande;
import io.github.tbondetti.testresourceserver.infrastructure.api.v1.response.CommandeResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static io.github.tbondetti.testresourceserver.infrastructure.api.v1.mapper.CommandeMapper.toResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandeMapperTest {

    @Test
    void toResponseOk() {

        final String numero = "numero";
        final String emailClient = "emailClient";
        final BigDecimal montant = new BigDecimal("123.45");
        final List<String> numerosProduits = List.of("P1", "P2");

        final Commande commande = Commande.builder()
                .numero(numero)
                .emailClient(emailClient)
                .montant(montant)
                .numerosProduits(numerosProduits)
                .build();

        final CommandeResponse expected = CommandeResponse.builder()
                .numero(numero)
                .emailClient(emailClient)
                .montant(montant)
                .numerosProduits(numerosProduits)
                .build();

        assertEquals(expected, toResponse(commande));
    }
}