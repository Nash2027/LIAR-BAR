package liar.jeux;

import liar.Joueur;
import java.util.*;

/**
 * Represente une partie du jeu Liar's Bar.
 * Une partie se joue avec plusieurs joueurs, une pioche de cartes, une pile centrale,
 * et une valeur de carte demandee a chaque tour.
 */
public class Partie {

    /** Liste des joueurs participant a la partie. */
    private List<Joueur> joueurs;

    /** Pioche de cartes utilisee pour distribuer au debut du jeu. */
    private Stack<Carte> pioche;

    /** Pile centrale ou les cartes jouees sont deposees. */
    private List<Carte> pileCentre;

    /** Valeur de carte demandee a chaque tour (choisie aleatoirement). */
    private Carte.TypeCarte valeurDemandee;

    /**
     * Construit une nouvelle partie avec une liste de joueurs et une valeur demande initiale.
     *
     * @param joueurs la liste des joueurs
     * @param valeurDemandeeInitiale la premiere valeur demandee (non utilisee ici, valeur aleatoire a la place)
     */
    public Partie(List<Joueur> joueurs, Carte.TypeCarte valeurDemandeeInitiale) {
        this.joueurs = new ArrayList<>(joueurs); // copie de la liste pour eviter les effets de bord
        this.valeurDemandee = choisirValeurAleatoire(); // valeur demandee choisie aleatoirement
        this.pileCentre = new ArrayList<>();
        creerPioche();
        distribuerCartes();
    }

    /**
     * Choisit aleatoirement une valeur de carte parmi AS, ROI, DAME, VALET.
     *
     * @return une valeur de carte aleatoire
     */
    private Carte.TypeCarte choisirValeurAleatoire() {
        Carte.TypeCarte[] valeurs = Carte.TypeCarte.values();
        return valeurs[new Random().nextInt(valeurs.length)];
    }

    /**
     * Cree et melange la pioche avec 4 cartes de chaque type.
     */
    private void creerPioche() {
        pioche = new Stack<>();
        List<Carte> toutesCartes = new ArrayList<>();
        for (Carte.TypeCarte type : Carte.TypeCarte.values()) {
            for (int i = 0; i < 4; i++) {
                toutesCartes.add(new Carte(type));
            }
        }
        Collections.shuffle(toutesCartes);
        pioche.addAll(toutesCartes);
    }

    /**
     * Distribue equitablement les cartes aux joueurs.
     */
    private void distribuerCartes() {
        int index = 0;
        while (!pioche.isEmpty()) {
            Carte carte = pioche.pop();
            joueurs.get(index).ajouterCarte(carte);
            index = (index + 1) % joueurs.size();
        }
    }

    /**
     * Lance et gere le deroulement d'une partie.
     *
     * @param scanner un objet Scanner pour lire les saisies clavier des joueurs humains
     */
    public void demarrer(Scanner scanner) {
        int tour = 0;

        while (nombreJoueursVivants() > 1) {

            // Cas de match nul : plus aucune carte chez tous les joueurs
            if (joueurs.stream().allMatch(j -> j.getCartes().isEmpty())) {
                System.out.println("Match nul : tous les joueurs n'ont plus de cartes !");
                break;
            }

            Joueur joueurActuel = prochainJoueur(tour);
            if (!joueurActuel.estVivant()) {
                tour++;
                continue;
            }

            valeurDemandee = choisirValeurAleatoire();

            System.out.println("\nTour de " + joueurActuel.getNom());
            System.out.println("Carte demandee : " + valeurDemandee);

            Carte carteJouee = joueurActuel.jouerCarte(valeurDemandee);
            pileCentre.add(carteJouee);

            System.out.println(joueurActuel.getNom() + " pose une carte en annonçant : " + valeurDemandee);

            boolean accusationEffectuee = false;

            for (Joueur adversaire : joueurs) {
                if (adversaire != joueurActuel && adversaire.estVivant()) {
                    boolean accuse;

                    if (adversaire.getClass().getSimpleName().equals("JoueurBot")) {
                        accuse = new Random().nextBoolean();
                        System.out.println(adversaire.getNom() + " a " + (accuse ? "accuse un bluff." : "decide de ne rien dire."));
                    } else {
                        System.out.print(adversaire.getNom() + ", voulez-vous accuser un bluff ? (true/false) : ");
                        accuse = scanner.nextBoolean();
                    }

                    if (accuse) {
                        accusationEffectuee = true;
                        if (carteJouee.getType() != valeurDemandee) {
                            System.out.println("Bluff attrape !");
                            joueurActuel.perdreVie();
                        } else {
                            System.out.println("Fausse accusation !");
                            adversaire.perdreVie();
                        }
                        break;
                    }
                }
            }

            if (!accusationEffectuee) {
                System.out.println("Aucune accusation. Le jeu continue.");
            }

            joueurs.removeIf(j -> !j.estVivant());

            tour++;
        }

        // Fin de partie : un gagnant ou plus de joueurs vivants
        if (nombreJoueursVivants() == 1) {
            Joueur gagnant = joueurs.stream().filter(Joueur::estVivant).findFirst().get();
            System.out.println("\n🎉 Le gagnant est " + gagnant.getNom() + " !");
        } else {
            System.out.println("Plus de joueurs vivants. Fin de partie.");
        }
    }

    /**
     * Compte et retourne le nombre de joueurs encore en vie.
     *
     * @return le nombre de joueurs vivants
     */
    public int nombreJoueursVivants() {
        int vivants = 0;
        for (Joueur j : joueurs) {
            if (j.estVivant()) vivants++;
        }
        return vivants;
    }

    /**
     * Retourne le joueur dont c'est le tour, en fonction de l'indice du tour.
     *
     * @param tour l'indice du tour en cours
     * @return le joueur concerne
     */
    public Joueur prochainJoueur(int tour) {
        return joueurs.get(tour % joueurs.size());
    }
}
