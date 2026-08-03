# OpenAPI

## Objectif

Le projet utilise :

- OpenAPI 3.1
- SpringDoc
- Swagger UI

afin de documenter automatiquement l'API REST.

---

## Philosophie

Les annotations OpenAPI sont volontairement séparées du code métier et des contrôleurs.

Objectifs :

- préserver la lisibilité des contrôleurs ;
- centraliser la documentation ;
- faciliter la maintenance ;
- découpler documentation et implémentation.

---

## Structure

La documentation OpenAPI est regroupée dans le module :

```text
test-resource-server-web
```

Structure :

```text
web
└── openapi
    ├── api
    ├── dto
    ├── response
    ├── constants
    └── config
```

---

## Documentation des endpoints

Les annotations OpenAPI sont placées dans des interfaces dédiées.

Exemples :

```text
ProduitApi
CommandeApi
```

Puis :

```
public class ProduitController implements ProduitApi
```

```
public class CommandeController implements CommandeApi
```

Cette approche permet de conserver des contrôleurs lisibles tout en bénéficiant d'une documentation complète.

---

## Documentation des DTO

Les descriptions des DTO sont séparées dans des interfaces dédiées.

Exemples :

```text
CreateProduitRequestApi
CreateCommandeRequestApi
```

Puis :

```
public record CreateProduitRequest(...) implements CreateProduitRequestApi
```

```
public record CreateCommandeRequest(...) implements CreateCommandeRequestApi
```

---

## Documentation des réponses

Les réponses de l'API sont documentées dans :

```text
ProduitResponseApi
CommandeResponseApi
ApiErrorResponseApi
```

Responsabilités :

- descriptions des champs ;
- exemples ;
- contraintes documentaires ;
- champs obligatoires.

---

## Bean Validation

Les DTO utilisent Jakarta Bean Validation :

```
@NotBlank
@NotNull
@NotEmpty
@Email
@Size
@Digits
@Positive
```

SpringDoc enrichit automatiquement le contrat OpenAPI à partir de ces annotations.

Exemples :

```
@NotBlank
```

↓

```yaml
required
```

```
@Size(min = 3, max = 50)
```

↓

```yaml
minLength: 3
maxLength: 50
```

```
@Email
```

↓

```yaml
format: email
```

```
@NotEmpty
```

↓

```yaml
minItems: 1
```

---

## Documentation des erreurs

L'ensemble des erreurs est documenté à partir du schéma :

```text
ApiErrorResponseApi
```

Format :

```json
{
  "code": "PRODUIT_NOT_FOUND",
  "description": "Aucun produit avec le numéro P123 n'est présent dans le référentiel."
}
```

---

## Validation des requêtes

Les erreurs de validation sont interceptées par :

```text
ApiExceptionHandler
```

et retournées sous le format standard :

```json
{
  "code": "VALIDATION_ERROR",
  "description": "Le nom du produit est obligatoire."
}
```

---

## Codes d'erreur documentés

La liste des codes est générée à partir de :

```
ResourceServerErrorCode
```

Exemples :

```text
UNAUTHORIZED
FORBIDDEN

PRODUIT_NOT_FOUND
COMMANDE_NOT_FOUND

VALIDATION_ERROR
```

---

## Sécurité OAuth2

La documentation expose :

- OAuth2 ;
- le flux `client_credentials` ;
- l'endpoint de génération de token ;
- les scopes disponibles.

Exemples :

```text
trs:produit-api.read
trs:produit-api.write

trs:commande-api.read
trs:commande-api.write
```

---

## Swagger UI

Documentation interactive :

```text
http://localhost:8081/swagger-ui.html
```

Swagger UI permet :

- d'obtenir un token OAuth2 ;
- de sélectionner des scopes ;
- de tester directement les endpoints sécurisés.

---

## Génération

OpenAPI JSON :

```text
http://localhost:8081/v3/api-docs
```

OpenAPI YAML :

```text
http://localhost:8081/v3/api-docs.yaml
```

Le fichier YAML généré est également versionné dans :

```text
docs/test-resource-server-api.yaml
```

---

## Avantages de l'approche

```text
Controller
    ↓
Lisible

OpenAPI
    ↓
Centralisé

Swagger UI
    ↓
Testable

Bean Validation
    ↓
Contrat enrichi automatiquement
```

La documentation reste découplée de l'implémentation tout en étant générée automatiquement à partir du code.