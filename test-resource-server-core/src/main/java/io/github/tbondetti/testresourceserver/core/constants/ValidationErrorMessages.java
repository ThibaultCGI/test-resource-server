package io.github.tbondetti.testresourceserver.core.constants;

import lombok.experimental.UtilityClass;

import static io.github.tbondetti.testresourceserver.core.constants.CommandeRules.COMMANDE_EMAIL_CLIENT_MAX_LENGTH;
import static io.github.tbondetti.testresourceserver.core.constants.ProduitRules.PRODUIT_NOM_MAX_LENGTH;
import static io.github.tbondetti.testresourceserver.core.constants.ProduitRules.PRODUIT_NOM_MIN_LENGTH;

@UtilityClass
public class ValidationErrorMessages {

    static final String CARACTERES = " caractères.";

    public static final String FORMAT_DONNEE_INCORRECT = "Format de donnée incorrect.";

    public static final String ERROR_PRODUIT_NOM_REQUIRED = "Le nom du produit est obligatoire.";

    public static final String ERROR_PRODUIT_NOM_LENGTH = "La taille du nom du produit doit être comprise entre "
            + PRODUIT_NOM_MIN_LENGTH
            + " et "
            + PRODUIT_NOM_MAX_LENGTH
            + CARACTERES
            ;

    public static final String ERROR_PRODUIT_NOM_TOO_SHORT = "Le nom du produit doit contenir au moins "
            + PRODUIT_NOM_MIN_LENGTH
            + CARACTERES
            ;

    public static final String ERROR_PRODUIT_NOM_TOO_LONG = "Le nom du produit ne doit pas dépasser les "
            + PRODUIT_NOM_MAX_LENGTH
            + CARACTERES
            ;

    public static final String ERROR_PRODUIT_PRIX_MUST_NOT_BE_NULL = "Le prix du produit ne peut être nul.";

    public static final String ERROR_PRODUIT_PRIX_MUST_BE_POSITIVE = "Le prix du produit doit être positif.";

    public static final String ERROR_PRODUIT_PRIX_FORMAT_INCORRECT = "Le format du prix du produit est incorrect.";

    public static final String ERROR_PRODUIT_PRIX_HAS_TOO_MANY_DECIMALS = "Le prix du produit ne peut avoir plus de deux décimales.";

    public static final String ERROR_COMMANDE_EMAIL_CLIENT_REQUIRED = "L'adresse email du client est obligatoire.";

    public static final String ERROR_COMMANDE_EMAIL_CLIENT_TOO_LONG = "L'adresse email du client ne peut dépasser "
            + COMMANDE_EMAIL_CLIENT_MAX_LENGTH
            + CARACTERES
            ;

    public static final String ERROR_COMMANDE_EMAIL_CLIENT_INVALID = "L'adresse email du client est invalide.";

    public static final String ERROR_PRODUITS_NOT_FOUND = "Les numéros de produits %s ne sont pas présents dans le référentiel.";

    public static final String ERROR_COMMANDE_NUMEROS_PRODUITS_ARE_REQUIRED = "Au moins un numéro de produit est obligatoire.";

    public static final String ERROR_COMMANDE_NUMEROS_PRODUIT_MUST_NOT_BE_BLANK = "Les numéros de produits ne peuvent être nuls ou vides.";

}
