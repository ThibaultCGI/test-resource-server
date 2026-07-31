package io.github.tbondetti.testresourceserver.infrastructure.openapi.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProduitOpenApiConstants {

    public static final String TAG = "Produits";

    public static final String TAG_DESCRIPTION = "Gestion des produits.";

    public static final String PRODUIT_READ_DESCRIPTION = "Lire les produits";

    public static final String PRODUIT_WRITE_DESCRIPTION = "Créer et modifier les produits";

    public static final String GET_SUMMARY = "Consulter un produit";

    public static final String GET_DESCRIPTION = "Retourne un produit à partir de son numéro.";

    public static final String CREATE_SUMMARY = "Créer un produit";

    public static final String CREATE_DESCRIPTION = "Crée un nouveau produit.";

    public static final String NUMERO_PARAMETER_DESCRIPTION = "Numéro du produit.";

    public static final String NUMERO_PARAMETER_EXAMPLE = "P00001";

    public static final String CREATE_REQUEST_DESCRIPTION = "Produit à créer.";

    public static final String RESPONSE_200_FOUND = "Produit trouvé.";

    public static final String RESPONSE_201_CREATED = "Produit créé.";

    public static final String RESPONSE_404_NOT_FOUND = "Produit introuvable.";

    public static final String NOM_PRODUIT_DESCRIPTION = "Nom du produit";

    public static final String NOM_PRODUIT_EXAMPLE = "Produit de démonstration";

    public static final String PRIX_PRODUIT_DESCRIPTION = "Prix du produit (maximum 2 décimales).";

    public static final String PRIX_PRODUIT_EXAMPLE = "12.34";
}
