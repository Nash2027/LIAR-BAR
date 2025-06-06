package liar.utilitaire;

import liar.Joueur;
import liar.people.JoueurBot;
import liar.people.JoueurHumain;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class AffichageTest {

    @Test
    void testConstructeurAffichage() {
        Affichage affichage = new Affichage();
        assertNotNull(affichage, "L'instance Affichage doit être créée");
    }

    // Test afficherRegles (simple affichage console)
    @Test
    void testAfficherRegles() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        Affichage.afficherRegles();

        System.setOut(originalOut);
        String output = out.toString();

        assertTrue(output.contains("Bienvenue dans Liar"));
        assertTrue(output.contains("Chaque joueur a 3 vies"));
        assertTrue(output.contains("Le dernier joueur avec des vies gagne"));
    }

    // Test choix invalide : non-int en entrée
    @Test
    void testSelectionnerJoueursChoixNonInt() {
        String input = "abc\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        List<Joueur> joueurs = Affichage.selectionnerJoueurs(scanner);
        assertTrue(joueurs.isEmpty());
    }

    // Test choix invalide : nombre hors bornes (exemple 99)
    @Test
    void testSelectionnerJoueursChoixInvalide() {
        String input = "99\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        List<Joueur> joueurs = Affichage.selectionnerJoueurs(scanner);
        assertTrue(joueurs.isEmpty());
    }

    // Choix 1 : Humain vs Bot
    @Test
    void testSelectionnerJoueursChoix1() {
        String input = "1\nAlice\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        List<Joueur> joueurs = Affichage.selectionnerJoueurs(scanner);
        assertEquals(2, joueurs.size());
        assertTrue(joueurs.get(0) instanceof JoueurHumain);
        assertEquals("Alice", joueurs.get(0).getNom());
        assertTrue(joueurs.get(1) instanceof JoueurBot);
        assertEquals("Bot1", joueurs.get(1).getNom());
    }

    // Choix 2 : 2 joueurs humains
    @Test
    void testSelectionnerJoueursChoix2() {
        String input = "2\nBob\nCharlie\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        List<Joueur> joueurs = Affichage.selectionnerJoueurs(scanner);
        assertEquals(2, joueurs.size());
        assertEquals("Bob", joueurs.get(0).getNom());
        assertEquals("Charlie", joueurs.get(1).getNom());
    }

    // Choix 3 : 3 joueurs humains
    @Test
    void testSelectionnerJoueursChoix3() {
        String input = "3\nDan\nEve\nFrank\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        List<Joueur> joueurs = Affichage.selectionnerJoueurs(scanner);
        assertEquals(3, joueurs.size());
        assertEquals("Dan", joueurs.get(0).getNom());
        assertEquals("Eve", joueurs.get(1).getNom());
        assertEquals("Frank", joueurs.get(2).getNom());
    }

    // Choix 4 : 4 joueurs humains
    @Test
    void testSelectionnerJoueursChoix4() {
        String input = "4\nGina\nHarry\nIvy\nJack\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        List<Joueur> joueurs = Affichage.selectionnerJoueurs(scanner);
        assertEquals(4, joueurs.size());
        assertEquals("Gina", joueurs.get(0).getNom());
        assertEquals("Harry", joueurs.get(1).getNom());
        assertEquals("Ivy", joueurs.get(2).getNom());
        assertEquals("Jack", joueurs.get(3).getNom());
    }

    // Choix 5 : partie personnalisée, tests avec boucle do-while (totalJoueurs = 3, nbHumains = 2)
    @Test
    void testSelectionnerJoueursChoix5() {
        // Cas normal, entrée valide
        String input = "5\n3\n2\nKate\nLiam\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        List<Joueur> joueurs = Affichage.selectionnerJoueurs(scanner);

        assertEquals(3, joueurs.size());
        assertEquals("Kate", joueurs.get(0).getNom());
        assertEquals("Liam", joueurs.get(1).getNom());
        assertTrue(joueurs.get(2) instanceof JoueurBot);
        assertEquals("Bot1", joueurs.get(2).getNom());
    }

    // Choix 5 : tests des boucles do-while en forçant mauvaises entrées avant bonnes
    @Test
    void testSelectionnerJoueursChoix5BoucleDoWhile() {
        // totalJoueurs : entre 0 et 5 invalides, puis 2 valide
        // nbHumains : entre -1 et 5 invalides, puis 1 valide
        String input = "5\n0\n5\n2\n-1\n5\n1\nAlice\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        List<Joueur> joueurs = Affichage.selectionnerJoueurs(scanner);

        // Devrait accepter totalJoueurs = 2 et nbHumains = 1
        assertEquals(2, joueurs.size());
        assertEquals("Alice", joueurs.get(0).getNom());
        assertTrue(joueurs.get(1) instanceof JoueurBot);
    }

    // Choix 6 : quitter
    @Test
    void testSelectionnerJoueursChoix6() {
        String input = "6\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        List<Joueur> joueurs = Affichage.selectionnerJoueurs(scanner);
        assertTrue(joueurs.isEmpty());
    }
}
