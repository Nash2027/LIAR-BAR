package liar.people;

import liar.Joueur;
import liar.jeux.Carte;

import java.util.Scanner;

public class JoueurHumain extends Joueur {

    public JoueurHumain(String nom) {
        super(nom);
    }

    @Override
    public Carte jouerCarte(Carte.TypeCarte valeurDemandee) {
        afficherMain();
        Scanner scanner = new Scanner(System.in);
        int index = -1;

        // Demander à l'utilisateur de choisir une carte valide
        do {
            System.out.print("Entrez l'indice de la carte à jouer (0 à " + (main.size() - 1) + ") : ");
            if (scanner.hasNextInt()) {
                index = scanner.nextInt();
            } else {
                scanner.next(); // consommer l'entrée invalide
                index = -1;
            }
        } while (index < 0 || index >= main.size());

        // Retirer et retourner la carte choisie
        return main.remove(index);
    }


    public Carte jouerCarteAuto(Carte.TypeCarte valeurDemandee) {
        // Si le joueur a une carte qui correspond à la valeur demandée, il la joue
        for (Carte carte : main) {
            if (carte.getType() == valeurDemandee) {
                main.remove(carte);
                return carte;
            }
        }
        // Sinon, il bluffe : il joue la première carte disponible
        return main.isEmpty() ? null : main.removeFirst();
    }
}
