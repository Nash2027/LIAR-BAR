package liar.people;

import liar.jeux.Carte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe {@link JoueurBot}.
 * <p>
 * Verifie le comportement de la methode {@code jouerCarte(TypeCarte valeurDemandee)}.
 * <ul>
 *   <li>Test quand la main contient une carte correspondant a la valeur demandee.</li>
 *   <li>Test quand la main ne contient aucune carte correspondante (jeu de bluff).</li>
 * </ul>
 */
public class JoueurBotTest {

    private JoueurBot bot;

    /**
     * Initialise un joueur bot avant chaque test.
     */
    @BeforeEach
    void setUp() {
        bot = new JoueurBot("BotTest");
    }

    /**
     * Teste que {@code jouerCarte} joue une carte correspondant a la valeur demandee
     * et la retire de la main.
     */
    @Test
    void testJouerCarte_avecCarteCorrespondante() {
        Carte.TypeCarte valeurDemandee = Carte.TypeCarte.AS;
        bot.main = new ArrayList<>();
        bot.main.add(new Carte(Carte.TypeCarte.AS));
        bot.main.add(new Carte(valeurDemandee)); // carte correspondante
        bot.main.add(new Carte(Carte.TypeCarte.ROI));

        Carte jouee = bot.jouerCarte(valeurDemandee);

        assertEquals(valeurDemandee, jouee.getType(), "La carte jouee doit correspondre a la valeur demandee.");
        assertFalse(bot.main.contains(jouee), "La carte jouee doit etre retiree de la main.");
        assertEquals(2, bot.main.size(), "La main doit contenir 2 cartes apres le jeu.");
    }

    /**
     * Teste que {@code jouerCarte} joue la premiere carte (bluff)
     * quand aucune carte ne correspond a la valeur demandee.
     */
    @Test
    void testJouerCarte_sansCarteCorrespondante() {
        Carte.TypeCarte valeurDemandee = Carte.TypeCarte.DAME;
        bot.main = new ArrayList<>();
        bot.main.add(new Carte(Carte.TypeCarte.AS));
        bot.main.add(new Carte(Carte.TypeCarte.ROI));

        Carte jouee = bot.jouerCarte(valeurDemandee);

        assertEquals(Carte.TypeCarte.AS, jouee.getType(), "Le bot doit jouer la premiere carte comme bluff.");
        assertFalse(bot.main.contains(jouee), "La carte jouee doit etre retiree de la main.");
        assertEquals(1, bot.main.size(), "La main doit contenir une carte apres le jeu.");
    }
}
