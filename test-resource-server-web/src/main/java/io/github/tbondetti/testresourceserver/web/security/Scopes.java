package io.github.tbondetti.testresourceserver.web.security;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Scopes {
    public static final String PRODUIT_READ =  "trs:produit-api.read";
    public static final String PRODUIT_WRITE = "trs:produit-api.write";
    public static final String COMMANDE_READ = "trs:commande-api.read";
    public static final String COMMANDE_WRITE = "trs:commande-api.write";

}
