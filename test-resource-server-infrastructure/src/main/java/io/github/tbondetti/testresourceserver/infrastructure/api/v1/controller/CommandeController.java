package io.github.tbondetti.testresourceserver.infrastructure.api.v1.controller;

import io.github.tbondetti.testresourceserver.core.usecase.commande.CreateCommandeUseCase;
import io.github.tbondetti.testresourceserver.core.usecase.commande.GetCommandeUseCase;
import io.github.tbondetti.testresourceserver.infrastructure.api.v1.dto.CreateCommandeRequest;
import io.github.tbondetti.testresourceserver.infrastructure.api.v1.response.CommandeResponse;
import io.github.tbondetti.testresourceserver.infrastructure.openapi.api.CommandeApi;
import jakarta.validation.Valid;
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
import static io.github.tbondetti.testresourceserver.infrastructure.constants.Authorizations.CAN_READ_COMMANDE;
import static io.github.tbondetti.testresourceserver.infrastructure.constants.Authorizations.CAN_WRITE_COMMANDE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/commandes")
@RequiredArgsConstructor
@PreAuthorize(CAN_READ_COMMANDE)
public class CommandeController implements CommandeApi {

    private final GetCommandeUseCase getCommandeUseCase;
    private final CreateCommandeUseCase createCommandeUseCase;

    @Override
    @GetMapping(
            value = "/{numero}",
            produces = APPLICATION_JSON_VALUE
    )
    public CommandeResponse getCommande(@PathVariable final String numero) {
        return toResponse(this.getCommandeUseCase.execute(numero));
    }

    @Override
    @PostMapping(
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(CAN_WRITE_COMMANDE)
    public CommandeResponse createCommande(
            @Valid @RequestBody final CreateCommandeRequest request
    ) {
        return toResponse(this.createCommandeUseCase.execute(
                request.emailClient(),
                request.numerosProduits()
        ));
    }
}
