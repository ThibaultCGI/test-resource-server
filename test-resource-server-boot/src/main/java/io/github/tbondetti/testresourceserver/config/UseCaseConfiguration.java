package io.github.tbondetti.testresourceserver.config;

import io.github.tbondetti.testresourceserver.core.generator.NumeroGenerator;
import io.github.tbondetti.testresourceserver.core.port.CommandeRepositoryPort;
import io.github.tbondetti.testresourceserver.core.port.NumeroRepositoryPort;
import io.github.tbondetti.testresourceserver.core.port.ProduitRepositoryPort;
import io.github.tbondetti.testresourceserver.core.usecase.commande.CreateCommandeUseCase;
import io.github.tbondetti.testresourceserver.core.usecase.commande.GetCommandeUseCase;
import io.github.tbondetti.testresourceserver.core.usecase.produit.CreateProduitUseCase;
import io.github.tbondetti.testresourceserver.core.usecase.produit.GetProduitUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    NumeroGenerator numeroGenerator(final NumeroRepositoryPort numeroRepositoryPort) {
        return new NumeroGenerator(numeroRepositoryPort);
    }

    @Bean
    GetProduitUseCase getProduitUseCase(final ProduitRepositoryPort produitRepositoryPort) {
        return new GetProduitUseCase(produitRepositoryPort);
    }

    @Bean
    CreateProduitUseCase createProduitUseCase(
            final ProduitRepositoryPort produitRepositoryPort,
            final NumeroGenerator numeroGenerator
    ) {
        return new CreateProduitUseCase(produitRepositoryPort, numeroGenerator);
    }

    @Bean
    GetCommandeUseCase getCommandeUseCase(final CommandeRepositoryPort commandeRepositoryPort) {
        return new GetCommandeUseCase(commandeRepositoryPort);
    }

    @Bean
    CreateCommandeUseCase createCommandeUseCase(
            final CommandeRepositoryPort commandeRepositoryPort,
            final ProduitRepositoryPort produitRepositoryPort,
            final NumeroGenerator numeroGenerator
            ) {
        return new CreateCommandeUseCase(commandeRepositoryPort, produitRepositoryPort, numeroGenerator);
    }

}
