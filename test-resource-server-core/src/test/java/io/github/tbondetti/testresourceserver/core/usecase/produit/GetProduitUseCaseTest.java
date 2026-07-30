package io.github.tbondetti.testresourceserver.core.usecase.produit;

import io.github.tbondetti.testresourceserver.core.domain.Produit;
import io.github.tbondetti.testresourceserver.core.exception.ResourceServerNotFoundException;
import io.github.tbondetti.testresourceserver.core.port.ProduitRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.PRODUIT_NOT_FOUND;
import static io.github.tbondetti.testresourceserver.core.usecase.produit.GetProduitUseCase.ERROR_PRODUIT_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProduitUseCaseTest {

    @InjectMocks
    private GetProduitUseCase subject;

    @Mock
    private ProduitRepositoryPort produitRepositoryPort;


    @Test
    void executeKo() {
        final String numero = "numero";

        when(this.produitRepositoryPort.findByNumero(numero)).thenReturn(Optional.empty());

        final ResourceServerNotFoundException exception = assertThrows(
                ResourceServerNotFoundException.class,
                () -> this.subject.execute(numero)
        );

        assertSame(PRODUIT_NOT_FOUND, exception.getCode());
        assertEquals(ERROR_PRODUIT_NOT_FOUND.formatted(numero), exception.getMessage());
    }

    @Test
    void executeOk() {
        final String numero = "numero";

        final Produit expected = Produit.builder().build();

        when(this.produitRepositoryPort.findByNumero(numero)).thenReturn(Optional.of(expected));

        assertSame(expected, this.subject.execute(numero));
    }
}