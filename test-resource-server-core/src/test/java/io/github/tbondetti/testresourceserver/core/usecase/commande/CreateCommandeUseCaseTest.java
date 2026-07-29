package io.github.tbondetti.testresourceserver.core.usecase.commande;

import io.github.tbondetti.testresourceserver.core.domain.Commande;
import io.github.tbondetti.testresourceserver.core.domain.Produit;
import io.github.tbondetti.testresourceserver.core.generator.NumeroGenerator;
import io.github.tbondetti.testresourceserver.core.port.CommandeRepositoryPort;
import io.github.tbondetti.testresourceserver.core.port.ProduitRepositoryPort;
import io.github.tbondetti.testresourceserver.core.utils.CommandeValidationUtils;
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
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static io.github.tbondetti.testresourceserver.core.constants.CommandeRules.COMMANDE_NUMERO_LENGTH;
import static io.github.tbondetti.testresourceserver.core.utils.CommandeValidationUtils.normalizeAndValidateEmailClient;
import static io.github.tbondetti.testresourceserver.core.utils.CommandeValidationUtils.normalizeNumerosProduits;
import static io.github.tbondetti.testresourceserver.core.utils.CommandeValidationUtils.validateNumerosProduits;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCommandeUseCaseTest {

    @Spy
    @InjectMocks
    private CreateCommandeUseCase subject;

    @Mock
    private CommandeRepositoryPort commandeRepositoryPort;

    @Mock
    private ProduitRepositoryPort produitRepositoryPort;

    @Mock
    private NumeroGenerator numeroGenerator;

    @Captor
    private ArgumentCaptor<Predicate<String>> predicateCaptor;


    @Test
    void generateNumeroCommandeOk() {

        final String numero = "numero";

        when(this.numeroGenerator.generateNumero(
                eq(COMMANDE_NUMERO_LENGTH),
                this.predicateCaptor.capture()
        )).thenReturn(numero);

        assertSame(numero, this.subject.generateNumeroCommande());

        final Predicate<String> predicate = this.predicateCaptor.getValue();
        when(this.commandeRepositoryPort.findByNumero(numero)).thenReturn(Optional.empty());
        assertFalse(predicate.test(numero));

        when(this.commandeRepositoryPort.findByNumero(numero)).thenReturn(Optional.of(Commande.builder().build()));
        assertTrue(predicate.test(numero));
    }

    @Test
    void executeOk() {
        final String emailClient = "emailClient";
        final List<String> numerosProduits = List.of("numeroProduit1", "numeroProduit2");

        try (final MockedStatic<CommandeValidationUtils> utilities = mockStatic(CommandeValidationUtils.class)) {

            final String normalizedEmailClient = "normalizedEmailClient";
            utilities.when(() -> normalizeAndValidateEmailClient(emailClient)).thenReturn(normalizedEmailClient);

            final List<String> normalizedNumerosProduits = List.of("normalizedNumeroProduit1", "normalizedNumeroProduit2");
            utilities.when(() -> normalizeNumerosProduits(numerosProduits)).thenReturn(normalizedNumerosProduits);

            final String existingNumeroProduit1 = "existingNumeroProduit1";
            final Produit produit1 = Produit.builder()
                    .numero(existingNumeroProduit1)
                    .prix(new BigDecimal("10"))
                    .build();
            final String existingNumeroProduit2 = "existingNumeroProduit2";
            final Produit produit2 = Produit.builder()
                    .numero(existingNumeroProduit2)
                    .prix(new BigDecimal("15"))
                    .build();
            final List<Produit> produits = List.of(produit1, produit2);
            when(this.produitRepositoryPort.findAllByNumeroIn(normalizedNumerosProduits)).thenReturn(produits);

            final List<String> existingNumerosProduits = List.of(existingNumeroProduit1, existingNumeroProduit2);

            utilities.when(
                    () -> validateNumerosProduits(existingNumerosProduits, normalizedNumerosProduits)
            ).thenAnswer(_ -> null);

            final String numeroCommande = "numeroCommande";
            doReturn(numeroCommande).when(this.subject).generateNumeroCommande();

            final Commande commande = Commande.builder()
                    .numero(numeroCommande)
                    .emailClient(normalizedEmailClient)
                    .montant(new BigDecimal("25"))
                    .numerosProduits(existingNumerosProduits)
                    .build();

            final Commande expected = Commande.builder().build();

            when(this.commandeRepositoryPort.save(commande)).thenReturn(expected);

            assertSame(expected, this.subject.execute(
                    emailClient,
                    numerosProduits
            ));
        }
    }
}