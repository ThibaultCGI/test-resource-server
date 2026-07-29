package io.github.tbondetti.testresourceserver.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity // permet l'utilisation de @PreAuthorize
@Configuration
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    @SuppressWarnings("java:S4502")
    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) {

        return http
                .csrf(AbstractHttpConfigurer::disable)

                // Toute requête HTTP doit être authentifiée (i.e. avec un token valide)
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())

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
                .build();
    }

}
