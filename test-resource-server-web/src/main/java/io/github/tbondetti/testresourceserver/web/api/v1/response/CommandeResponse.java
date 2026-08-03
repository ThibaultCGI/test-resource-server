package io.github.tbondetti.testresourceserver.web.api.v1.response;

import io.github.tbondetti.testresourceserver.web.openapi.response.CommandeResponseApi;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record CommandeResponse(
        String numero,
        String emailClient,
        BigDecimal montant,
        List<String> numerosProduits
) implements CommandeResponseApi { }
