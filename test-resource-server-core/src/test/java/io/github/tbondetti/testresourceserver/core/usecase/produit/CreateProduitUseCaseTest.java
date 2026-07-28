package io.github.tbondetti.testresourceserver.core.usecase.produit;

import io.github.tbondetti.testresourceserver.core.domain.Produit;
import io.github.tbondetti.testresourceserver.core.exception.ResourceServerFunctionalException;
import io.github.tbondetti.testresourceserver.core.port.NumeroRepositoryPort;
import io.github.tbondetti.testresourceserver.core.port.ProduitRepositoryPort;
import io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static io.github.tbondetti.testresourceserver.core.constants.ProduitRules.PRODUIT_NUMERO_LENGTH;
import static io.github.tbondetti.testresourceserver.core.exception.ResourceServerErrorCode.NUMERO_GENERATION_FAILED;
import static io.github.tbondetti.testresourceserver.core.usecase.produit.CreateProduitUseCase.ERROR_NUMERO_GENERATION_FAILED;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.normalizeAndValidateNom;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.normalizeAndValidatePrix;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateProduitUseCaseTest {

    @Spy
    @InjectMocks
    private CreateProduitUseCase subject;

    @Mock
    private ProduitRepositoryPort produitRepositoryPort;

    @Mock
    private NumeroRepositoryPort numeroRepositoryPort;

    @Test
    void generateNumeroOk() {
        final String numero = "numero";

        when(this.numeroRepositoryPort.generate(PRODUIT_NUMERO_LENGTH)).thenReturn(numero);

        when(this.produitRepositoryPort.findByNumero(numero)).thenReturn(Optional.empty());

        assertSame(numero, this.subject.generateNumero());
    }

    @Test
    void generateNumeroKo() {
        final String numero = "numero";

        when(this.numeroRepositoryPort.generate(PRODUIT_NUMERO_LENGTH)).thenReturn(numero);

        when(this.produitRepositoryPort.findByNumero(numero)).thenReturn(Optional.of(Produit.builder().build()));

        final ResourceServerFunctionalException exception = assertThrows(
                ResourceServerFunctionalException.class,
                this.subject::generateNumero
        );

        assertSame(NUMERO_GENERATION_FAILED, exception.getCode());
        assertSame(ERROR_NUMERO_GENERATION_FAILED, exception.getMessage());
    }

    @Test
    void executeOk() {
        final String nom = "nom";
        final BigDecimal prix = new BigDecimal("12.999");

        try (final MockedStatic<ProduitValidationUtils> utilities = mockStatic(ProduitValidationUtils.class)) {
            final String normalizedNom = "normalizedNom";
            utilities.when(() -> normalizeAndValidateNom(nom)).thenReturn(normalizedNom);

            final BigDecimal normalizedPrix = new BigDecimal("12.99");
            utilities.when(() -> normalizeAndValidatePrix(prix)).thenReturn(normalizedPrix);

            final String numero = "numero";
            doReturn(numero).when(this.subject).generateNumero(); // déjà testé

            final Produit produit = Produit.builder()
                    .numero(numero)
                    .nom(normalizedNom)
                    .prix(normalizedPrix)
                    .build();

            final Produit expected = Produit.builder().build();

            when(this.produitRepositoryPort.save(produit)).thenReturn(expected);

            assertSame(expected, this.subject.execute(nom, prix));
        }
    }
}
