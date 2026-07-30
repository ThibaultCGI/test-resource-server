package io.github.tbondetti.testresourceserver.infrastructure.api.v1.controller;

import io.github.tbondetti.testresourceserver.core.usecase.produit.CreateProduitUseCase;
import io.github.tbondetti.testresourceserver.core.usecase.produit.GetProduitUseCase;
import io.github.tbondetti.testresourceserver.infrastructure.api.v1.dto.CreateProduitRequest;
import io.github.tbondetti.testresourceserver.infrastructure.api.v1.response.ProduitResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static io.github.tbondetti.testresourceserver.infrastructure.api.v1.mapper.ProduitMapper.toResponse;
import static io.github.tbondetti.testresourceserver.infrastructure.constants.Scopes.PRODUIT_READ;
import static io.github.tbondetti.testresourceserver.infrastructure.constants.Scopes.PRODUIT_WRITE;

@RestController
@RequestMapping("/api/v1/produits")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('" + PRODUIT_READ + "', '" + PRODUIT_WRITE + "')")
public class ProduitController {

    private final GetProduitUseCase getProduitUseCase;
    private final CreateProduitUseCase createProduitUseCase;

    @GetMapping("/{numero}")
    public ProduitResponse getProduit(@PathVariable final String numero) {
        return toResponse(this.getProduitUseCase.execute(numero));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('" + PRODUIT_WRITE + "')")
    public ProduitResponse createProduit(@RequestBody final CreateProduitRequest request) {
        return toResponse(this.createProduitUseCase.execute(
                request.nom(),
                request.prix()
        ));
    }
}
