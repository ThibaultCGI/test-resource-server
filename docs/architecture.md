# Architecture

## Vue d'ensemble

Le projet est construit selon une architecture hexagonale.

Objectifs :

- isoler le métier
- limiter les dépendances techniques
- faciliter les tests
- rendre le domaine indépendant de Spring

---

# Architecture générale

```text
                ┌─────────────────┐
                │   Controller    │
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

# Structure des modules

```text
test-resource-server
├── test-resource-server-boot
├── test-resource-server-core
├── test-resource-server-infrastructure
└── test-resource-server-coverage-report
```

---

# Core

Le Core contient l'intégralité du métier.

Il ne dépend pas de Spring.

## Contenu

```text
core
├── domain
├── usecase
├── port
├── exception
├── constants
├── generator
└── utils
```

### Domain

```text
Produit
Commande
```

### UseCases

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

# Infrastructure

Le module Infrastructure contient tous les éléments techniques.

## API

```text
api
├── controller
├── dto
├── response
├── mapper
└── error
```

### Controllers

```text
ProduitController
CommandeController
```

---

# OpenAPI

La documentation OpenAPI est volontairement séparée de l'implémentation REST.

Structure :

```text
openapi
├── api
├── dto
├── response
├── constants
└── config
```

---

## api

Interfaces de documentation des endpoints.

```text
ProduitApi
CommandeApi
```

Responsabilités :

- Tags
- Operations
- SecurityRequirement
- ApiResponse

---

## dto

Documentation des DTO.

```text
CreateProduitRequestApi
CreateCommandeRequestApi
```

Responsabilités :

- descriptions
- exemples
- contraintes documentaires

---

## response

Documentation des réponses.

```text
ProduitResponseApi
CommandeResponseApi
ApiErrorResponseApi
```

Responsabilités :

- descriptions
- exemples
- champs requis

---

## constants

Constantes de documentation OpenAPI.

```text
OpenApiConstants
ProduitOpenApiConstants
CommandeOpenApiConstants
```

---

## config

Configuration OpenAPI.

```text
OpenApiConfiguration
```

Responsabilités :

- OAuth2
- Scopes
- Swagger UI
- Description générale de l'API

---

# Sécurité

Le Resource Server utilise :

```text
OAuth2
JWT
JWKS
```

Validation :

```text
JWT
    ↓
JWKS
    ↓
Authentication
    ↓
@PreAuthorize
```

---

# Gestion des erreurs

## Fonctionnelles

```text
ResourceServerFunctionalException
```

↓

```http
400
```

---

## Ressource absente

```text
ResourceServerNotFoundException
```

↓

```http
404
```

---

## Techniques

```text
ResourceServerTechnicalException
```

↓

```http
500
```

---

# Dépendances autorisées

```text
Controller
    ↓
UseCase
    ↓
Port
    ↓
Adapter
```

Le Core ne dépend jamais de l'Infrastructure.