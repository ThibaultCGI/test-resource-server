package io.github.tbondetti.testresourceserver.infrastructure.api.v1.response;

import io.github.tbondetti.testresourceserver.infrastructure.openapi.response.ProduitResponseApi;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProduitResponse(
        String numero,
        String nom,
        BigDecimal prix
) implements ProduitResponseApi { }
