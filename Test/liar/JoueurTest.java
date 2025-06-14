package liar;

import liar.jeux.Carte;
import liar.people.JoueurBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe {@link JoueurBot}.
 * <p>
 * Ces tests couvrent principalement :
 * <ul>
 *   <li>L'ajout de cartes dans la main via {@code ajouterCarte(Carte)}.</li>
 *   <li>La gestion des vies avec {@code perdreVie()} et {@code getVies()}.</li>
 *   <li>Le test de survie {@code estVivant()} selon le nombre de vies.</li>
 *   <li>L'accès au nom du joueur via {@code getNom()}.</li>
 *   <li>L'affichage de la main du joueur via {@code afficherMain()},
 *       avec capture de la sortie console pour vérification.</li>
 * </ul>
 * <p>
 * Chaque test réinitialise un nouvel objet {@code JoueurBot} nommé "BotTest".
 */
public class JoueurTest {

    private JoueurBot joueur;

    /**
     * Initialisation avant chaque test : création d'un {@code JoueurBot} nommé "BotTest".
     */
    @BeforeEach
    void setUp() {
        joueur = new JoueurBot("BotTest");
    }

    /**
     * Test l'ajout d'une carte dans la main du joueur.
     * Vérifie que la carte est bien ajoutée et que la taille de la main est correcte.
     */
    @Test
    void testAjouterCarte() {
        Carte carte = new Carte(Carte.TypeCarte.AS);
        joueur.ajouterCarte(carte);
        assertTrue(joueur.getMain().contains(carte));
        assertEquals(1, joueur.getMain().size());
    }

    /**
     * Test la perte de vies et la récupération du nombre de vies.
     * Vérifie la décrémentation correcte des vies à chaque perte.
     */
    @Test
    void testPerdreVie_et_getVies() {
        assertEquals(3, joueur.getVies());

        joueur.perdreVie();
        assertEquals(2, joueur.getVies());

        joueur.perdreVie();
        joueur.perdreVie();
        assertEquals(0, joueur.getVies());
    }

    /**
     * Test si le joueur est vivant en fonction du nombre de vies restantes.
     * Le joueur est vivant tant que ses vies sont supérieures à 0.
     */
    @Test
    void testEstVivant() {
        assertTrue(joueur.estVivant());

        joueur.perdreVie();
        joueur.perdreVie();
        joueur.perdreVie();

        assertFalse(joueur.estVivant());
    }

    /**
     * Test la récupération du nom du joueur.
     */
    @Test
    void testGetNom() {
        assertEquals("BotTest", joueur.getNom());
    }

    /**
     * Test l'affichage de la main du joueur.
     * Capture la sortie standard et vérifie que le texte contient les indices et le nom du joueur.
     */
    @Test
    void testAfficherMain() {
        joueur.ajouterCarte(new Carte(Carte.TypeCarte.DAME));
        joueur.ajouterCarte(new Carte(Carte.TypeCarte.ROI));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        joueur.afficherMain();

        System.setOut(originalOut); // restauration sortie standard

        String output = out.toString();
        assertTrue(output.contains("Main de BotTest"));
        assertTrue(output.contains("0 :"));
        assertTrue(output.contains("1 :"));
    }
}
