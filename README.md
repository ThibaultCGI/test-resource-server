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

Projet de simulation d'un **OAuth2 Resource Server** développé avec **Spring Boot**.

L'objectif principal de ce projet est de servir de consommateur d'API sécurisé afin de tester l'application :

- auth-server

Le projet implémente :

- Authentication OAuth2 via JWT
- Validation des JWT via JWKS
- Contrôle d'accès basé sur les scopes OAuth2
- Architecture hexagonale
- API REST de démonstration pour la gestion de produits et de commandes

---

# Fonctionnalités

## Produits

- Création d'un produit
- Consultation d'un produit

## Commandes

- Création d'une commande
- Consultation d'une commande

## Sécurité

- OAuth2 Resource Server
- JWT signé par auth-server
- Validation automatique via JWKS
- Authentification obligatoire sur tous les endpoints
- Autorisation par scopes via `@PreAuthorize`

---

# Architecture

Le projet est organisé selon une architecture hexagonale.

```text
Controller
    ↓
Use Case
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

## Core

Contient :

- Domain
- UseCases
- Ports
- Exceptions
- Validation métier

Le module ne dépend pas de Spring.

## Infrastructure

Contient :

- Controllers REST
- DTO
- Response
- Mappers
- Configurations Spring
- Adapters de persistence
- Sécurité OAuth2

## Boot

Point d'entrée Spring Boot.

---

# Authentification

Le projet est configuré comme un OAuth2 Resource Server.

Configuration :

```properties
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=${auth-server.path}/oauth2/jwks
```

Au démarrage, Spring Security récupère automatiquement les clés publiques exposées par l'Authorization Server afin de valider les JWT reçus.

---

# Scopes

## Produits

Lecture :

```text
trs:product-api.read
```

Écriture :

```text
trs:product-api.write
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

# Endpoints

## Produits

### Consultation d'un produit

```http
GET /api/v1/produits/{numero}
```

Scopes requis :

```text
trs:product-api.read
```

ou

```text
trs:product-api.write
```

### Création d'un produit

```http
POST /api/v1/produits
```

Payload :

```json
{
  "nom": "Produit de test",
  "prix": 12.34
}
```

Scope requis :

```text
trs:product-api.write
```

---

## Commandes

### Consultation d'une commande

```http
GET /api/v1/commandes/{numero}
```

Scopes requis :

```text
trs:commande-api.read
```

ou

```text
trs:commande-api.write
```

### Création d'une commande

```http
POST /api/v1/commandes
```

Payload :

```json
{
  "emailClient": "jean.martin@gmail.com",
  "numerosProduits": [
    "P00001",
    "P00002"
  ]
}
```

Scope requis :

```text
trs:commande-api.write
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

## Codes HTTP utilisés

### 400

Erreur fonctionnelle :

```text
PRODUIT_NOM_REQUIRED
COMMANDE_EMAIL_CLIENT_INVALID
PRODUIT_PRIX_MUST_BE_POSITIVE
...
```

### 401

Utilisateur non authentifié.

### 403

Utilisateur authentifié mais non autorisé.

### 404

Ressource inexistante :

```text
PRODUIT_NOT_FOUND
COMMANDE_NOT_FOUND
```

### 500

Erreur technique.

---

# Obtention d'un token

Exemple avec le grant :

```text
client_credentials
```

```http
POST /oauth2/token
```

Body :

```text
grant_type=client_credentials
scope=trs:product-api.read
```

Authentification client :

```http
Authorization: Basic base64(clientId:clientSecret)
```

---

# Exécution locale

## Auth Server

Démarrer :

```text
auth-server
```

par défaut :

```text
http://localhost:8080
```

## Resource Server

Démarrer :

```text
test-resource-server
```

par défaut :

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

Rapport de couverture :

```text
test-resource-server-coverage-report
```

---

# Objectif du projet

Ce projet est un support de démonstration OAuth2 permettant :

- de tester auth-server
- de tester les scopes OAuth2
- de tester les JWT
- de tester la validation JWKS
- de démontrer une architecture hexagonale avec Spring Boot