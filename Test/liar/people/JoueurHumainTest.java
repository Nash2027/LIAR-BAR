package liar.people;

import liar.jeux.Carte;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JoueurHumainTest {

    private JoueurHumain joueur;
    private InputStream systemInBackup;

    @BeforeEach
    void setUp() {
        joueur = new JoueurHumain("Test");
        systemInBackup = System.in; // sauvegarder System.in
    }

    @AfterEach
    void tearDown() {
        System.setIn(systemInBackup); // restaurer System.in après chaque test
    }

    // Tests existants pour jouerCarteAuto

    @Test
    void testJouerCarteAuto_avecCarteCorrespondante() {
        Carte.TypeCarte valeurDemandee = Carte.TypeCarte.DAME;
        joueur.main = new ArrayList<>();
        joueur.main.add(new Carte(Carte.TypeCarte.AS));
        joueur.main.add(new Carte(valeurDemandee)); // carte à jouer
        joueur.main.add(new Carte(Carte.TypeCarte.ROI));

        Carte carteJouee = joueur.jouerCarteAuto(valeurDemandee);

        assertEquals(valeurDemandee, carteJouee.getType());
        assertFalse(joueur.main.contains(carteJouee));
        assertEquals(2, joueur.main.size());
    }

    @Test
    void testJouerCarteAuto_sansCarteCorrespondante() {
        Carte.TypeCarte valeurDemandee = Carte.TypeCarte.DAME;
        joueur.main = new ArrayList<>();
        joueur.main.add(new Carte(Carte.TypeCarte.AS));
        joueur.main.add(new Carte(Carte.TypeCarte.ROI));

        Carte carteJouee = joueur.jouerCarteAuto(valeurDemandee);

        // doit jouer la première carte (AS)
        assertEquals(Carte.TypeCarte.AS, carteJouee.getType());
        assertFalse(joueur.main.contains(carteJouee));
        assertEquals(1, joueur.main.size());
    }

    @Test
    void testJouerCarteAuto_mainVide() {
        joueur.main = new ArrayList<>();

        Carte carteJouee = joueur.jouerCarteAuto(Carte.TypeCarte.AS);

        assertNull(carteJouee);
        assertTrue(joueur.main.isEmpty());
    }

    // Nouveaux tests pour jouerCarte (avec simulation d'entrée utilisateur)

    @Test
    void testJouerCarte_avecEntreesInvalidesPuisValide() {
        joueur.main = new ArrayList<>();
        joueur.main.add(new Carte(Carte.TypeCarte.AS));
        joueur.main.add(new Carte(Carte.TypeCarte.DAME));
        joueur.main.add(new Carte(Carte.TypeCarte.ROI));

        // Simuler plusieurs entrées : "abc" (non int), "-1" (hors borne), "10" (hors borne), puis "1" (valide)
        String input = "abc\n-1\n10\n1\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        Carte carteJouee = joueur.jouerCarte(Carte.TypeCarte.AS);

        // L'indice valide est 1, donc la carte DAME doit être jouée
        assertEquals(Carte.TypeCarte.DAME, carteJouee.getType());
        assertEquals(2, joueur.main.size());
        assertFalse(joueur.main.contains(carteJouee));
    }

    @Test
    void testJouerCarte_avecEntreeValideDirecte() {
        joueur.main = new ArrayList<>();
        joueur.main.add(new Carte(Carte.TypeCarte.AS));
        joueur.main.add(new Carte(Carte.TypeCarte.DAME));
        joueur.main.add(new Carte(Carte.TypeCarte.ROI));

        String input = "0\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        Carte carteJouee = joueur.jouerCarte(Carte.TypeCarte.AS);

        // indice 0 => AS joué
        assertEquals(Carte.TypeCarte.AS, carteJouee.getType());
        assertEquals(2, joueur.main.size());
        assertFalse(joueur.main.contains(carteJouee));
    }

    @Test
    void testJouerCarte_avecEntreeValideDerniereCarte() {
        joueur.main = new ArrayList<>();
        joueur.main.add(new Carte(Carte.TypeCarte.AS));
        joueur.main.add(new Carte(Carte.TypeCarte.DAME));
        joueur.main.add(new Carte(Carte.TypeCarte.ROI));

        String input = "2\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        Carte carteJouee = joueur.jouerCarte(Carte.TypeCarte.AS);

        // indice 2 => ROI joué
        assertEquals(Carte.TypeCarte.ROI, carteJouee.getType());
        assertEquals(2, joueur.main.size());
        assertFalse(joueur.main.contains(carteJouee));
    }
}
