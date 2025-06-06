package liar;

import liar.jeux.Carte;
import liar.people.JoueurBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class JoueurTest {

    private JoueurBot joueur;

    @BeforeEach
    void setUp() {
        joueur = new JoueurBot("BotTest");
    }

    @Test
    void testAjouterCarte() {
        Carte carte = new Carte(Carte.TypeCarte.AS);
        joueur.ajouterCarte(carte);
        assertTrue(joueur.getMain().contains(carte));
        assertEquals(1, joueur.getMain().size());
    }

    @Test
    void testPerdreVie_et_getVies() {
        assertEquals(3, joueur.getVies());

        joueur.perdreVie();
        assertEquals(2, joueur.getVies());

        joueur.perdreVie();
        joueur.perdreVie();
        assertEquals(0, joueur.getVies());
    }

    @Test
    void testEstVivant() {
        assertTrue(joueur.estVivant());

        joueur.perdreVie();
        joueur.perdreVie();
        joueur.perdreVie();

        assertFalse(joueur.estVivant());
    }

    @Test
    void testGetNom() {
        assertEquals("BotTest", joueur.getNom());
    }

    @Test
    void testAfficherMain() {
        joueur.ajouterCarte(new Carte(Carte.TypeCarte.DAME));
        joueur.ajouterCarte(new Carte(Carte.TypeCarte.ROI));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        joueur.afficherMain();

        String output = out.toString();
        assertTrue(output.contains("Main de BotTest"));
        assertTrue(output.contains("0 :"));
        assertTrue(output.contains("1 :"));

        System.setOut(System.out); // reset stdout
    }
}
