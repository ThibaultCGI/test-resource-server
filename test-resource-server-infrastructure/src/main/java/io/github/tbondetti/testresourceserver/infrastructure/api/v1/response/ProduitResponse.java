package io.github.tbondetti.testresourceserver.infrastructure.api.v1.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProduitResponse(
        String numero,
        String nom,
        BigDecimal prix
) { }
