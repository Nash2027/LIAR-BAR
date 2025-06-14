package liar.people;

import liar.jeux.Carte;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe {@link JoueurHumain}.
 * <p>
 * Cette classe teste les methodes {@code jouerCarteAuto(TypeCarte)} et {@code jouerCarte(TypeCarte)}.
 * <ul>
 *   <li>Tests de {@code jouerCarteAuto} avec ou sans carte correspondante, et main vide.</li>
 *   <li>Tests de {@code jouerCarte} simulant des entrées utilisateur via {@code System.in},
 *       y compris entrées invalides puis valide, et entrées valides directes.</li>
 * </ul>
 * <p>
 * Les tests sur {@code jouerCarte} redirigent {@code System.in} pour simuler la saisie utilisateur,
 * et restaurent le flux d'entrée original après chaque test.
 */
public class JoueurHumainTest {

    private JoueurHumain joueur;
    private InputStream systemInBackup;

    /**
     * Initialise un joueur humain avant chaque test et sauvegarde System.in.
     */
    @BeforeEach
    void setUp() {
        joueur = new JoueurHumain("Test");
        systemInBackup = System.in; // sauvegarde de System.in
    }

    /**
     * Restaure System.in après chaque test.
     */
    @AfterEach
    void tearDown() {
        System.setIn(systemInBackup); // restauration de System.in
    }

    /**
     * Test que {@code jouerCarteAuto} joue une carte correspondant à la valeur demandée
     * et la retire de la main.
     */
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

    /**
     * Test que {@code jouerCarteAuto} joue la première carte (bluff) si aucune carte correspondante.
     */
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

    /**
     * Test que {@code jouerCarteAuto} retourne null quand la main est vide.
     */
    @Test
    void testJouerCarteAuto_mainVide() {
        joueur.main = new ArrayList<>();

        Carte carteJouee = joueur.jouerCarteAuto(Carte.TypeCarte.AS);

        assertNull(carteJouee);
        assertTrue(joueur.main.isEmpty());
    }

    /**
     * Test que {@code jouerCarte} gère plusieurs entrées invalides avant une entrée valide,
     * et joue la carte sélectionnée par l'utilisateur.
     */
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

    /**
     * Test que {@code jouerCarte} joue correctement la carte correspondant à l'indice saisi directement.
     */
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

    /**
     * Test que {@code jouerCarte} joue la dernière carte de la main quand son indice est saisi.
     */
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
