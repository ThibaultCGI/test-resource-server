package io.github.tbondetti.testresourceserver.web.openapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.github.tbondetti.testresourceserver.web.openapi.constants.CommandeOpenApiConstants.COMMANDE_READ_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.CommandeOpenApiConstants.COMMANDE_WRITE_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.OpenApiConstants.API_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.OpenApiConstants.API_TITLE;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.OpenApiConstants.API_VERSION;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.OpenApiConstants.CONTACT_NAME;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.OpenApiConstants.SECURITY_SCHEME_NAME;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.ProduitOpenApiConstants.PRODUIT_READ_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.web.openapi.constants.ProduitOpenApiConstants.PRODUIT_WRITE_DESCRIPTION;
import static io.github.tbondetti.testresourceserver.web.security.Scopes.COMMANDE_READ;
import static io.github.tbondetti.testresourceserver.web.security.Scopes.COMMANDE_WRITE;
import static io.github.tbondetti.testresourceserver.web.security.Scopes.PRODUIT_READ;
import static io.github.tbondetti.testresourceserver.web.security.Scopes.PRODUIT_WRITE;
import static io.swagger.v3.oas.models.security.SecurityScheme.Type.OAUTH2;

@Configuration
public class OpenApiConfiguration {

    @Value("${auth-server.oauth2.token-path}")
    private String tokenUrl;

    private Scopes scopes() {
        return new Scopes()
                .addString(PRODUIT_READ, PRODUIT_READ_DESCRIPTION)
                .addString(PRODUIT_WRITE, PRODUIT_WRITE_DESCRIPTION)
                .addString(COMMANDE_READ, COMMANDE_READ_DESCRIPTION)
                .addString(COMMANDE_WRITE, COMMANDE_WRITE_DESCRIPTION)
        ;
    }

    private OAuthFlow oauthFlow() {
        return new OAuthFlow()
                .tokenUrl(this.tokenUrl)
                .scopes(this.scopes())
                ;
    }

    private OAuthFlows oauthFlows() {
        return new OAuthFlows()
                .clientCredentials(this.oauthFlow())
                ;
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name(SECURITY_SCHEME_NAME)
                .type(OAUTH2)
                .flows(this.oauthFlows())
                ;
    }

    private Contact contact() {
        return new Contact()
                .name(CONTACT_NAME)
                ;
    }

    private Info info() {
        return new Info()
                .title(API_TITLE)
                .description(API_DESCRIPTION)
                .version(API_VERSION)
                .contact(this.contact())
                ;
    }

    private Components components() {
        return new Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME, this.securityScheme())
                ;
    }

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .info(this.info())
                .components(this.components())
                ;
    }
}