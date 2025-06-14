package liar.jeux;

/**
 * Represente une carte utilisee dans le jeu Liar's Bar.
 * Chaque carte possede un type parmi : As, Roi, Dame ou Valet.
 */
public class Carte {

    /**
     * Enumeration des types de cartes disponibles dans le jeu.
     */
    public enum TypeCarte {
        AS, ROI, DAME, VALET
    }

    /**
     * Le type de la carte (AS, ROI, DAME ou VALET).
     */
    private final TypeCarte type;

    /**
     * Construit une nouvelle carte avec le type specifie.
     *
     * @param type le type de la carte a creer
     */
    public Carte(TypeCarte type) {
        this.type = type;
    }

    /**
     * Retourne le type de la carte.
     *
     * @return le type de la carte
     */
    public TypeCarte getType() {
        return type;
    }

    /**
     * Retourne une representation textuelle lisible de la carte.
     *
     * @return le nom du type de la carte en francais (As, Roi, Dame, Valet)
     */
    @Override
    public String toString() {
        return switch (type) {
            case AS -> "As";
            case ROI -> "Roi";
            case DAME -> "Dame";
            case VALET -> "Valet";
        };
    }
}
