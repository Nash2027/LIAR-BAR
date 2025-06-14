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

/**
 * Tests unitaires pour la classe {@link Affichage}.
 * <p>
 * Cette classe couvre notamment :
 * <ul>
 *   <li>La création d'une instance {@link Affichage}.</li>
 *   <li>La vérification de l'affichage des règles via la méthode {@code afficherRegles} (capture console).</li>
 *   <li>Les tests de la méthode {@code selectionnerJoueurs(Scanner)} avec divers scénarios d'entrée utilisateur simulée :
 *       <ul>
 *         <li>Choix invalide non numérique.</li>
 *         <li>Choix invalide hors bornes.</li>
 *         <li>Choix valides 1 à 4 (différentes combinaisons humain/bot).</li>
 *         <li>Choix 5 avec boucle do-while, tests avec mauvaises entrées puis bonnes.</li>
 *         <li>Choix 6 pour quitter (retourne liste vide).</li>
 *       </ul>
 *   </li>
 * </ul>
 * <p>
 * Les entrées utilisateurs sont simulées via redirection de {@code System.in} avec {@link ByteArrayInputStream}.
 */
public class AffichageTest {

    @Test
    void testConstructeurAffichage() {
        Affichage affichage = new Affichage();
        assertNotNull(affichage, "L'instance Affichage doit être créée");
    }

    /**
     * Test l'affichage des règles de jeu en capturant la sortie console.
     */
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

    /**
     * Test de la méthode selectionnerJoueurs avec une entrée non entière (ex : "abc").
     * Doit retourner une liste vide.
     */
    @Test
    void testSelectionnerJoueursChoixNonInt() {
        String input = "abc\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        List<Joueur> joueurs = Affichage.selectionnerJoueurs(scanner);
        assertTrue(joueurs.isEmpty());
    }

    /**
     * Test de selectionnerJoueurs avec un choix hors bornes (ex : 99).
     * Doit retourner une liste vide.
     */
    @Test
    void testSelectionnerJoueursChoixInvalide() {
        String input = "99\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        List<Joueur> joueurs = Affichage.selectionnerJoueurs(scanner);
        assertTrue(joueurs.isEmpty());
    }

    /**
     * Test sélection du mode 1 : Humain vs Bot.
     */
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

    /**
     * Test sélection du mode 2 : 2 joueurs humains.
     */
    @Test
    void testSelectionnerJoueursChoix2() {
        String input = "2\nBob\nCharlie\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        List<Joueur> joueurs = Affichage.selectionnerJoueurs(scanner);
        assertEquals(2, joueurs.size());
        assertEquals("Bob", joueurs.get(0).getNom());
        assertEquals("Charlie", joueurs.get(1).getNom());
    }

    /**
     * Test sélection du mode 3 : 3 joueurs humains.
     */
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

    /**
     * Test sélection du mode 4 : 4 joueurs humains.
     */
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

    /**
     * Test sélection du mode 5 : partie personnalisée avec boucle do-while
     * et nombre total de joueurs + nombre d'humains valides.
     */
    @Test
    void testSelectionnerJoueursChoix5() {
        String input = "5\n3\n2\nKate\nLiam\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        List<Joueur> joueurs = Affichage.selectionnerJoueurs(scanner);

        assertEquals(3, joueurs.size());
        assertEquals("Kate", joueurs.get(0).getNom());
        assertEquals("Liam", joueurs.get(1).getNom());
        assertTrue(joueurs.get(2) instanceof JoueurBot);
        assertEquals("Bot1", joueurs.get(2).getNom());
    }

    /**
     * Test mode 5 avec entrées invalides dans les boucles do-while,
     * puis entrées valides.
     */
    @Test
    void testSelectionnerJoueursChoix5BoucleDoWhile() {
        String input = "5\n0\n5\n2\n-1\n5\n1\nAlice\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        List<Joueur> joueurs = Affichage.selectionnerJoueurs(scanner);

        assertEquals(2, joueurs.size());
        assertEquals("Alice", joueurs.get(0).getNom());
        assertTrue(joueurs.get(1) instanceof JoueurBot);
    }

    /**
     * Test mode 6 : quitter (liste vide).
     */
    @Test
    void testSelectionnerJoueursChoix6() {
        String input = "6\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        List<Joueur> joueurs = Affichage.selectionnerJoueurs(scanner);
        assertTrue(joueurs.isEmpty());
    }
}
