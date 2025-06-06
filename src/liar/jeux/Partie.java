package liar.jeux;

import liar.Joueur;
import java.util.*;

public class Partie {
    private List<Joueur> joueurs;
    private Stack<Carte> pioche;
    private List<Carte> pileCentre;
    private Carte.TypeCarte valeurDemandee;

    public Partie(List<Joueur> joueurs, Carte.TypeCarte valeurDemandeeInitiale) {
        this.joueurs = new ArrayList<>(joueurs); // copie de la liste pour éviter les effets de bord
        this.valeurDemandee = choisirValeurAleatoire(); // aléatoire dès le début
        this.pileCentre = new ArrayList<>();
        creerPioche();
        distribuerCartes();
    }

    private Carte.TypeCarte choisirValeurAleatoire() {
        Carte.TypeCarte[] valeurs = Carte.TypeCarte.values();
        return valeurs[new Random().nextInt(valeurs.length)];
    }

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

    private void distribuerCartes() {
        int index = 0;
        while (!pioche.isEmpty()) {
            Carte carte = pioche.pop();
            joueurs.get(index).ajouterCarte(carte);
            index = (index + 1) % joueurs.size();
        }
    }

    public void demarrer(Scanner scanner) {
        int tour = 0;


        while (nombreJoueursVivants() > 1) {

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
            System.out.println("Carte demandée : " + valeurDemandee);

            Carte carteJouee = joueurActuel.jouerCarte(valeurDemandee);
            pileCentre.add(carteJouee);

            System.out.println(joueurActuel.getNom() + " pose une carte en annonçant : " + valeurDemandee);

            boolean accusationEffectuee = false;

            for (Joueur adversaire : joueurs) {
                if (adversaire != joueurActuel && adversaire.estVivant()) {
                    boolean accuse;

                    if (adversaire.getClass().getSimpleName().equals("JoueurBot")) {
                        accuse = new Random().nextBoolean();
                        System.out.println(adversaire.getNom() + " a " + (accuse ? "accusé un bluff." : "décidé de ne rien dire."));
                    } else {
                        System.out.print(adversaire.getNom() + ", voulez-vous accuser un bluff ? (true/false) : ");
                        accuse = scanner.nextBoolean();
                    }

                    if (accuse) {
                        accusationEffectuee = true;
                        if (carteJouee.getType() != valeurDemandee) {
                            System.out.println("Bluff attrapé !");
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

        if (nombreJoueursVivants() == 1) {
            Joueur gagnant = joueurs.stream().filter(Joueur::estVivant).findFirst().get();
            System.out.println("\n🎉 Le gagnant est " + gagnant.getNom() + " !");
        } else {
            System.out.println("Plus de joueurs vivants. Fin de partie.");
        }
    }


    public int nombreJoueursVivants() {
        int vivants = 0;
        for (Joueur j : joueurs) {
            if (j.estVivant()) vivants++;
        }
        return vivants;
    }

    public Joueur prochainJoueur(int tour) {
        return joueurs.get(tour % joueurs.size());
    }
}
