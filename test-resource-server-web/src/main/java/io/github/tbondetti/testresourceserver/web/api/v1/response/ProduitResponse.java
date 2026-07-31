package io.github.tbondetti.testresourceserver.web.api.v1.response;

import io.github.tbondetti.testresourceserver.web.openapi.response.ProduitResponseApi;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProduitResponse(
        String numero,
        String nom,
        BigDecimal prix
) implements ProduitResponseApi { }
