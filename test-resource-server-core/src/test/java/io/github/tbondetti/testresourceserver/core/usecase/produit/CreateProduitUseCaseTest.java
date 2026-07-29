package io.github.tbondetti.testresourceserver.core.usecase.produit;

import io.github.tbondetti.testresourceserver.core.domain.Produit;
import io.github.tbondetti.testresourceserver.core.generator.NumeroGenerator;
import io.github.tbondetti.testresourceserver.core.port.ProduitRepositoryPort;
import io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Predicate;

import static io.github.tbondetti.testresourceserver.core.constants.ProduitRules.PRODUIT_NUMERO_LENGTH;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.normalizeAndValidateNom;
import static io.github.tbondetti.testresourceserver.core.utils.ProduitValidationUtils.normalizeAndValidatePrix;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    private NumeroGenerator numeroGenerator;

    @Captor
    private ArgumentCaptor<Predicate<String>> predicateCaptor;

    @Test
    void generateNumeroProduitOk() {
        final String numero = "numero";

        when(this.numeroGenerator.generateNumero(
                org.mockito.Mockito.eq(PRODUIT_NUMERO_LENGTH),
                this.predicateCaptor.capture()
        )).thenReturn(numero);

        assertSame(numero, this.subject.generateNumeroProduit());

        final Predicate<String> predicate = this.predicateCaptor.getValue();
        when(this.produitRepositoryPort.findByNumero(numero)).thenReturn(Optional.empty());
        assertFalse(predicate.test(numero));

        when(this.produitRepositoryPort.findByNumero(numero)).thenReturn(Optional.of(Produit.builder().build()));
        assertTrue(predicate.test(numero));
    }

    @Test
    void executeOk() {
        final String nom = "nom";
        final BigDecimal prix = new BigDecimal("12.99");

        try (final MockedStatic<ProduitValidationUtils> utilities = mockStatic(ProduitValidationUtils.class)) {

            final String normalizedNom = "normalizedNom";
            utilities.when(() -> normalizeAndValidateNom(nom)).thenReturn(normalizedNom);

            final BigDecimal normalizedPrix = new BigDecimal("99.99");
            utilities.when(() -> normalizeAndValidatePrix(prix)).thenReturn(normalizedPrix);

            final String numero = "numero";
            doReturn(numero).when(this.subject).generateNumeroProduit();

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