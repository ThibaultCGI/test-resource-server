package io.github.tbondetti.testresourceserver.infrastructure.openapi.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommandeOpenApiConstants {

    public static final String TAG = "Commandes";

    public static final String TAG_DESCRIPTION = "Gestion des commandes.";

    public static final String COMMANDE_READ_DESCRIPTION = "Lire les commandes";

    public static final String COMMANDE_WRITE_DESCRIPTION = "Créer et modifier les commandes";

    public static final String GET_SUMMARY = "Consulter une commande";

    public static final String GET_DESCRIPTION = "Retourne une commande à partir de son numéro.";

    public static final String CREATE_SUMMARY = "Créer une commande";

    public static final String CREATE_DESCRIPTION = "Crée une nouvelle commande.";

    public static final String NUMERO_PARAMETER_DESCRIPTION = "Numéro de la commande.";

    public static final String NUMERO_PARAMETER_EXAMPLE = "CMD-20260730-123456";

    public static final String CREATE_REQUEST_DESCRIPTION = "Commande à créer.";

    public static final String RESPONSE_200_FOUND = "Commande trouvée.";

    public static final String RESPONSE_201_CREATED = "Commande créée.";

    public static final String RESPONSE_404_NOT_FOUND = "Commande introuvable.";

    public static final String EMAIL_CLIENT_DESCRIPTION = "Adresse email du client";

    public static final String EMAIL_CLIENT_EXAMPLE = "jean.martin@gmail.com";

    public static final String NUMEROS_PRODUITS_DESCRIPTION = "Liste des numéros de produits";

    public static final String NUMEROS_PRODUITS_EXAMPLE = "[\"P00001\",\"P00002\"]";

    public static final String MONTANT_DESCRIPTION = "Montant total de la commande";

    public static final String MONTANT_EXAMPLE = "123.45";
}
