# OAuth2

## Objectif

Le projet agit comme un OAuth2 Resource Server.

Il consomme les JWT émis par :

```text
auth-server
```

et protège les ressources exposées via l'API REST.

---

# Architecture OAuth2

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
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=${auth-server.oauth2.jwks-path}
```

Le Resource Server :

1. télécharge les clés publiques
2. valide la signature
3. valide l'expiration
4. construit le SecurityContext

---

# JWKS

Endpoint utilisé :

```text
/oauth2/jwks
```

Permet la récupération des clés publiques utilisées pour vérifier les JWT.

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

Le contrôle d'accès est effectué via :

```
@PreAuthorize(...)
```

Exemple :

```
@PreAuthorize(CAN_WRITE_PRODUCT)
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

## 500

Erreur technique.

```http
500 Internal Server Error
```

---

# OpenAPI

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

---

# OAuth2 dans Swagger

La configuration OpenAPI expose :

- OAuth2
- client_credentials
- les scopes disponibles

L'utilisateur peut consulter la documentation complète de l'API et de son modèle de sécurité depuis Swagger UI.

---

# Tests avec Bruno

## Obtention d'un token

```http
POST http://localhost:8080/oauth2/token
```

Grant :

```text
client_credentials
```

Authentification :

```text
Basic Auth Header
```

---

## Consommation d'une ressource

```http
GET /api/v1/produits/P00001
```

Header :

```http
Authorization: Bearer <token>
```

---

# Applications concernées

Authorization Server :

```text
auth-server
```

Resource Server :

```text
test-resource-server
```