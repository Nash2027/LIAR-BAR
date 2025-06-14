package liar.jeux;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe {@link Carte}.
 *
 * Verifie le bon fonctionnement du constructeur, de la methode {@code getType()}
 * ainsi que de la methode {@code toString()} pour chaque type de carte.
 */
public class CarteTest {

    /**
     * Teste la construction d'une carte et la methode {@code getType()}.
     */
    @Test
    public void testConstructeurEtGetType() {
        Carte carte = new Carte(Carte.TypeCarte.AS);
        assertEquals(Carte.TypeCarte.AS, carte.getType(), "Le type retourne par getType() est incorrect.");
    }

    /**
     * Teste la methode {@code toString()} pour le type AS.
     */
    @Test
    public void testToStringAs() {
        Carte carte = new Carte(Carte.TypeCarte.AS);
        assertEquals("As", carte.toString(), "Le toString() pour AS est incorrect.");
    }

    /**
     * Teste la methode {@code toString()} pour le type ROI.
     */
    @Test
    public void testToStringRoi() {
        Carte carte = new Carte(Carte.TypeCarte.ROI);
        assertEquals("Roi", carte.toString(), "Le toString() pour ROI est incorrect.");
    }

    /**
     * Teste la methode {@code toString()} pour le type DAME.
     */
    @Test
    public void testToStringDame() {
        Carte carte = new Carte(Carte.TypeCarte.DAME);
        assertEquals("Dame", carte.toString(), "Le toString() pour DAME est incorrect.");
    }

    /**
     * Teste la methode {@code toString()} pour le type VALET.
     */
    @Test
    public void testToStringValet() {
        Carte carte = new Carte(Carte.TypeCarte.VALET);
        assertEquals("Valet", carte.toString(), "Le toString() pour VALET est incorrect.");
    }

    /**
     * Teste la creation d'une carte pour chaque type et verifie
     * que {@code getType()} retourne le bon type et {@code toString()} ne retourne pas null.
     */
    @Test
    public void testTousLesTypes() {
        for (Carte.TypeCarte type : Carte.TypeCarte.values()) {
            Carte carte = new Carte(type);
            assertEquals(type, carte.getType(), "getType() ne retourne pas le bon type.");
            assertNotNull(carte.toString(), "toString() retourne null pour " + type);
        }
    }
}
