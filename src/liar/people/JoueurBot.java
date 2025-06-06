package liar.people;

import liar.Joueur;
import liar.jeux.Carte;

import java.util.Random;

public class JoueurBot extends Joueur {

    private final Random random = new Random();

    public JoueurBot(String nom) {
        super(nom);
    }

    @Override
    public Carte jouerCarte(Carte.TypeCarte valeurDemandee) {
        // Stratégie simple : jouer la première carte de la main si possible,
        // sinon bluffer en jouant une autre carte.

        // Chercher une carte qui correspond à la valeur demandée
        for (int i = 0; i < main.size(); i++) {
            if (main.get(i).getType() == valeurDemandee) {
                return main.remove(i);
            }
        }

        // Sinon jouer la première carte (bluff)
        return main.remove(0);
    }

}
