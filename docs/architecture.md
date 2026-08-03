# Architecture

## Vue d'ensemble

Le projet est construit selon une architecture hexagonale.

Objectifs :

- isoler le métier
- limiter les dépendances techniques
- faciliter les tests
- rendre le domaine indépendant de Spring
- permettre l'évolution de l'infrastructure sans impact sur le métier

---

## Architecture générale

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

---

## Structure des modules

```text
test-resource-server
├── test-resource-server-boot
├── test-resource-server-core
├── test-resource-server-web
├── test-resource-server-infrastructure
└── test-resource-server-coverage-report
```

---

## Boot

Le module Boot assemble l'application.

### Structure

```text
boot
├── TestResourceServerApplication
└── config
    ├── PersistenceConfiguration
    └── UseCaseConfiguration
```

### Responsabilités

- démarrage de l'application
- assemblage des dépendances
- déclaration des beans Spring
- liaison entre ports et adapters

---

## Core

Le module Core contient l'intégralité du métier.

Il ne dépend pas de Spring.

### Structure

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

### Domain

```text
Produit
Commande
```

### Use Cases

```text
GetProduitUseCase
CreateProduitUseCase

GetCommandeUseCase
CreateCommandeUseCase
```

### Ports

```text
ProduitRepositoryPort
CommandeRepositoryPort
NumeroRepositoryPort
```

---

## Web

Le module Web contient tout ce qui concerne l'exposition HTTP de l'application.

### Structure

```text
web
├── api
├── openapi
└── security
```

### API

```text
api
├── controller
├── dto
├── response
├── mapper
└── error
```

### Responsabilités

- contrôleurs REST
- DTO de requête
- DTO de réponse
- mappings
- gestion centralisée des erreurs

### OpenAPI

```text
openapi
├── api
├── dto
├── response
├── constants
└── config
```

### Responsabilités

- documentation OpenAPI
- Swagger UI
- OAuth2 dans Swagger
- schémas des DTO

### Security

```text
security
├── SecurityFilterChainConfig
├── SecurityPaths
├── Authorizations
├── Scopes
└── handler
```

### Responsabilités

- OAuth2 Resource Server
- JWT
- contrôle d'accès
- gestion des erreurs 401 / 403

---

## Infrastructure

Le module Infrastructure contient les implémentations techniques des ports du Core.

### Structure

```text
infrastructure
├── persistence
│   └── adapter
└── utils
```

### Persistence

```text
ProduitRepositoryAdapter
CommandeRepositoryAdapter
NumeroRepositoryAdapter
```

### Responsabilités

- implémentation des ports métier
- accès aux données
- intégrations techniques

---

## Sécurité

Le Resource Server utilise :

- OAuth2
- JWT
- JWKS

Flux :

```text
JWT
    ↓
Validation signature
    ↓
Création Authentication
    ↓
@PreAuthorize(...)
```

---

## Gestion des erreurs

### Validation

```text
Bean Validation
        ↓
VALIDATION_ERROR
        ↓
400 Bad Request
```

### Fonctionnelles

```text
ResourceServerFunctionalException
        ↓
400 Bad Request
```

### Ressource absente

```text
ResourceServerNotFoundException
        ↓
404 Not Found
```

### Techniques

```text
ResourceServerTechnicalException
        ↓
500 Internal Server Error
```

---

## Dépendances entre modules

```text
boot
 ├── web
 ├── infrastructure
 └── core

web
 └── core

infrastructure
 └── core

core
 └── aucune dépendance vers les autres modules
```

Le Core ne dépend jamais du Web ni de l'Infrastructure.