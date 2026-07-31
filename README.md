# test-resource-server

Projet de démonstration d'un OAuth2 Resource Server développé avec Spring Boot.

L'objectif principal est de fournir une API REST sécurisée permettant de tester et valider le fonctionnement de l'application d'authentification :

- auth-server

Le projet implémente :

- OAuth2 Resource Server
- JWT
- JWKS
- Contrôle d'accès par scopes
- Architecture hexagonale
- OpenAPI 3
- Swagger UI

---

# Fonctionnalités

## Produits

- Consultation d'un produit
- Création d'un produit

## Commandes

- Consultation d'une commande
- Création d'une commande

## Sécurité

- Authentification OAuth2
- Validation des JWT
- Validation des signatures via JWKS
- Autorisation basée sur les scopes OAuth2
- Contrôle d'accès via `@PreAuthorize`

## Documentation

- OpenAPI 3
- Swagger UI
- Documentation des DTO
- Documentation des réponses métier
- Documentation des réponses d'erreur

---

# Architecture

Le projet est développé selon une architecture hexagonale.

```text
Controller
    ↓
UseCase
    ↓
Port
    ↓
Adapter
```

Structure :

```text
test-resource-server
├── test-resource-server-boot
├── test-resource-server-core
├── test-resource-server-infrastructure
└── test-resource-server-coverage-report
```

---

# OAuth2

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

- signature
- expiration
- scopes

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

# OpenAPI

Swagger UI :

```text
http://localhost:8081/swagger-ui.html
```

Description OpenAPI JSON :

```text
http://localhost:8081/v3/api-docs
```

Description OpenAPI YAML :

```text
http://localhost:8081/v3/api-docs.yaml
```

---

# Endpoints

## Produits

### Consulter un produit

```http
GET /api/v1/produits/{numero}
```

### Créer un produit

```http
POST /api/v1/produits
```

---

## Commandes

### Consulter une commande

```http
GET /api/v1/commandes/{numero}
```

### Créer une commande

```http
POST /api/v1/commandes
```

---

# Gestion des erreurs

Format standard :

```json
{
  "code": "PRODUIT_NOT_FOUND",
  "description": "Aucun produit avec le numéro P123 n'est présent dans le référentiel."
}
```

Codes HTTP :

| Code | Description              |
|------|--------------------------|
| 400  | Erreur fonctionnelle     |
| 401  | Authentification requise |
| 403  | Accès refusé             |
| 404  | Ressource inexistante    |
| 500  | Erreur technique         |

---

# Exécution locale

## Auth Server

```text
http://localhost:8080
```

## Resource Server

```text
http://localhost:8081
```

---

# Tests

Le projet contient :

- Tests unitaires des UseCases
- Tests unitaires des Controllers
- Tests unitaires des Mappers
- Tests unitaires des ValidationUtils
- Tests unitaires des Generators

Rapport :

```text
test-resource-server-coverage-report
```

---

# Objectif du projet

Le projet permet :

- de tester auth-server
- de tester OAuth2
- de tester JWT
- de tester JWKS
- de tester les scopes OAuth2
- de démontrer une architecture hexagonale avec Spring Boot