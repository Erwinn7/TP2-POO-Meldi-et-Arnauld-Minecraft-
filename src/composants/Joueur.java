package composants;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Joueur extends Entite {
    private List<Item> inventaire;
    private Random random;

    public Joueur(String nom, int pointsDeVie, int pointsDattaque, int pointsDeDefense, int axe_x, int axe_y, Item main1, Item main2) {
        super(nom, pointsDeVie, pointsDattaque, pointsDeDefense, axe_x, axe_y, main1, main2);
        this.inventaire = new ArrayList<>();
        this.random = new Random();
    }
    
    public Joueur(String nom) {
    	this(nom, 100, 10, 10, 1, 1, null, null);
    }
    
    public Joueur(String nom, int pointsDeVie, int pointsDattaque, int pointsDeDefense, int axe_x, int axe_y) {
        this(nom, pointsDeVie, pointsDattaque, pointsDeDefense, axe_x, axe_y, null, null);
    }

    public void deplacer() {
        System.out.println(nom + " se déplace dans le monde...");
        int event = random.nextInt(3);
        switch (event) {
            case 0:
                System.out.println("Vous avez trouvé un bloc !");
                Bloc bloc = new Bloc("Pierre", true, true, false);
                System.out.println("Bloc trouvé : " + bloc);
                break;
            case 1:
                System.out.println("Un ennemi sauvage apparaît !");
                Entite monstre = new Entite("Zombie", 30, 8, 2, 3, 4) {};
                combat(monstre);
                break;
            case 2:
                System.out.println("Rien d'intéressant ici...");
                break;
        }
    }
    
    public void deplacer(String direction) {
        switch (direction.toLowerCase()) {
        	// haut    
        	case "nord":
                axe_x--;
                break;
            // bas
            case "sud":
                axe_x++;
                break;
            // droite
            case "est":
                axe_y++;
                break;
            // gauche
            case "ouest":
                axe_y--;
                break;
            default:
                System.out.println("Direction invalide !");
                return;
        }
        System.out.println(nom + " s'est déplacé vers le " + direction + ". Nouvelle position : (" + axe_x + ", " + axe_y + ")");
    }

    public void ajouterItem(Item item) {
        inventaire.add(item);
        System.out.println(item.getNom() + " a été ajouté à votre inventaire.");
    }
    
    public void changerItem(Item ancien, Item nouveau) {
        // Vérifier que l'ancien item est dans une main
        if (mainPrincipale != ancien && mainSecondaire != ancien) {
            System.out.println("L'item à changer n'est pas actuellement équipé !");
            return;
        }

        if (!inventaire.contains(nouveau)) {
            System.out.println("Le nouvel item n'est pas dans l'inventaire !");
            return;
        }

        if (!nouveau.getEstChangeable()) {
            System.out.println("Cet item ne peut pas être équipé !");
            return;
        }

        if (mainPrincipale == ancien && mainPrincipale.getType().equals(nouveau.getType())) {
            System.out.println("Vous ne pouvez pas équiper deux armes du même type !");
            return;
        }

        if (mainSecondaire == ancien && mainSecondaire.getType().equals(nouveau.getType())) {
            System.out.println("Vous ne pouvez pas équiper deux armes du même type !");
            return;
        }

        inventaire.add(ancien);

        inventaire.remove(nouveau);

        if (mainPrincipale == ancien) {
            mainPrincipale = nouveau;
            System.out.println("Main principale changée : " + ancien.getNom() + " remplacé par " + nouveau.getNom());
        } else if (mainSecondaire == ancien) {
            mainSecondaire = nouveau;
            System.out.println("Main secondaire changée : " + ancien.getNom() + " remplacé par " + nouveau.getNom());
        }
    }


    public void afficherInventaire() {
        System.out.println("Inventaire de " + nom + " :");
        for (Item item : inventaire) {
            System.out.println("- " + item);
        }
    }

    private void combat(Entite monstre) {
        while (monstre.estEnVie() && this.estEnVie()) {
            this.attaquer(monstre);
            if (monstre.estEnVie()) {
                monstre.attaquer(this);
            }
        }
        if (this.estEnVie()) {
            System.out.println("Vous avez vaincu le " + monstre.nom + " !");
        } else {
            System.out.println("Vous êtes mort !");
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
