package io.github.tbondetti.testresourceserver.infrastructure.utils;

import io.github.tbondetti.testresourceserver.core.domain.Commande;
import io.github.tbondetti.testresourceserver.core.domain.Produit;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.math.RoundingMode.DOWN;

@UtilityClass
public class DomainGeneratorUtils {

    static final double MONTANT_MIN = 1.00;
    static final double MONTANT_MAX = 100.00;
    static final int MONTANT_SCALE = 2;
    static final String NOM_PRODUIT = "Produit %s";
    static final List<String> PRENOMS = List.of(
            "jean",
            "marie",
            "paul",
            "julie",
            "luc",
            "emma",
            "thomas",
            "camille",
            "hugo",
            "lea"
    );

    static final List<String> NOMS = List.of(
            "martin",
            "bernard",
            "durand",
            "dubois",
            "moreau",
            "laurent",
            "simon",
            "michel",
            "leroy",
            "roux"
    );

    static final List<String> DOMAINES = List.of(
            "gmail.com",
            "outlook.com",
            "hotmail.fr",
            "yahoo.fr",
            "orange.fr",
            "free.fr",
            "laposte.net",
            "proton.me"
    );

    public static Produit generateRandomProduit(final String numero) {
        return Produit.builder()
                .numero(numero)
                .nom(NOM_PRODUIT.formatted(numero))
                .prix(generateRandomBigDecimal())
                .build();
    }

    public static BigDecimal generateRandomBigDecimal() {
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(MONTANT_MIN, MONTANT_MAX))
                .setScale(MONTANT_SCALE, DOWN);
    }

    public static Commande generateRandomCommande(final String numero) {
        return Commande.builder()
                .numero(numero)
                .emailClient(randomMail())
                .montant(generateRandomBigDecimal())
                .numerosProduits(randomNumerosProduits())
                .build();
    }

    public static int randomInt(
            final int min,
            final int max
    ) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static String randomMail() {

        final String prenom = PRENOMS.get(ThreadLocalRandom.current().nextInt(PRENOMS.size()));

        final String nom = NOMS.get(ThreadLocalRandom.current().nextInt(NOMS.size()));

        final String domaine = DOMAINES.get(ThreadLocalRandom.current().nextInt(DOMAINES.size()));

        return "%s.%s@%s".formatted(
                prenom,
                nom,
                domaine
        );
    }


    public static String randomNumeroProduit() {
        return "P%05d".formatted(randomInt(1, 99999));
    }

    public static List<String> randomNumerosProduits() {
        final int size = randomInt(3, 15);
        final List<String> numeros = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            numeros.add(randomNumeroProduit());
        }

        return numeros;
    }
}
