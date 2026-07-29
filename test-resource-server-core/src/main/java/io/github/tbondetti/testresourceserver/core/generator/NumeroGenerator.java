package io.github.tbondetti.testresourceserver.core.generator;

import io.github.tbondetti.testresourceserver.core.exception.ResourceServerFunctionalException;
import io.github.tbondetti.testresourceserver.core.port.NumeroRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.NUMERO_GENERATION_FAILED;

@RequiredArgsConstructor
public class NumeroGenerator {

    static final int MAX_NUMERO_GENERATION_ATTEMPTS = 5;
    static final String ERROR_NUMERO_GENERATION_FAILED = "Impossible de générer un numéro après plusieurs tentatives.";

    private final NumeroRepositoryPort numeroRepositoryPort;

    public String generateNumero(
            final int numeroLength,
            final Predicate<String> existsNumero
    ) {
        for (int i = 0; i < MAX_NUMERO_GENERATION_ATTEMPTS; i++) {
            final String numero = this.numeroRepositoryPort.generate(numeroLength);

            if (!existsNumero.test(numero)) {
                return numero;
            }
        }

        throw new ResourceServerFunctionalException(NUMERO_GENERATION_FAILED, ERROR_NUMERO_GENERATION_FAILED);
    }
}
