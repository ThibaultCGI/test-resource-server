package io.github.tbondetti.testresourceserver.infrastructure.api.v1.dto;

import io.github.tbondetti.testresourceserver.infrastructure.openapi.dto.CreateProduitRequestApi;

import java.math.BigDecimal;

public record CreateProduitRequest(
        String nom,
        BigDecimal prix
) implements CreateProduitRequestApi { }
