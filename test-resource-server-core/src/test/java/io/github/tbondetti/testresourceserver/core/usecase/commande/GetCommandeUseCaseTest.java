package io.github.tbondetti.testresourceserver.core.usecase.commande;

import io.github.tbondetti.testresourceserver.core.domain.Commande;
import io.github.tbondetti.testresourceserver.core.exception.ResourceServerFunctionalException;
import io.github.tbondetti.testresourceserver.core.port.CommandeRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.COMMANDE_NOT_FOUND;
import static io.github.tbondetti.testresourceserver.core.usecase.commande.GetCommandeUseCase.ERROR_COMMANDE_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCommandeUseCaseTest {

    @InjectMocks
    private GetCommandeUseCase subject;

    @Mock
    private CommandeRepositoryPort commandeRepositoryPort;


    @Test
    void executeKo() {
        final String numero = "numero";

        when(this.commandeRepositoryPort.findByNumero(numero)).thenReturn(Optional.empty());

        final ResourceServerFunctionalException exception = assertThrows(
                ResourceServerFunctionalException.class,
                () -> this.subject.execute(numero)
        );

        assertSame(COMMANDE_NOT_FOUND, exception.getCode());
        assertEquals(ERROR_COMMANDE_NOT_FOUND.formatted(numero), exception.getMessage());
    }

    @Test
    void executeOk() {
        final String numero = "numero";

        final Commande expected = Commande.builder().build();

        when(this.commandeRepositoryPort.findByNumero(numero)).thenReturn(Optional.of(expected));

        assertSame(expected, this.subject.execute(numero));
    }
}