package liar.utilitaire;

import liar.jeux.Carte;
import liar.jeux.Partie;
import liar.Joueur;
import liar.people.JoueurBot;
import liar.people.JoueurHumain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Classe utilitaire pour l'affichage et la gestion des interactions avec l'utilisateur dans le jeu Liar's Bar.
 */
public class Affichage {

    /**
     * Affiche les regles du jeu Liar's Bar.
     */
    public static void afficherRegles() {
        System.out.println("Bienvenue dans Liar, le jeu du bluff !");
        System.out.println("Regles :");
        System.out.println("- Chaque joueur a 3 vies.");
        System.out.println(" Au debut, une carte est demandee (exemple : '3' ou ici As/Roi/Dame/Valet).");
        System.out.println(" A chaque tour, un joueur pose une carte face cachee en annoncant qu'elle correspond a la valeur demandee.");
        System.out.println(" Il peut bluffer ou dire la verite.");
        System.out.println(" Les autres joueurs peuvent l'accuser de bluff.");
        System.out.println(" Si le bluff est attrape, le menteur perd une vie.");
        System.out.println(" Si l'accusation est fausse, l'accusateur perd une vie.");
        System.out.println(" Le dernier joueur avec des vies gagne.");
        System.out.println();
    }

    /**
     * Selectionne les joueurs selon les choix de l'utilisateur via le scanner.
     *
     * @param scanner Scanner d'entree
     * @return liste des joueurs choisis ou liste vide si quitter ou entree invalide
     */
    public static List<Joueur> selectionnerJoueurs(Scanner scanner) {
        List<Joueur> joueurs = new ArrayList<>();

        System.out.println("Choisissez un mode de jeu :");
        System.out.println("1 - Humain vs Bot");
        System.out.println("2 - 2 Joueurs");
        System.out.println("3 - 3 Joueurs");
        System.out.println("4 - 4 Joueurs");
        System.out.println("5 - Partie personnalisee");
        System.out.println("6 - Quitter");
        System.out.print("Votre choix : ");

        int choix;
        try {
            choix = scanner.nextInt();
            scanner.nextLine();  // consommer la fin de ligne
        } catch (Exception e) {
            System.out.println("Entree invalide. Fin du programme.");
            return joueurs; // vide = fin
        }

        switch (choix) {
            case 1:
                System.out.print("Entrez le nom du joueur humain : ");
                String nomHumain = scanner.nextLine();
                joueurs.add(new JoueurHumain(nomHumain));
                joueurs.add(new JoueurBot("Bot1"));
                break;

            case 2:
                System.out.print("Entrez le nom du premier joueur humain : ");
                String nom1 = scanner.nextLine();
                joueurs.add(new JoueurHumain(nom1));
                System.out.print("Entrez le nom du second joueur humain : ");
                String nom2 = scanner.nextLine();
                joueurs.add(new JoueurHumain(nom2));
                break;

            case 3:
                for (int i = 1; i <= 3; i++) {
                    System.out.print("Entrez le nom du joueur " + i + " : ");
                    String nom = scanner.nextLine();
                    joueurs.add(new JoueurHumain(nom));
                }
                break;

            case 4:
                for (int i = 1; i <= 4; i++) {
                    System.out.print("Entrez le nom du joueur " + i + " : ");
                    String nom = scanner.nextLine();
                    joueurs.add(new JoueurHumain(nom));
                }
                break;

            case 5:
                int totalJoueurs;
                do {
                    System.out.print("Combien de joueurs au total ? (2 a 4) : ");
                    totalJoueurs = scanner.nextInt();
                    scanner.nextLine();
                } while (totalJoueurs < 2 || totalJoueurs > 4);

                int nbHumains;
                do {
                    System.out.print("Combien de joueurs humains ? (0 a " + totalJoueurs + ") : ");
                    nbHumains = scanner.nextInt();
                    scanner.nextLine();
                } while (nbHumains < 0 || nbHumains > totalJoueurs);

                for (int i = 1; i <= nbHumains; i++) {
                    System.out.print("Entrez le nom du joueur humain " + i + " : ");
                    String nom = scanner.nextLine();
                    joueurs.add(new JoueurHumain(nom));
                }

                for (int i = 1; i <= (totalJoueurs - nbHumains); i++) {
                    joueurs.add(new JoueurBot("Bot" + i));
                }
                break;

            case 6:
                System.out.println("Au revoir !");
                break;

            default:
                System.out.println("Choix invalide. Fin du programme.");
                break;
        }

        return joueurs;
    }

    /**
     * Lance le jeu complet, avec regles, selection des joueurs puis la partie.
     *
     * @param scanner Scanner d'entree
     */
    public static void lancerJeux(Scanner scanner) {
        afficherRegles();

        List<Joueur> joueurs = selectionnerJoueurs(scanner);
        if (joueurs.isEmpty()) {
            // Quitter ou erreur
            scanner.close();
            return;
        }

        Carte.TypeCarte[] valeursPossibles = Carte.TypeCarte.values();
        Carte.TypeCarte valeurDemandee = valeursPossibles[new Random().nextInt(valeursPossibles.length)];
        Partie partie = new Partie(joueurs, valeurDemandee);

        partie.demarrer(scanner);

        scanner.close();
    }
}
