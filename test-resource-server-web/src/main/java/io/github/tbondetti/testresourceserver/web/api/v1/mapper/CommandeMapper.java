package io.github.tbondetti.testresourceserver.web.api.v1.mapper;

import io.github.tbondetti.testresourceserver.core.domain.Commande;
import io.github.tbondetti.testresourceserver.web.api.v1.response.CommandeResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommandeMapper {

    public static CommandeResponse toResponse(final Commande commande) {
        return CommandeResponse
                .builder()
                .numero(commande.numero())
                .emailClient(commande.emailClient())
                .montant(commande.montant())
                .numerosProduits(commande.numerosProduits())
                .build();
    }
}
