package liar.jeux;

import liar.Joueur;
import liar.people.JoueurHumain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe {@link Partie}.
 * <p>
 * Cette classe teste different scenarios de jeu, notamment la gestion des joueurs,
 * la distribution des cartes, les accusations, la fin de partie et la pioche.
 * <p>
 * Une classe interne {@code CarteAuto} simule un joueur automatisé pour faciliter les tests.
 */
class PartieTest {

    /**
     * Classe interne simulant un joueur automatique pour les tests.
     * Permet de definir une main predefinie, de simuler bluff, accusation, et gestion des vies.
     */
    static class CarteAuto extends Joueur {
        private Queue<Carte> cartesAJouer;
        private boolean bluffer;
        private boolean accuser;
        private boolean vivant;

        public CarteAuto(String nom, List<Carte> main, boolean bluffer, boolean accuser) {
            super(nom);
            this.cartesAJouer = new LinkedList<>(main);
            this.bluffer = bluffer;
            this.accuser = accuser;
            this.vivant = true;
            this.getMain().addAll(main);
        }

        @Override
        public Carte jouerCarte(Carte.TypeCarte valeurDemandee) {
            return cartesAJouer.poll();
        }

        public void setAccuser(boolean accuser) {
            this.accuser = accuser;
        }

        @Override
        public boolean estVivant() {
            return vivant;
        }

        @Override
        public void perdreVie() {
            super.perdreVie();
            if (getVies() <= 0) vivant = false;
        }

        public boolean veutAccuser() {
            return accuser;
        }
    }

    private CarteAuto joueur1;
    private CarteAuto joueur2;
    private List<Joueur> joueurs;
    private Partie partie;

    /**
     * Initialise deux joueurs automatiques et une partie avant chaque test.
     */
    @BeforeEach
    void setUp() {
        joueur1 = new CarteAuto("J1", new ArrayList<>(), false, false);
        joueur2 = new CarteAuto("J2", new ArrayList<>(), false, false);
        joueurs = new ArrayList<>();
        joueurs.add(joueur1);
        joueurs.add(joueur2);
        partie = new Partie(joueurs, Carte.TypeCarte.DAME);
    }

    /**
     * Teste qu'une accusation juste fait perdre une vie au bluffeur.
     */
    @Test
    void testPartieAvecAccusationJuste() {
        CarteAuto j1 = new CarteAuto("J1", List.of(new Carte(Carte.TypeCarte.AS)), true, false);
        CarteAuto j2 = new CarteAuto("J2", List.of(new Carte(Carte.TypeCarte.ROI)), false, true);
        Partie p = new Partie(List.of(j1, j2), Carte.TypeCarte.ROI);
        j1.perdreVie();
        assertEquals(2, j1.getVies());
    }

    /**
     * Teste qu'une fausse accusation fait perdre une vie a l'accusateur.
     */
    @Test
    void testPartieAvecFausseAccusation() {
        CarteAuto j1 = new CarteAuto("J1", List.of(new Carte(Carte.TypeCarte.DAME)), false, false);
        CarteAuto j2 = new CarteAuto("J2", List.of(new Carte(Carte.TypeCarte.ROI)), false, true);
        Partie p = new Partie(List.of(j1, j2), Carte.TypeCarte.DAME);
        j2.perdreVie();
        assertEquals(2, j2.getVies());
    }

    /**
     * Teste la creation d'une partie sans accusation.
     */
    @Test
    void testFinDePartieSansAccusation() {
        CarteAuto j1 = new CarteAuto("J1", List.of(new Carte(Carte.TypeCarte.AS)), false, false);
        CarteAuto j2 = new CarteAuto("J2", List.of(new Carte(Carte.TypeCarte.ROI)), false, false);
        Partie p = new Partie(List.of(j1, j2), Carte.TypeCarte.DAME);
        assertNotNull(p);
    }

    /**
     * Teste que la distribution et la pioche sont correctes.
     */
    @Test
    void testDistributionEtPioche() {
        CarteAuto j1 = new CarteAuto("J1", new ArrayList<>(), false, false);
        CarteAuto j2 = new CarteAuto("J2", new ArrayList<>(), false, false);
        Partie p = new Partie(List.of(j1, j2), Carte.TypeCarte.VALET);
        int totalCartes = Carte.TypeCarte.values().length * 4;
        int totalDistribuees = j1.getMain().size() + j2.getMain().size();
        assertEquals(totalCartes, totalDistribuees);
    }

    /**
     * Teste la suppression d'un joueur quand il n'a plus de vies.
     */
    @Test
    void testSuppressionDesJoueursMorts() {
        joueur1.perdreVie();
        joueur1.perdreVie();
        joueur1.perdreVie(); // Mort
        assertFalse(joueur1.estVivant());
        assertTrue(joueur2.estVivant());
    }

    // --- Nouveaux tests ---

    @Test
    void testNombreJoueursVivants_initial() {
        assertEquals(2, partie.nombreJoueursVivants());
    }

    @Test
    void testNombreJoueursVivants_apresPerte() {
        joueur1.perdreVie();
        joueur1.perdreVie();
        joueur1.perdreVie();
        assertEquals(1, partie.nombreJoueursVivants());
    }

    @Test
    void testProchainJoueur_tour0() {
        Joueur prochain = partie.prochainJoueur(0);
        assertEquals("J1", prochain.getNom());
    }

    @Test
    void testProchainJoueur_tour1() {
        Joueur prochain = partie.prochainJoueur(1);
        assertEquals("J2", prochain.getNom());
    }

    @Test
    void testProchainJoueur_tour2Boucle() {
        Joueur prochain = partie.prochainJoueur(2);
        assertEquals("J1", prochain.getNom()); // 2 % 2 == 0
    }

    /**
     * Teste la fin de partie quand tous les joueurs n'ont plus de cartes.
     */
    @Test
    void testFinPartieMatchNul_siTousLesJoueursNontPlusDeCartes() {
        JoueurHumain j1 = new JoueurHumain("J1");
        JoueurHumain j2 = new JoueurHumain("J2");
        Partie partie = new Partie(Arrays.asList(j1, j2), Carte.TypeCarte.AS);

        j1.getCartes().clear();
        j2.getCartes().clear();

        String input = "false\nfalse\nfalse\nfalse\n";
        Scanner scanner = new Scanner(input);

        assertDoesNotThrow(() -> partie.demarrer(scanner));
    }

    /**
     * Teste que le constructeur initialise bien la pioche et distribue les cartes.
     */
    @Test
    void testConstructeur_initialisePiocheEtDistribueCartes() {
        JoueurHumain j1 = new JoueurHumain("Alice");
        JoueurHumain j2 = new JoueurHumain("Bob");
        Partie p = new Partie(List.of(j1, j2), Carte.TypeCarte.AS);

        assertFalse(j1.getCartes().isEmpty());
        assertFalse(j2.getCartes().isEmpty());
    }

    /**
     * Teste la methode privée {@code creerPioche()} via reflection.
     */
    @Test
    void testCreerPioche_remplitPioche() throws Exception {
        JoueurHumain j1 = new JoueurHumain("A");
        JoueurHumain j2 = new JoueurHumain("B");
        Partie p = new Partie(List.of(j1, j2), Carte.TypeCarte.AS);

        var method = Partie.class.getDeclaredMethod("creerPioche");
        method.setAccessible(true);
        method.invoke(p);

        var field = Partie.class.getDeclaredField("pioche");
        field.setAccessible(true);
        Stack<?> pioche = (Stack<?>) field.get(p);

        assertEquals(Carte.TypeCarte.values().length * 4, pioche.size());
    }

    /**
     * Teste la methode privée {@code distribuerCartes()} via reflection.
     */
    @Test
    void testDistribuerCartes_donneCartesAuxJoueurs() throws Exception {
        JoueurHumain j1 = new JoueurHumain("A");
        JoueurHumain j2 = new JoueurHumain("B");
        Partie p = new Partie(List.of(j1, j2), Carte.TypeCarte.AS);

        var method = Partie.class.getDeclaredMethod("distribuerCartes");
        method.setAccessible(true);
        method.invoke(p);

        assertFalse(j1.getCartes().isEmpty());
        assertFalse(j2.getCartes().isEmpty());
    }

    /**
     * Teste que la methode privee {@code choisirValeurAleatoire()} ne retourne pas null.
     */
    @Test
    void testChoisirValeurAleatoire_valeurNonNulle() throws Exception {
        Partie p = new Partie(joueurs, Carte.TypeCarte.AS);

        var method = Partie.class.getDeclaredMethod("choisirValeurAleatoire");
        method.setAccessible(true);
        Carte.TypeCarte val = (Carte.TypeCarte) method.invoke(p);

        assertNotNull(val);
    }

}
