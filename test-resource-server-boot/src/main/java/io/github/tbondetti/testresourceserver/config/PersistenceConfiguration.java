package io.github.tbondetti.testresourceserver.config;

import io.github.tbondetti.testresourceserver.core.port.CommandeRepositoryPort;
import io.github.tbondetti.testresourceserver.core.port.NumeroRepositoryPort;
import io.github.tbondetti.testresourceserver.core.port.ProduitRepositoryPort;
import io.github.tbondetti.testresourceserver.infrastructure.persistence.adapter.CommandeRepositoryAdapter;
import io.github.tbondetti.testresourceserver.infrastructure.persistence.adapter.NumeroRepositoryAdapter;
import io.github.tbondetti.testresourceserver.infrastructure.persistence.adapter.ProduitRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfiguration {

    @Bean
    ProduitRepositoryPort produitRepositoryPort() {
        return new ProduitRepositoryAdapter();
    }

    @Bean
    CommandeRepositoryPort commandeRepositoryPort() {
        return new CommandeRepositoryAdapter();
    }

    @Bean
    NumeroRepositoryPort numeroRepositoryPort() {
        return new NumeroRepositoryAdapter();
    }
}
