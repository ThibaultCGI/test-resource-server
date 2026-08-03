package io.github.tbondetti.testresourceserver.web.security;

import lombok.experimental.UtilityClass;

import static io.github.tbondetti.testresourceserver.web.security.Scopes.COMMANDE_READ;
import static io.github.tbondetti.testresourceserver.web.security.Scopes.COMMANDE_WRITE;
import static io.github.tbondetti.testresourceserver.web.security.Scopes.PRODUIT_READ;
import static io.github.tbondetti.testresourceserver.web.security.Scopes.PRODUIT_WRITE;

@UtilityClass
public class Authorizations {

    static final String SCOPE_PREFIX = "SCOPE_";

    public static final String CAN_READ_PRODUCT = "hasAnyAuthority('" + SCOPE_PREFIX + PRODUIT_READ + "', '" + SCOPE_PREFIX + PRODUIT_WRITE + "')";
    public static final String CAN_WRITE_PRODUCT = "hasAuthority('" + SCOPE_PREFIX + PRODUIT_WRITE + "')";

    public static final String CAN_READ_COMMANDE = "hasAnyAuthority('" + SCOPE_PREFIX + COMMANDE_READ + "', '" + SCOPE_PREFIX + COMMANDE_WRITE + "')";
    public static final String CAN_WRITE_COMMANDE = "hasAuthority('" + SCOPE_PREFIX + COMMANDE_WRITE + "')";
}
