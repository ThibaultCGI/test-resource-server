package io.github.tbondetti.testresourceserver.infrastructure.openapi.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OpenApiConstants {

    public static final String API_TITLE = "Test Resource Server API";

    public static final String API_DESCRIPTION = """
            API OAuth2 Resource Server utilisée pour tester auth-server.
            
            Cette application expose une API REST sécurisée
            par OAuth2 et JWT.
            """;

    public static final String API_VERSION = "1.0.0";

    public static final String CONTACT_NAME = "Thibault BONDETTI";

    public static final String SECURITY_SCHEME_NAME = "oauth2";

    public static final String RESPONSE_400_BAD_REQUEST = "Requête invalide.";

    public static final String RESPONSE_401_UNAUTHORIZED = "Authentification requise.";

    public static final String RESPONSE_403_FORBIDDEN = "Accès refusé.";

    public static final String RESPONSE_500_INTERNAL_SERVER_ERROR = "Erreur interne du serveur.";

    public static final String API_ERROR_RESPONSE_CODE_DESCRIPTION = "Code fonctionnel";

    public static final String API_ERROR_RESPONSE_CODE_EXAMPLE = "PRODUIT_NOT_FOUND";

    public static final String API_ERROR_RESPONSE_DESCRIPTION_DESCRIPTION = "Description de l'erreur";

    public static final String API_ERROR_RESPONSE_DESCRIPTION = "Format standard des réponses en erreur.";
}
