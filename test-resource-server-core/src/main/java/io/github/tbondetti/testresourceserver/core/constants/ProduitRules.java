package io.github.tbondetti.testresourceserver.core.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProduitRules {

    public static final int PRODUIT_NUMERO_LENGTH = 25;
    public static final int PRODUIT_NOM_MIN_LENGTH = 3;
    public static final int PRODUIT_NOM_MAX_LENGTH = 50;

    public static final int PRODUIT_PRIX_MAX_SCALE = 2;
}
