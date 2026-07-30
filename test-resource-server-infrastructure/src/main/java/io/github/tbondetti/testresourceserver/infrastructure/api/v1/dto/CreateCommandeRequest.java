package io.github.tbondetti.testresourceserver.infrastructure.api.v1.dto;

import io.github.tbondetti.testresourceserver.infrastructure.openapi.dto.CreateCommandeRequestApi;

import java.util.List;

public record CreateCommandeRequest(
        String emailClient,
        List<String> numerosProduits
) implements CreateCommandeRequestApi { }
