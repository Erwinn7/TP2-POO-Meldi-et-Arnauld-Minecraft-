package jeu;

import composants.Effet;
import composants.Item;
import composants.Joueur;
import composants.TypeItem;

public class Jeu {
	public static void main(String args[]) {
		Item epee = new Item("Épée", TypeItem.ARMES, Effet.AUGMENTE_PA, 10, true);
		
        Item bouclier = new Item("Bouclier", TypeItem.ARMURES, Effet.AUGMENTE_PD, 5, true);
        Item potion = new Item("Potion", TypeItem.POTIONS, Effet.AUGMENTE_PV, 20, false); // Non changeable
        Item arc = new Item("Arc", TypeItem.ARMES, Effet.AUGMENTE_PA, 15, true);

        // Création d'un joueur
        Joueur joueur = new Joueur("Alex", 100, 15, 10, 1, 1);

        // Ajout des items à l'inventaire
        joueur.ajouterItem(bouclier);
        joueur.ajouterItem(potion);
        joueur.ajouterItem(arc);

        // Équipement initial
        joueur.setMainPrincipale(epee); // Épée en main principale
        joueur.setMainSecondaire(null); // Rien en main secondaire

        // Afficher l'inventaire
        joueur.afficherInventaire();

        // Tentative de changement d'items
        System.out.println("\nTentative 1 : Changer l'épée par le bouclier");
        joueur.changerItem(epee, bouclier);

        System.out.println("\nInventaire après le changement :");
        joueur.afficherInventaire();

        System.out.println("\nTentative 2 : Changer le bouclier par la potion");
        joueur.changerItem(bouclier, potion);

        System.out.println("\nTentative 3 : Changer l'arc par la potion (erreur)");
        joueur.changerItem(arc, potion);
        
	}
}
