package io.github.tbondetti.testresourceserver.infrastructure.api.v1.dto;

import java.math.BigDecimal;

public record CreateProduitRequest(
        String nom,
        BigDecimal prix
) { }
