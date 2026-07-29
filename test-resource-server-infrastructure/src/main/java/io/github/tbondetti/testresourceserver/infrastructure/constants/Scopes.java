package io.github.tbondetti.testresourceserver.infrastructure.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Scopes {
    public static final String PRODUIT_READ = "SCOPE_trs:product-api.read";
    public static final String PRODUIT_WRITE = "SCOPE_trs:product-api.write";
    public static final String COMMANDE_READ = "SCOPE_trs:commande-api.read";
    public static final String COMMANDE_WRITE = "SCOPE_trs:commande-api.write";

}
