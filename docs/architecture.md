# Architecture

## Vue d'ensemble

Le projet `test-resource-server` est développé selon une architecture hexagonale.

L'objectif est de séparer :

- le métier
- les cas d'utilisation
- les dépendances techniques
- l'exposition REST
- la sécurité OAuth2

afin de préserver l'indépendance du domaine métier.

---

# Architecture générale

```text
                    ┌─────────────────────┐
                    │     Controllers     │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │      Use Cases      │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │        Ports        │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │      Adapters       │
                    └─────────────────────┘
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

# Module Core

Le module Core contient l'intégralité du métier.

Le module ne dépend pas de Spring.

## Contenu

```text
core
├── domain
├── usecase
├── port
├── exception
├── constants
├── utils
└── generator
```

### Domain

Contient les objets métier :

```text
Produit
Commande
```

---

### Use Cases

Contient les cas d'utilisation :

```text
GetProduitUseCase
CreateProduitUseCase

GetCommandeUseCase
CreateCommandeUseCase
```

---

### Ports

Définissent les contrats utilisés par le métier.

Exemples :

```text
ProduitRepositoryPort
CommandeRepositoryPort
NumeroRepositoryPort
```

---

### Generator

Contient les composants métier réutilisables.

Exemple :

```text
NumeroGenerator
```

---

### Validation

Les règles de validation sont regroupées dans :

```text
ProduitValidationUtils
CommandeValidationUtils
```

---

# Module Infrastructure

Le module Infrastructure contient tous les éléments techniques.

## Contenu

```text
infrastructure
├── api
├── config
├── persistence
├── security
└── utils
```

---

## API

Exposition REST.

### Controllers

```text
ProduitController
CommandeController
```

### DTO

```text
CreateProduitRequest
CreateCommandeRequest
```

### Response

```text
ProduitResponse
CommandeResponse
```

### Mapper

```text
ProduitMapper
CommandeMapper
```

---

## Persistence

Implémentations des ports du Core.

```text
ProduitRepositoryAdapter
CommandeRepositoryAdapter
NumeroRepositoryAdapter
```

Ces adapters simulent un comportement de persistance afin de tester OAuth2 sans dépendre d'une base de données.

---

## Security

Configuration du Resource Server.

```text
SecurityFilterChainConfig
Scopes
```

Responsabilités :

- validation des JWT
- récupération des clés publiques
- authentification
- contrôle d'accès basé sur les scopes

---

# Module Boot

Contient le point d'entrée Spring Boot.

```text
TestResourceServerApplication
```

---

# Gestion des erreurs

## Exceptions métier

```text
ResourceServerFunctionalException
```

Retour :

```http
400 Bad Request
```

---

## Ressources inexistantes

```text
ResourceServerNotFoundException
```

Retour :

```http
404 Not Found
```

---

## Exceptions techniques

```text
ResourceServerTechnicalException
```

Retour :

```http
500 Internal Server Error
```

---

# Principes de test

Le projet applique les règles suivantes :

## Une responsabilité = un test

```text
Une méthode
    ↓
Un comportement
    ↓
Un test dédié
```

---

## Méthodes composites

Les méthodes composites testent uniquement leur orchestration.

Exemple :

```text
normalizeAndValidateEmailClient()
```

vérifie :

```text
appel normalizeEmailClient()
appel validateEmailClient()
```

sans retester leur logique interne.

---

## Use Cases

Les dépendances sont mockées.

Les méthodes internes déjà testées sont spyées.

Exemple :

```java
@Spy
CreateCommandeUseCase
```

---

## Controllers

Les UseCases sont mockés.

Les Mappers statiques sont mockés.

Le contrôleur est testé uniquement sur sa responsabilité.

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
