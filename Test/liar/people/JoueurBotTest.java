package liar.people;

import liar.jeux.Carte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JoueurBotTest {

    private JoueurBot bot;

    @BeforeEach
    void setUp() {
        bot = new JoueurBot("BotTest");
    }

    @Test
    void testJouerCarte_avecCarteCorrespondante() {
        // Préparer la main avec une carte qui correspond à valeurDemandee
        Carte.TypeCarte valeurDemandee = Carte.TypeCarte.AS;
        bot.main = new ArrayList<>();
        bot.main.add(new Carte(Carte.TypeCarte.AS));
        bot.main.add(new Carte(valeurDemandee)); // la bonne carte
        bot.main.add(new Carte(Carte.TypeCarte.ROI));

        Carte jouee = bot.jouerCarte(valeurDemandee);

        assertEquals(valeurDemandee, jouee.getType());
        // La main doit avoir perdu cette carte
        assertFalse(bot.main.contains(jouee));
        // La main contient toujours les 2 autres cartes
        assertEquals(2, bot.main.size());
    }

    @Test
    void testJouerCarte_sansCarteCorrespondante() {
        // Préparer la main sans carte qui correspond à valeurDemandee
        Carte.TypeCarte valeurDemandee = Carte.TypeCarte.DAME;
        bot.main = new ArrayList<>();
        bot.main.add(new Carte(Carte.TypeCarte.AS));
        bot.main.add(new Carte(Carte.TypeCarte.ROI));

        Carte jouee = bot.jouerCarte(valeurDemandee);

        // Doit jouer la première carte (bluff)
        assertEquals(Carte.TypeCarte.AS, jouee.getType());
        assertFalse(bot.main.contains(jouee));
        assertEquals(1, bot.main.size());
    }
}
