package io.github.tbondetti.testresourceserver.core.generator;

import io.github.tbondetti.testresourceserver.core.exception.ResourceServerFunctionalException;
import io.github.tbondetti.testresourceserver.core.port.NumeroRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Predicate;

import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.NUMERO_GENERATION_FAILED;
import static io.github.tbondetti.testresourceserver.core.generator.NumeroGenerator.ERROR_NUMERO_GENERATION_FAILED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NumeroGeneratorTest {

    @InjectMocks
    private NumeroGenerator subject;

    @Mock
    private NumeroRepositoryPort numeroRepositoryPort;

    @Test
    void generateNumeroOk() {
        final int numeroLength = 25;

        final String numero = "numero";
        when(this.numeroRepositoryPort.generate(numeroLength)).thenReturn(numero);

        final Predicate<String> existsNumero = _ -> false;

        assertSame(numero, this.subject.generateNumero(
                numeroLength,
                existsNumero
        ));
    }

    @Test
    void generateNumeroKo() {
        final int numeroLength = 25;

        final String numero = "numero";
        when(this.numeroRepositoryPort.generate(numeroLength)).thenReturn(numero);

        final Predicate<String> existsNumero = _ -> true;

        final ResourceServerFunctionalException exception = assertThrows(
                ResourceServerFunctionalException.class,
                () -> this.subject.generateNumero(numeroLength, existsNumero)
        );

        assertSame(NUMERO_GENERATION_FAILED, exception.getCode());
        assertEquals(ERROR_NUMERO_GENERATION_FAILED, exception.getMessage());
    }
}