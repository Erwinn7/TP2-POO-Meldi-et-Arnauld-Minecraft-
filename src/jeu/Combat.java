package jeu;

import java.io.IOException;

import composants.Joueur;

public class Combat {
    private Joueur joueur1;
    private Joueur joueur2;
    private int tourCourant = 1;

    public Combat(Joueur joueur1, Joueur joueur2) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
    }

    public void demarrer() throws IOException {
        
        
        try {
        	//lancer le serveur 
            ServeurPvP serveur = new ServeurPvP();
            serveur.demarrerServeur(joueur1,joueur2);
                  
            System.out.println("Le combat commence entre " + joueur1.getNom() + " et " + joueur2.getNom());
      
        } catch (IOException e) {
            e.printStackTrace();
        }
        


//        boolean combatEnCours = true;
//        while (combatEnCours) {
//            System.out.println("Tour " + tourCourant);
//
//            // Tour du joueur 1
//            String actionJoueur1 = joueur1.recevoirMessage();
//            System.out.println(joueur1.getNom() + " fait : " + actionJoueur1);
//            ActionCombat action1 = new ActionCombat(actionJoueur1, 10); // Exemple d'action
//            action1.executerAction(joueur1, joueur2);
//
//            if (joueur2.getPointsDeVie() <= 0) {
//                System.out.println(joueur2.getNom() + " est KO ! " + joueur1.getNom() + " gagne !");
//                combatEnCours = false;
//                break;
//            }
//
//            // Tour du joueur 2
//            String actionJoueur2 = joueur2.recevoirMessage();
//            System.out.println(joueur2.getNom() + " fait : " + actionJoueur2);
//            ActionCombat action2 = new ActionCombat(actionJoueur2, 10); // Exemple d'action
//            action2.executerAction(joueur2, joueur1);
//
//            if (joueur1.getPointsDeVie() <= 0) {
//                System.out.println(joueur1.getNom() + " est KO ! " + joueur2.getNom() + " gagne !");
//                combatEnCours = false;
//                break;
//            }
//
//            tourCourant++;
//        }
    }
    
    
    public static void main(String[] args) throws IOException {
    	
    	Joueur matias = new Joueur("Matias");
    	Joueur mathieu = new Joueur("Mathieu");
    	
    	Combat lecombat = new Combat(matias,mathieu);
    	lecombat.demarrer();

    	
    	
    	
    	
        
    }
}
 
