package jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import composants.Effet;
import composants.Item;
import composants.Joueur;
import composants.TypeItem;

public class Jeu {
	/*
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
        
        int largeur = 10;
        int hauteur = 10;

        // Préparer le jeu
        PreparationJeu preparationJeu = new PreparationJeu(largeur, hauteur);
        
        // Générer les éléments sur le terrain
        preparationJeu.genererElements();
        
        // Afficher le terrain dans la console
        System.out.println("État initial du terrain :");
        preparationJeu.afficherTerrain();
        
        // Sauvegarder le terrain dans un fichier
        preparationJeu.ecrireTerrainDansFichier("terrain.txt");

    }
    */
	
	private static Scanner scanner = new Scanner(System.in);
	
    private static Joueur joueur = new Joueur("Joueur", 100, 15, 10, 0, 0);
    private static PreparationJeu terrain = new PreparationJeu(10, 10); // Terrain de 10x10
    private static Map<List<String>, String> tableDeCraft = PreparationJeu.getTableDeCraft();

    public static void main(String[] args) {
    	String nomJoueur = "";
        boolean jeuEnCours = true;
        
        terrain.genererElements();
        
        
        System.out.println("----------\tBienvenue dans MINECRAFT\t----------\n");
        
        do {
        	System.out.print("Entrez votre nom : ");
        	nomJoueur = scanner.nextLine();
        	
        	if (nomJoueur.length() <= 1) {
        		System.out.println("Oups entrez un nom plus long ...");
        		nomJoueur = "";
        	}
        } while (nomJoueur.length() <= 1);
        
        int typePartie = 0;
        do {
        	System.out.println("Jouer :");
        	System.out.println("1- En Solo");
        	System.out.println("2- En PVP");
        	System.out.println("3- Quitter");
        	
        	System.out.print("choix: ");
        	typePartie = scanner.nextInt();
        	
        	switch (typePartie) {
				case 1: {
		            scanner.nextLine();
		            joueur.setNom(nomJoueur);

		            Item bouclier = new Item("Bouclier", TypeItem.ARMURES, Effet.AUGMENTE_PD, 5, true);
		            Item lingotArgent = new Item("Lingot d'argent", TypeItem.RESSOURCES, Effet.AUGMENTE_PV, 1, false);
		            Item blocPierre = new Item("Bloc de pierre", TypeItem.ARMES, Effet.AUGMENTE_PA, 5, true);
		            
		            joueur.ajouterItem(bouclier);
		            joueur.ajouterItem(lingotArgent);
		            joueur.ajouterItem(blocPierre);
		            
					while (jeuEnCours) {
			            afficherMenu();
			            System.out.print("Choix : ");
			            int choix = Integer.parseInt(scanner.nextLine());

			            switch (choix) {
			                case 1: // Se déplacer
			                    deplacerJoueur();
			                    break;

			                case 2: // Consulter l'inventaire
			                    consulterInventaire();
			                    break;
			                
			                case 3: // Crafter
			                	List<Map.Entry<List<String>, String>> listeCraft = new ArrayList<>(tableDeCraft.entrySet());
			                    afficherTableDeCraft();
			                    int craft = 0;
			                    
			                    System.out.print("Choix : ");
			                    craft = scanner.nextInt();
			                    scanner.nextLine();
			                    
			                    if (craft < 1 || craft > listeCraft.size()) {
			                        System.out.println("Numéro invalide. Veuillez réessayer.");
			                        continue;
			                    }
			                    
			                    Map.Entry<List<String>, String> combinaisonChoisie = listeCraft.get(craft - 1);
			                    List<String> ingredients = combinaisonChoisie.getKey();
			                    String resultat = combinaisonChoisie.getValue();
			                    
			                    // Vérification des items dans l'inventaire
			                    Item item1 = joueur.trouverItemDansInventaire(ingredients.get(0));
			                    Item item2 = joueur.trouverItemDansInventaire(ingredients.get(1));

			                    if (item1 != null && item2 != null) {
			                        joueur.crafter(item1, item2);
			                        System.out.println("Vous avez crafté : " + resultat);
			                    } else {
			                        System.out.println("L'un des items requis est manquant dans votre inventaire.");
			                    }
			                    
			                    break;
			                
			                case 4: // Consulter la map
			                	terrain.enregistrerMap("terrain.txt", joueur);
			                    break;
			                
			                case 5: // Utiliser un item
			                    utiliserItem();
			                    break;
							/*
			                case 6: // Quitter le jeu
			                    System.out.println("Au revoir !");
			                    enregistrerTerrainEtJoueur(); // Sauvegarde finale avant de quitter
			                    jeuEnCours = false;
			                    break;
			                */
			                    
			                case 6:
			                    System.out.println("Au revoir !");
			                    jeuEnCours = false;
			                	break;
			                default:
			                    System.out.println("Option invalide. Veuillez réessayer.");
			            }
			        }
					break;
				}
				case 2: {
									
					break;
				}
				case 3: {
					System.out.println("Vous avez quitter la partie !!!");
					System.out.println("Merci d'avoir jouer Minecraft.");
					return;
				}
				default:
					System.out.println("Choix non disponible !");
					typePartie = 0;
			}
        } while (typePartie < 1 || typePartie > 3);
        
    }

    private static void afficherMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1- Se déplacer");
        System.out.println("2- Consulter l'inventaire");
        System.out.println("3- Crafter");
        System.out.println("4- Consulter la map");
        System.out.println("5- Utiliser un item");
        System.out.println("6- Quitter le jeu");
    }

    private static void deplacerJoueur() {
        System.out.println("\nChoisissez une direction : ");
        System.out.println("1- Haut");
        System.out.println("2- Bas");
        System.out.println("3- Gauche");
        System.out.println("4- Droite");
        System.out.print("choix: ");
        int direction = Integer.parseInt(scanner.nextLine());

        switch (direction) {
            case 1:
                joueur.deplacer(Joueur.HAUT, terrain);
                break;
            case 2:
                joueur.deplacer(Joueur.BAS, terrain);
                break;
            case 3:
                joueur.deplacer(Joueur.GAUCHE, terrain);
                break;
            case 4:
                joueur.deplacer(Joueur.DROITE, terrain);
                break;
            default:
                System.out.println("Direction invalide.");
        }
    }

    private static void consulterInventaire() {
        System.out.println("\n=== Inventaire ===");
        joueur.afficherInventaire();
    }
    
    public static void afficherTableDeCraft() {
        System.out.println("\n=== Table de Craft ===");

        int index = 1;
        for (Map.Entry<List<String>, String> entry : tableDeCraft.entrySet()) {
            List<String> ingredients = entry.getKey();
            String resultat = entry.getValue();
            System.out.println(index + ". " + ingredients.get(0) + " + " + ingredients.get(1) + " => " + resultat);
            index++;
        }
    }


    private static void utiliserItem() {
        System.out.println("=== Utiliser un item ===");
        joueur.afficherInventaire();

        System.out.print("Entrez l'index de l'item à utiliser (ou -1 pour annuler) : ");
        int index = Integer.parseInt(scanner.nextLine());

        if (index >= 0 && index < joueur.getInventaire().size()) {
            Item item = joueur.getInventaire().get(index);
            joueur.utiliserItem(item.getNom());
        } else if (index != -1) {
            System.out.println("Index invalide.");
        }
    }

	/*
    private static void enregistrerTerrainEtJoueur() {
        // Enregistrer le terrain dans un fichier texte
        try (FileWriter writer = new FileWriter("terrain.txt")) {
            writer.write(terrain.toString());
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde du terrain : " + e.getMessage());
        }

        // Enregistrer les informations du joueur dans un fichier JSON
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("joueur.json"), joueur);
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde des informations du joueur : " + e.getMessage());
        }
    }
    */
}
