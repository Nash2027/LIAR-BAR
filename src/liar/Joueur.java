package liar;

import liar.jeux.Carte;
import java.util.ArrayList;
import java.util.List;

public abstract class Joueur {
    protected String nom;
    public List<Carte> main;
    protected int vies;

    public Joueur(String nom) {
        this.nom = nom;
        this.main = new ArrayList<>();
        this.vies = 3;  // Chaque joueur commence avec 3 vies
    }

    public String getNom() {
        return nom;
    }

    public List<Carte> getMain() {
        return main;
    }

    // Ajouter une carte à la main du joueur
    public void ajouterCarte(Carte carte) {
        main.add(carte);
    }

    // Retirer une vie (perte de vie)
    public void perdreVie() {
        vies--;
        System.out.println(nom + " perd une vie ! Vies restantes : " + vies);
    }

    // Vérifier si le joueur est encore vivant (au moins 1 vie)
    public boolean estVivant() {
        return vies > 0;
    }

    // Afficher la main (utile pour JoueurHumain)
    public void afficherMain() {
        System.out.println("Main de " + nom + " :");
        for (int i = 0; i < main.size(); i++) {
            System.out.println(i + " : " + main.get(i));
        }
    }

    public List<Carte> getCartes() {
        return this.main; // ou le nom de ta variable qui stocke les cartes
    }


    // Méthodes abstraites à implémenter dans les sous-classes
    public abstract Carte jouerCarte(Carte.TypeCarte valeurDemandee);

    public int getVies() {
        return vies;
    }
}
