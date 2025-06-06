package liar.jeux;

public class Carte {
    public enum TypeCarte {
        AS, ROI, DAME, VALET
    }

    private final TypeCarte type;

    public Carte(TypeCarte type) {
        this.type = type;
    }

    public TypeCarte getType() {
        return type;
    }

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
