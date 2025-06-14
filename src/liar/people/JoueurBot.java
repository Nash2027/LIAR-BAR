package liar.people;

import liar.Joueur;
import liar.jeux.Carte;

import java.util.Random;

/**
 * Classe representant un joueur bot (IA) dans le jeu Liar's Bar.
 * Le bot joue selon une strategie simple : jouer une carte correspondante a la valeur demandee si possible,
 * sinon bluffer en jouant une autre carte.
 */
public class JoueurBot extends Joueur {

    private final Random random = new Random();

    /**
     * Constructeur du joueur bot avec un nom.
     *
     * @param nom nom du joueur bot
     */
    public JoueurBot(String nom) {
        super(nom);
    }

    /**
     * Methode pour jouer une carte en fonction de la valeur demandee.
     * Le bot cherche une carte correspondant a la valeur demandee dans sa main.
     * S'il en trouve une, il la joue, sinon il bluffe en jouant la premiere carte disponible.
     *
     * @param valeurDemandee la valeur de carte que le bot doit essayer de jouer
     * @return la carte jouee
     */
    @Override
    public Carte jouerCarte(Carte.TypeCarte valeurDemandee) {
        // Strategie simple : jouer la premiere carte de la main si possible,
        // sinon bluffer en jouant une autre carte.

        // Chercher une carte qui correspond a la valeur demandee
        for (int i = 0; i < main.size(); i++) {
            if (main.get(i).getType() == valeurDemandee) {
                return main.remove(i);
            }
        }

        // Sinon jouer la premiere carte (bluff)
        return main.remove(0);
    }

}
