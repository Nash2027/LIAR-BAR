package liar.jeux;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CarteTest {

    @Test
    public void testConstructeurEtGetType() {
        Carte carte = new Carte(Carte.TypeCarte.AS);
        assertEquals(Carte.TypeCarte.AS, carte.getType(), "Le type retourné par getType() est incorrect.");
    }

    @Test
    public void testToStringAs() {
        Carte carte = new Carte(Carte.TypeCarte.AS);
        assertEquals("As", carte.toString(), "Le toString() pour AS est incorrect.");
    }

    @Test
    public void testToStringRoi() {
        Carte carte = new Carte(Carte.TypeCarte.ROI);
        assertEquals("Roi", carte.toString(), "Le toString() pour ROI est incorrect.");
    }

    @Test
    public void testToStringDame() {
        Carte carte = new Carte(Carte.TypeCarte.DAME);
        assertEquals("Dame", carte.toString(), "Le toString() pour DAME est incorrect.");
    }

    @Test
    public void testToStringValet() {
        Carte carte = new Carte(Carte.TypeCarte.VALET);
        assertEquals("Valet", carte.toString(), "Le toString() pour VALET est incorrect.");
    }

    @Test
    public void testTousLesTypes() {
        for (Carte.TypeCarte type : Carte.TypeCarte.values()) {
            Carte carte = new Carte(type);
            assertEquals(type, carte.getType(), "getType() ne retourne pas le bon type.");
            assertNotNull(carte.toString(), "toString() retourne null pour " + type);
        }
    }
}
