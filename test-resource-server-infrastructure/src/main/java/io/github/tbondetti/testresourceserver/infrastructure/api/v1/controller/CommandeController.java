package io.github.tbondetti.testresourceserver.infrastructure.api.v1.controller;

import io.github.tbondetti.testresourceserver.core.usecase.commande.CreateCommandeUseCase;
import io.github.tbondetti.testresourceserver.core.usecase.commande.GetCommandeUseCase;
import io.github.tbondetti.testresourceserver.infrastructure.api.v1.dto.CreateCommandeRequest;
import io.github.tbondetti.testresourceserver.infrastructure.api.v1.response.CommandeResponse;
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

import static io.github.tbondetti.testresourceserver.infrastructure.api.v1.mapper.CommandeMapper.toResponse;
import static io.github.tbondetti.testresourceserver.infrastructure.constants.Scopes.COMMANDE_READ;
import static io.github.tbondetti.testresourceserver.infrastructure.constants.Scopes.COMMANDE_WRITE;

@RestController
@RequestMapping("/api/v1/commandes")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('" + COMMANDE_READ + "', '" + COMMANDE_WRITE + "')")
public class CommandeController {

    private final GetCommandeUseCase getCommandeUseCase;
    private final CreateCommandeUseCase createCommandeUseCase;

    @GetMapping("/{numero}")
    public CommandeResponse getCommande(@PathVariable final String numero) {
        return toResponse(this.getCommandeUseCase.execute(numero));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('" + COMMANDE_WRITE + "')")
    public CommandeResponse createCommande(@RequestBody final CreateCommandeRequest request) {
        return toResponse(this.createCommandeUseCase.execute(
                request.emailClient(),
                request.numerosProduits()
        ));
    }
}
