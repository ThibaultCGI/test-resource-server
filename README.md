# test-resource-server

[![Quality gate status](https://sonarcloud.io/api/project_badges/measure?project=ThibaultCGI_test-resource-server&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=ThibaultCGI_test-resource-server)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=ThibaultCGI_test-resource-server&metric=bugs)](https://sonarcloud.io/summary/new_code?id=ThibaultCGI_test-resource-server)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=ThibaultCGI_test-resource-server&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=ThibaultCGI_test-resource-server)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=ThibaultCGI_test-resource-server&metric=coverage)](https://sonarcloud.io/summary/new_code?id=ThibaultCGI_test-resource-server)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=ThibaultCGI_test-resource-server&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=ThibaultCGI_test-resource-server)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=ThibaultCGI_test-resource-server&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=ThibaultCGI_test-resource-server)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=ThibaultCGI_test-resource-server&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=ThibaultCGI_test-resource-server)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=ThibaultCGI_test-resource-server&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=ThibaultCGI_test-resource-server)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=ThibaultCGI_test-resource-server&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=ThibaultCGI_test-resource-server)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=ThibaultCGI_test-resource-server&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=ThibaultCGI_test-resource-server)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=ThibaultCGI_test-resource-server&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=ThibaultCGI_test-resource-server)

Projet de démonstration d'un OAuth2 Resource Server développé avec Spring Boot.

L'objectif principal est de fournir une API REST sécurisée permettant de tester et valider le fonctionnement de l'application d'authentification :

- auth-server

Le projet implémente :

- OAuth2 Resource Server
- JWT
- JWKS
- Contrôle d'accès par scopes OAuth2
- Bean Validation
- Gestion centralisée des erreurs
- Architecture hexagonale
- OpenAPI 3.1
- Swagger UI

---

## Fonctionnalités

### Produits

- Consultation d'un produit
- Création d'un produit

### Commandes

- Consultation d'une commande
- Création d'une commande

### Sécurité

- Authentification OAuth2
- Validation des JWT
- Validation des signatures via JWKS
- Contrôle des scopes OAuth2
- Contrôle d'accès via `@PreAuthorize`

### Validation

- Bean Validation (Jakarta Validation)
- Validation métier
- Réponses d'erreur homogènes

### Documentation

- OpenAPI 3.1
- Swagger UI
- Documentation des endpoints
- Documentation des DTO
- Documentation des réponses métier
- Documentation des réponses d'erreur
- Documentation OAuth2

---

## Architecture

Le projet est développé selon une architecture hexagonale.

### Architecture générale

```text
                ┌─────────────────┐
                │      Web        │
                └────────┬────────┘
                         │
                         ▼
                ┌─────────────────┐
                │    Use Case     │
                └────────┬────────┘
                         │
                         ▼
                ┌─────────────────┐
                │      Port       │
                └────────┬────────┘
                         │
                         ▼
                ┌─────────────────┐
                │    Adapter      │
                └─────────────────┘
```

### Structure des modules

```text
test-resource-server
├── test-resource-server-boot
├── test-resource-server-core
├── test-resource-server-web
├── test-resource-server-infrastructure
└── test-resource-server-coverage-report
```

### Boot

Assemblage de l'application.

```text
boot
├── TestResourceServerApplication
└── config
    ├── PersistenceConfiguration
    └── UseCaseConfiguration
```

### Core

Contient le métier et ne dépend pas de Spring.

```text
core
├── constants
├── domain
├── exception
├── generator
├── port
├── usecase
└── utils
```

### Web

Contient tout ce qui concerne l'exposition HTTP.

```text
web
├── api
├── openapi
└── security
```

### Infrastructure

Contient les implémentations techniques des ports.

```text
infrastructure
├── persistence
│   └── adapter
└── utils
```

---

## OAuth2

Le projet agit comme un OAuth2 Resource Server.

Les JWT sont émis par :

```text
auth-server
```

Le Resource Server récupère automatiquement les clés publiques via :

```text
/oauth2/jwks
```

afin de valider :

- la signature ;
- l'expiration ;
- les scopes OAuth2.

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

---

## OpenAPI

### Swagger UI

```text
http://localhost:8081/swagger-ui.html
```

### OpenAPI JSON

```text
http://localhost:8081/v3/api-docs
```

### OpenAPI YAML

```text
http://localhost:8081/v3/api-docs.yaml
```

La documentation expose :

- OAuth2
- client_credentials
- scopes
- DTO
- réponses métier
- réponses d'erreur

---

## Validation

Le projet utilise Jakarta Bean Validation :

```
@NotBlank
@NotNull
@NotEmpty
@Email
@Size
@Digits
@Positive
```

Les erreurs de validation sont retournées sous un format homogène :

```json
{
  "code": "VALIDATION_ERROR",
  "description": "Le nom du produit est obligatoire."
}
```

---

## Endpoints

### Produits

#### Consulter un produit

```http
GET /api/v1/produits/{numero}
```

#### Créer un produit

```http
POST /api/v1/produits
```

### Commandes

#### Consulter une commande

```http
GET /api/v1/commandes/{numero}
```

#### Créer une commande

```http
POST /api/v1/commandes
```

---

## Gestion des erreurs

Format standard :

```json
{
  "code": "PRODUIT_NOT_FOUND",
  "description": "Aucun produit avec le numéro P123 n'est présent dans le référentiel."
}
```

### Codes HTTP

| Code  | Description                        |
|-------|------------------------------------|
| 400   | Erreur fonctionnelle ou validation |
| 401   | Authentification requise           |
| 403   | Accès refusé                       |
| 404   | Ressource inexistante              |
| 500   | Erreur technique                   |

---

## Exécution locale

### Authorization Server

```text
http://localhost:8080
```

### Resource Server

```text
http://localhost:8081
```

---

## Tests

Le projet contient :

- Tests unitaires des Use Cases
- Tests unitaires des Controllers
- Tests unitaires des Mappers
- Tests unitaires des ValidationUtils
- Tests unitaires des ExceptionHandlers
- Tests unitaires des Security Handlers
- Tests unitaires des Generators

Rapport :

```text
test-resource-server-coverage-report
```

---

## Documentation

Documentation technique :

```text
docs/
├── architecture.md
├── oauth2.md
├── openapi.md
└── test-resource-server-api.yaml
```

---

## Objectif du projet

Le projet permet :

- de tester auth-server ;
- de tester OAuth2 ;
- de tester JWT ;
- de tester JWKS ;
- de tester les scopes OAuth2 ;
- de tester Swagger UI avec OAuth2 ;
- de démontrer une architecture hexagonale avec Spring Boot ;
- de démontrer l'intégration OpenAPI / Bean Validation.
