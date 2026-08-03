package io.github.tbondetti.testresourceserver.web.api.v1.controller;

import io.github.tbondetti.testresourceserver.core.domain.Produit;
import io.github.tbondetti.testresourceserver.core.usecase.produit.CreateProduitUseCase;
import io.github.tbondetti.testresourceserver.core.usecase.produit.GetProduitUseCase;
import io.github.tbondetti.testresourceserver.web.api.v1.dto.CreateProduitRequest;
import io.github.tbondetti.testresourceserver.web.api.v1.mapper.ProduitMapper;
import io.github.tbondetti.testresourceserver.web.api.v1.response.ProduitResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static io.github.tbondetti.testresourceserver.web.api.v1.mapper.ProduitMapper.toResponse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProduitControllerTest {

    @InjectMocks
    private ProduitController subject;

    @Mock
    private GetProduitUseCase getProduitUseCase;

    @Mock
    private CreateProduitUseCase createProduitUseCase;

    @Test
    void getProduitOk() {
        final String numero = "numero";

        final Produit produit = Produit.builder().build();
        when(this.getProduitUseCase.execute(numero)).thenReturn(produit);

        try (final MockedStatic<ProduitMapper> mapper = mockStatic(ProduitMapper.class)) {

            final ProduitResponse expected = ProduitResponse.builder().build();
            mapper.when(() -> toResponse(produit)).thenReturn(expected);

            assertSame(expected, this.subject.getProduit(numero));
        }
    }

    @Test
    void createProduitOk() {
        final String nom = "nom";
        final BigDecimal prix = new BigDecimal("12.34");

        final Produit produit = Produit.builder().build();
        when(this.createProduitUseCase.execute(nom, prix)).thenReturn(produit);

        try (final MockedStatic<ProduitMapper> mapper = mockStatic(ProduitMapper.class)) {
            final ProduitResponse expected = ProduitResponse.builder().build();
            mapper.when(() -> toResponse(produit)).thenReturn(expected);

            final CreateProduitRequest request = new CreateProduitRequest(nom, prix);
            assertSame(expected, this.subject.createProduit(request));
        }
    }
}