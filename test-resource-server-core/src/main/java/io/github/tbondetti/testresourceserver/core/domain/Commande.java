package io.github.tbondetti.testresourceserver.core.domain;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record Commande(
        String numero,
        String emailClient,
        BigDecimal montant,
        List<String> numerosProduits
) { }
