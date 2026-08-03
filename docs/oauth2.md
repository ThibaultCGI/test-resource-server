# OAuth2

## Objectif

Le projet agit comme un OAuth2 Resource Server.

Il consomme les JWT émis par :

```text
auth-server
```

et protège les ressources exposées via l'API REST.

---

## Architecture OAuth2

```text
                 ┌──────────────┐
                 │    Client    │
                 └──────┬───────┘
                        │
                        │ OAuth2
                        ▼
                 ┌──────────────┐
                 │ Auth Server  │
                 └──────┬───────┘
                        │
                        │ JWT
                        ▼
                 ┌──────────────┐
                 │ Resource     │
                 │ Server       │
                 └──────────────┘
```

---

## Authentification

Le projet utilise :

- OAuth2 Resource Server
- JWT

La sécurité est implémentée dans :

```text
web
└── security
    └── SecurityFilterChainConfig
```

L'ensemble des endpoints de l'application est protégé par défaut.

Seuls les endpoints techniques sont accessibles sans authentification :

```text
/actuator/**
/swagger-ui/**
/swagger-ui.html
/v3/api-docs/**
```

---

## Validation des JWT

Configuration :

```
spring.security.oauth2.resourceserver.jwt.jwk-set-uri
```

Le Resource Server :

- récupère les clés publiques du serveur d'autorisation ;
- valide la signature du JWT ;
- valide les dates d'expiration ;
- construit un objet `Authentication` ;
- alimente le `SecurityContext`.

---

## JWKS

Le Resource Server valide les JWT à l'aide de l'endpoint :

```text
/oauth2/jwks
```

exposé par :

```text
auth-server
```

Ce endpoint fournit les clés publiques nécessaires à la vérification des signatures.

---

## Scopes

### Produits

Lecture :

```text
trs:produit-api.read
```

Écriture :

```text
trs:produit-api.write
```

### Commandes

Lecture :

```text
trs:commande-api.read
```

Écriture :

```text
trs:commande-api.write
```

Les scopes sont centralisés dans :

```text
web
└── security
    └── Scopes
```

---

## Contrôle d'accès

Le contrôle d'accès est assuré via :

```
@PreAuthorize(...)
```

Les expressions de sécurité sont centralisées dans :

```text
web
└── security
    └── Authorizations
```

Exemples :

```
@PreAuthorize(CAN_READ_PRODUCT)

@PreAuthorize(CAN_WRITE_PRODUCT)

@PreAuthorize(CAN_READ_COMMANDE)

@PreAuthorize(CAN_WRITE_COMMANDE)
```

---

## Gestion des erreurs de sécurité

### 401 Unauthorized

Retourné lorsque :

- aucun JWT n'est fourni ;
- le JWT est invalide ;
- le JWT est expiré.

Exemple :

```json
{
  "code": "UNAUTHORIZED",
  "description": "Authentification requise."
}
```

Implémentation :

```text
ApiAuthenticationEntryPoint
```

---

### 403 Forbidden

Retourné lorsque :

- le JWT est valide ;
- l'utilisateur ne possède pas le scope requis.

Exemple :

```json
{
  "code": "FORBIDDEN",
  "description": "Vous n'êtes pas autorisé à accéder à la ressource."
}
```

Implémentation :

```text
ApiAccessDeniedHandler
```

---

## OAuth2 dans Swagger UI

Swagger UI est configuré pour utiliser OAuth2.

Flux supporté :

```text
client_credentials
```

L'utilisateur peut :

- obtenir un token directement depuis Swagger UI ;
- sélectionner les scopes souhaités ;
- tester les endpoints sécurisés.

---

## CORS

Swagger UI est exécuté depuis :

```text
http://localhost:8081
```

et obtient ses tokens auprès de :

```text
http://localhost:8080
```

Une configuration CORS est donc nécessaire côté Authorization Server.

Les requêtes préflight `OPTIONS` sont traitées avant les contrôles de sécurité afin de permettre l'obtention des tokens OAuth2 depuis Swagger UI.

---

## OpenAPI

Swagger UI :

```text
http://localhost:8081/swagger-ui.html
```

OpenAPI JSON :

```text
http://localhost:8081/v3/api-docs
```

OpenAPI YAML :

```text
http://localhost:8081/v3/api-docs.yaml
```

Le schéma OpenAPI documente :

- OAuth2 ;
- le flux `client_credentials` ;
- les scopes disponibles ;
- les réponses HTTP ;
- les codes d'erreur applicatifs.

---

## Tests avec Swagger UI

### Obtention d'un token

Cliquer sur :

```text
Authorize
```

Puis renseigner :

```text
Client Id
Client Secret
Scopes
```

Swagger UI récupère alors automatiquement un JWT auprès de :

```text
http://localhost:8080/oauth2/token
```

---

### Consultation d'un produit

Exemple :

```http
GET /api/v1/produits/P00
```

Scope requis :

```text
trs:produit-api.read
```

---

### Création d'un produit

Exemple :

```http
POST /api/v1/produits
```

Scope requis :

```text
trs:produit-api.write
```

Un token ne possédant qu'un scope de lecture provoquera :

```http
403 Forbidden
```

---

## Applications concernées

### Authorization Server

```text
auth-server
```

Responsabilités :

- authentification des clients ;
- génération des JWT ;
- exposition de l'endpoint JWKS ;
- gestion des scopes OAuth2.

### Resource Server

```text
test-resource-server
```

Responsabilités :

- validation des JWT ;
- contrôle des autorisations ;
- protection des ressources métier ;
- exposition de l'API REST.