package liar.people;

import liar.Joueur;
import liar.jeux.Carte;

import java.util.Scanner;

/**
 * Represente un joueur humain dans le jeu Liar's Bar.
 * Ce joueur choisit manuellement une carte a jouer via l'entree console.
 */
public class JoueurHumain extends Joueur {

    /**
     * Construit un joueur humain avec un nom donne.
     *
     * @param nom le nom du joueur
     */
    public JoueurHumain(String nom) {
        super(nom);
    }

    /**
     * Demande au joueur de choisir une carte a jouer via l'entree console.
     * Affiche la main et verifie que l'indice fourni est valide.
     *
     * @param valeurDemandee la valeur que le joueur est cense jouer
     * @return la carte choisie par le joueur
     */
    @Override
    public Carte jouerCarte(Carte.TypeCarte valeurDemandee) {
        afficherMain();
        Scanner scanner = new Scanner(System.in);
        int index = -1;

        // Demander a l'utilisateur de choisir une carte valide
        do {
            System.out.print("Entrez l'indice de la carte a jouer (0 a " + (main.size() - 1) + ") : ");
            if (scanner.hasNextInt()) {
                index = scanner.nextInt();
            } else {
                scanner.next(); // Consommer l'entree invalide
                index = -1;
            }
        } while (index < 0 || index >= main.size());

        // Retirer et retourner la carte choisie
        return main.remove(index);
    }

    /**
     * Variante automatique pour tests ou version sans interaction utilisateur.
     * Joue une carte correspondant a la valeur demandee si possible, sinon bluffe.
     *
     * @param valeurDemandee la valeur que le joueur est cense jouer
     * @return la carte jouee, ou {@code null} si la main est vide
     */
    public Carte jouerCarteAuto(Carte.TypeCarte valeurDemandee) {
        for (Carte carte : main) {
            if (carte.getType() == valeurDemandee) {
                main.remove(carte);
                return carte;
            }
        }
        return main.isEmpty() ? null : main.remove(0);
    }
}
