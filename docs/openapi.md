# OpenAPI

## Objectif

Le projet utilise :

- OpenAPI 3
- SpringDoc
- Swagger UI

afin de documenter automatiquement l'API REST.

---

# Philosophie

Les annotations OpenAPI sont volontairement séparées du code métier.

Objectifs :

- préserver la lisibilité des contrôleurs
- centraliser la documentation
- faciliter la maintenance

---

# Structure

```text
openapi
├── api
├── dto
├── response
├── constants
└── config
```

---

# Documentation des endpoints

Les annotations OpenAPI sont placées dans des interfaces dédiées.

Exemple :

```text
ProduitApi
CommandeApi
```

Puis :

```
public class ProduitController
        implements ProduitApi
```

---

# Documentation des DTO

Les descriptions des DTO sont placées dans :

```text
CreateProduitRequestApi
CreateCommandeRequestApi
```

Exemple :

```
public record CreateProduitRequest(...)
        implements CreateProduitRequestApi
```

---

# Documentation des réponses

Les réponses sont documentées via :

```text
ProduitResponseApi
CommandeResponseApi
ApiErrorResponseApi
```

---

# Documentation des erreurs

Format :

```json
{
  "code": "PRODUIT_NOT_FOUND",
  "description": "Aucun produit avec le numéro P123 n'est présent dans le référentiel."
}
```

Schéma :

```text
ApiErrorResponseApi
```

---

# Génération

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

# OAuth2

La documentation expose :

- client_credentials
- token endpoint
- scopes documentés

Exemples :

```text
trs:produit-api.read
trs:produit-api.write

trs:commande-api.read
trs:commande-api.write
```

---

# Avantages de l'approche

```text
Controller
    ↓
Lisible

OpenAPI
    ↓
Documenté
```

La documentation est découplée de l'implémentation tout en restant générée automatiquement.