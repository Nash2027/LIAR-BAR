import liar.utilitaire.Affichage;
import java.util.Scanner;

/**
 * Classe principale du jeu Liar's Bar.
 * Lance l'application en demarrant l'affichage et la gestion du jeu via la classe Affichage.
 */
public class Main {

    /**
     * Point d'entree du programme.
     * Initialise un Scanner pour la saisie utilisateur et lance le jeu.
     *
     * @param args arguments en ligne de commande (non utilises)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Affichage.lancerJeux(scanner);
        scanner.close();
    }
}
