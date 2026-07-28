# Test Resource Server

Projet Spring Boot servant de **Resource Server OAuth2** pour valider le bon fonctionnement du projet **auth-server**.

L'objectif de cette application est de :

- valider les JWT émis par `auth-server`
- récupérer automatiquement les clés publiques depuis le endpoint JWKS
- exposer des endpoints protégés par des scopes OAuth2
- vérifier le fonctionnement de bout en bout de l'écosystème OAuth2

---

# Architecture

```text
┌─────────────────────┐
│     auth-server     │
│ AuthorizationServer │
└──────────┬──────────┘
           │
           │ JWT signé
           ▼
┌─────────────────────┐
│ test-resource-server│
│   Resource Server   │
└──────────┬──────────┘
           │
           ▼
      API protégées
```

---

# Prérequis

- Java 25
- Maven 3.9+
- Projet `auth-server` démarré

---

# Configuration

## application.properties

```properties
server.port=8081

spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://localhost:8080/oauth2/jwks
```

Le Resource Server utilisera automatiquement le endpoint :

```text
http://localhost:8080/oauth2/jwks
```

pour récupérer les clés publiques lui permettant de valider les signatures des JWT.

---

# Démarrage

Depuis la racine du projet :

```bash
mvn spring-boot:run
```

ou :

```bash
mvn clean package
java -jar target/test-resource-server-*.*ar
```

---

# Obtention*d'un token

Exemple de génération **un*token depuis l'Authorization Serve* :

```bash
curl --request POST \
* --url*http://localhost:8080/oauth2/token*\
  --header 'Content-Type: applic*tion/x-www-form-urlencoded' \
  --*ata grant_type=client_credentials *
  --data scope=tpa:tpa-api.read \*  --user CLIENT_ID*CLIENT_SECRET
```

Réponse :

*``*son
{
  "access_token": "...",
  "*oken_type": "Bearer",
  "expires_i*": 300,
**"scope": "tpa:tpa-api.read"
}
```
*---

# Utilisation du token

Exemp*e :

```bash
curl*\
  --header "Authorization: Beare* <ACCESS_TOKEN>" \
  http://*ocalhost:8081/api/read
```

---

#*Scopes utilisés

Dans le projet d'*xemple :

| Scope*| Description |
|---------**--------|
| `tpa:tpa-api.read` | L*cture des ressources exposées par *PA |
| `tpa:tpa-api.write` | Écrit*re des ressources exposées par TPA*|

---

# Exemples d'API

## Endpo*nt public

```http
GET /api/public*```

Accessible sans authentificat*on.

---

## Endpoint lecture

```*ttp
GET /api/read
```

Nécessite :*
```text
tpa:tpa-api.read
```

Exe*ple Spring Security :

```*ava*@PreAuthorize("hasAuthority('SCOPE*tpa:tpa-api.read')")
```

---

## *
