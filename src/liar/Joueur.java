package liar;

import liar.jeux.Carte;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite representant un joueur du jeu Liar.
 * Elle sert de base pour les joueurs humains et les bots.
 */
public abstract class Joueur {

    /**
     * Nom du joueur.
     */
    protected String nom;

    /**
     * Main du joueur, composee de cartes.
     */
    public List<Carte> main;

    /**
     * Nombre de vies restantes du joueur.
     */
    protected int vies;

    /**
     * Constructeur d'un joueur.
     * Initialise la main et les vies a 3.
     *
     * @param nom le nom du joueur
     */
    public Joueur(String nom) {
        this.nom = nom;
        this.main = new ArrayList<>();
        this.vies = 3;  // Chaque joueur commence avec 3 vies
    }

    /**
     * Retourne le nom du joueur.
     *
     * @return le nom du joueur
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne la main du joueur.
     *
     * @return la liste de cartes du joueur
     */
    public List<Carte> getMain() {
        return main;
    }

    /**
     * Ajoute une carte a la main du joueur.
     *
     * @param carte la carte a ajouter
     */
    public void ajouterCarte(Carte carte) {
        main.add(carte);
    }

    /**
     * Retire une vie au joueur et affiche un message.
     */
    public void perdreVie() {
        vies--;
        System.out.println(nom + " perd une vie ! Vies restantes : " + vies);
    }

    /**
     * Verifie si le joueur est encore en vie.
     *
     * @return true si le joueur a au moins une vie, false sinon
     */
    public boolean estVivant() {
        return vies > 0;
    }

    /**
     * Affiche les cartes de la main du joueur dans la console.
     * Surtout utile pour les joueurs humains.
     */
    public void afficherMain() {
        System.out.println("Main de " + nom + " :");
        for (int i = 0; i < main.size(); i++) {
            System.out.println(i + " : " + main.get(i));
        }
    }

    /**
     * Retourne la liste des cartes du joueur.
     * Alias de {@link #getMain()}.
     *
     * @return liste de cartes
     */
    public List<Carte> getCartes() {
        return this.main;
    }

    /**
     * Methode abstraite a implementer pour jouer une carte selon la valeur demandee.
     *
     * @param valeurDemandee la carte attendue (le joueur peut bluffer ou dire la verite)
     * @return la carte jouee
     */
    public abstract Carte jouerCarte(Carte.TypeCarte valeurDemandee);

    /**
     * Retourne le nombre de vies restantes.
     *
     * @return nombre de vies
     */
    public int getVies() {
        return vies;
    }
}
