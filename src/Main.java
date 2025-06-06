import liar.utilitaire.Affichage;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Affichage.lancerJeux(scanner);
        scanner.close();  // ferme à la fin du programme
    }
}
