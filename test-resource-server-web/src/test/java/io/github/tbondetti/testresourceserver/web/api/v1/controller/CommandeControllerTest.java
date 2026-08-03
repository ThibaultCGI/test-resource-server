package io.github.tbondetti.testresourceserver.web.api.v1.controller;

import io.github.tbondetti.testresourceserver.core.domain.Commande;
import io.github.tbondetti.testresourceserver.core.usecase.commande.CreateCommandeUseCase;
import io.github.tbondetti.testresourceserver.core.usecase.commande.GetCommandeUseCase;
import io.github.tbondetti.testresourceserver.web.api.v1.dto.CreateCommandeRequest;
import io.github.tbondetti.testresourceserver.web.api.v1.mapper.CommandeMapper;
import io.github.tbondetti.testresourceserver.web.api.v1.response.CommandeResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static io.github.tbondetti.testresourceserver.web.api.v1.mapper.CommandeMapper.toResponse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandeControllerTest {

    @InjectMocks
    private CommandeController subject;

    @Mock
    private GetCommandeUseCase getCommandeUseCase;

    @Mock
    private CreateCommandeUseCase createCommandeUseCase;

    @Test
    void getCommandeOk() {
        final String numero = "numero";

        final Commande commande = Commande.builder().build();
        when(this.getCommandeUseCase.execute(numero)).thenReturn(commande);

        try (final MockedStatic<CommandeMapper> mapper = mockStatic(CommandeMapper.class)) {

            final CommandeResponse expected = CommandeResponse.builder().build();
            mapper.when(() -> toResponse(commande)).thenReturn(expected);

            assertSame(expected, this.subject.getCommande(numero));
        }
    }

    @Test
    void createCommandeOk() {
        final String emailClient = "emailClient";
        final List<String> numerosProduits = List.of("P1", "P2");


        final Commande commande = Commande.builder().build();
        when(this.createCommandeUseCase.execute(emailClient, numerosProduits)).thenReturn(commande);

        try (final MockedStatic<CommandeMapper> mapper = mockStatic(CommandeMapper.class)) {

            final CommandeResponse expected = CommandeResponse.builder().build();
            mapper.when(() -> toResponse(commande)).thenReturn(expected);

            final CreateCommandeRequest request = new CreateCommandeRequest(emailClient, numerosProduits);
            assertSame(expected, this.subject.createCommande(request));
        }
    }
}