package io.github.tbondetti.testresourceserver.web.security;

import io.github.tbondetti.testresourceserver.web.security.handler.ApiAccessDeniedHandler;
import io.github.tbondetti.testresourceserver.web.security.handler.ApiAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import tools.jackson.databind.ObjectMapper;

import static io.github.tbondetti.testresourceserver.web.security.SecurityPaths.ACTUATOR_ALL;
import static io.github.tbondetti.testresourceserver.web.security.SecurityPaths.OPENAPI_DOCS_ALL;
import static io.github.tbondetti.testresourceserver.web.security.SecurityPaths.OPENAPI_DOCS_YAML;
import static io.github.tbondetti.testresourceserver.web.security.SecurityPaths.SWAGGER_UI_ALL;
import static io.github.tbondetti.testresourceserver.web.security.SecurityPaths.SWAGGER_UI_HTML;

@EnableMethodSecurity // permet l'utilisation de @PreAuthorize
@Configuration
@RequiredArgsConstructor
public class SecurityFilterChainConfiguration {

    private final ObjectMapper objectMapper;

    @SuppressWarnings("java:S4502")
    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) {

        return http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth

                        // Pas d'authentification requise pour les endpoint suivants
                        .requestMatchers(
                                ACTUATOR_ALL,
                                SWAGGER_UI_ALL,
                                SWAGGER_UI_HTML,
                                OPENAPI_DOCS_ALL,
                                OPENAPI_DOCS_YAML
                        ).permitAll()

                        // Toute autre requête HTTP doit être authentifiée (i.e. avec un token valide)
                        .anyRequest().authenticated())

                /*
                 Permet d'activer le mode "OAuth2 Resource Server"
                 i.e.
                 déclenche automatiquement la lecture / validation du token,
                 la vérification de la signature / de l'expiration du token
                 et construit un objet Authentification dans le SecurityContext

                 Customizer.withDefaults() → Configure le JWT Resource Server avec la configuration standard.
                 i.e.
                 va checher un spring.security.oauth2.resourceserver.jwt.jwk-set-uri dans le fichier de configuration
                 pour récupérer la clé publique du serveur d'autorisation et construire un JwtDecoder
                */
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))

                /*
                Pour custom les retours 401 et 403
                */
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new ApiAuthenticationEntryPoint(this.objectMapper))
                        .accessDeniedHandler(new ApiAccessDeniedHandler(this.objectMapper))
                )
                .build();
    }
}
