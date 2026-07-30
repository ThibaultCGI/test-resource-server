# OAuth2

## Objectif

Le projet `test-resource-server` agit comme un OAuth2 Resource Server.

Il consomme les JWT émis par :

```text
auth-server
```

afin de protéger les endpoints REST.

---

# Architecture OAuth2

```text
                   ┌────────────────────┐
                   │       Client       │
                   └─────────┬──────────┘
                             │
                             │ Client Credentials
                             │
                             ▼
                   ┌────────────────────┐
                   │    Auth Server     │
                   └─────────┬──────────┘
                             │
                             │ JWT
                             │
                             ▼
                   ┌────────────────────┐
                   │ Resource Server    │
                   └────────────────────┘
```

---

# Authentification

Le projet utilise :

```text
OAuth2 Resource Server
```

avec :

```text
JWT
```

---

# Validation des JWT

Configuration :

```properties
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=${auth-server.path}/oauth2/jwks
```

Au démarrage :

```text
Resource Server
    ↓
Télécharge les clés publiques
    ↓
Construit un JwtDecoder
```

Lors d'une requête :

```text
JWT reçu
    ↓
Signature vérifiée
    ↓
Expiration vérifiée
    ↓
Authentification créée
```

---

# JWKS

Endpoint exposé par l'Authorization Server :

```text
/oauth2/jwks
```

Le Resource Server utilise ce point d'entrée pour récupérer les clés publiques permettant de valider les JWT.

---

# SecurityFilterChain

```java
@Bean
SecurityFilterChain securityFilterChain(
        final HttpSecurity http
) throws Exception {

    return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
            .build();
}
```

---

# Authentification

```java
.authorizeHttpRequests(auth ->
        auth.anyRequest().authenticated()
)
```

Signifie :

```text
Chaque requête doit fournir un JWT valide.
```

---

# Resource Server

```java
.oauth2ResourceServer(
        oauth2 -> oauth2.jwt(Customizer.withDefaults())
)
```

Active :

```text
Lecture du JWT
Validation du JWT
Validation de la signature
Validation de l'expiration
Création du SecurityContext
```

---

# Scopes

## Produits

Lecture :

```text
trs:produit-api.read
```

Écriture :

```text
trs:produit-api.write
```

---

## Commandes

Lecture :

```text
trs:commande-api.read
```

Écriture :

```text
trs:commande-api.write
```

---

# Contrôle d'accès

Le contrôle d'accès s'effectue via :

```java
@PreAuthorize(...)
```

Exemple :

```java
@PreAuthorize(
    "hasAuthority('SCOPE_trs:produit-api.write')"
)
```

---

# Correspondance des scopes

JWT :

```text
trs:produit-api.read
```

↓

Authority Spring Security :

```text
SCOPE_trs:produit-api.read
```

---

# Produits

## Lecture

```http
GET /api/v1/produits/{numero}
```

Scopes autorisés :

```text
trs:produit-api.read
trs:produit-api.write
```

---

## Création

```http
POST /api/v1/produits
```

Scope requis :

```text
trs:produit-api.write
```

---

# Commandes

## Lecture

```http
GET /api/v1/commandes/{numero}
```

Scopes autorisés :

```text
trs:commande-api.read
trs:commande-api.write
```

---

## Création

```http
POST /api/v1/commandes
```

Scope requis :

```text
trs:commande-api.write
```

---

# Réponses HTTP

## 401

JWT absent ou invalide.

```http
401 Unauthorized
```

---

## 403

JWT valide mais scope insuffisant.

```http
403 Forbidden
```

---

## 404

Ressource inexistante.

```http
404 Not Found
```

---

# Test avec Bruno

## Obtention d'un token

```http
POST http://localhost:8080/oauth2/token
```

Grant :

```text
client_credentials
```

Scope :

```text
trs:produit-api.read
```

Authentification :

```text
Basic Auth Header
```

---

## Appel d'une ressource

```http
GET http://localhost:8081/api/v1/produits/test
```

Header :

```http
Authorization: Bearer <access_token>
```

---

# Projet associé

Authorization Server :

```text
auth-server
```

Resource Server :

```text
test-resource-server
```

Les deux projets sont conçus pour être exécutés ensemble afin de démontrer un flux OAuth2 complet.