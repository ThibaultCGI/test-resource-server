package io.github.tbondetti.testresourceserver.core.domain;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record Produit(
        String numero,
        String nom,
        BigDecimal prix
) { }
