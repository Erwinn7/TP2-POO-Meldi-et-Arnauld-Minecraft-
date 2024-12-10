package jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import composants.Effet;
import composants.Item;
import composants.Joueur;
import composants.Monstre;
import composants.TypeItem;

public class Jeu {
	
	private static Scanner scanner = new Scanner(System.in);
	
    private static Joueur joueur = new Joueur("Joueur", 100, 15, 10, 0, 0);
    private static PreparationJeu terrain = new PreparationJeu(10, 10); // Terrain de 10x10
    private static Map<List<String>, String> tableDeCraft = PreparationJeu.getTableDeCraft();
    private static Random random = new Random();
    private static String TIRETS = "----------";
    
    public static void main(String[] args) {
    	String nomJoueur = "";
        boolean jeuEnCours = true;
        
        terrain.genererElements();
        
        
        System.out.println(TIRETS +"\tBienvenue dans MINECRAFT\t"+ TIRETS +"\n");
        
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
        	System.out.println("\nVous souhaitez faire une partie :");
        	System.out.println("1- En Solo");
        	System.out.println("2- En PVP");
        	System.out.println("3- Quitter");
        	
        	System.out.print("choix: ");
        	typePartie = scanner.nextInt();
        	
        	switch (typePartie) {
				case 1: {
		            scanner.nextLine();
		            joueur.setNom(nomJoueur);
		            
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
			                	actualiserLaMap();
			                    break;
			                
			                case 5: // Utiliser un item
			                    utiliserItem();
			                    break;
			                case 6: // Afficher le profil d'un joueur
			                    afficherProfilJoueur();
			                    break;
			                case 7:
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

// ======================================================================================

    // MENU
    private static void afficherMenu() {
        System.out.println("\n"+ TIRETS +"MENU"+ TIRETS);
        System.out.println("1- Se déplacer");
        System.out.println("2- Consulter l'inventaire");
        System.out.println("3- Crafter");
        System.out.println("4- Consulter la map");
        System.out.println("5- Utiliser un item");
        System.out.println("6- Afficher le profil du joueur");
        System.out.println("7- Quitter le jeu");
    }
    
    
 // ======================================================================================

    // 1- DEPLACER LE JOUEUR
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
        int nouveauX = joueur.getAxe_x();
        int nouveauY = joueur.getAxe_y();
        
        char caractere = terrain.getContenuCase(nouveauX, nouveauY);
        logiqueJeu(caractere);
        terrain.setContenuCase(nouveauX, nouveauY, PreparationJeu.BLOC);
    }
    
    /* 
     * Si lors du déplacement le joueur tombe sur un item, on va le générer en fonction du type,
     * Dans la carte chaque type d'item est représenté par un caractère.
     * S'il tombe sur un Monstre alors le monstre est également généré et le combat est déclenché.
     */
    private static void logiqueJeu(char caractere) {
    	
    	switch (caractere) {
	        case PreparationJeu.MONSTRE: // Monstre
	            gererCombat();
	            break;
	        case 'A': // Arme
	        case 'B': // Bloc
	        case 'P': // Potion
	        case 'N': // Aliment
	        case 'R': // Armure
	        case 'S': // Ressource
	        case 'D': // Décoration
	        case 'E': // Redstone
	        case 'L': // Livre
	        case 'X': // Objet spécial
	            gererItem(caractere);
	            break;
	        default:
	            System.out.println("Rien ici...");
    	}
    }
    
    // Gestion du combat
    private static void gererCombat() {
        Monstre monstre = PreparationJeu.genererMonstreAleatoire();
        System.out.println("\nUn monstre apparaît : " + monstre);

        while (joueur.getPointsDeVie() > 0 && monstre.getPointsDeVie() > 0) {
            System.out.println("\nCombat en cours...");
            joueur.attaquer(monstre);
            if (monstre.getPointsDeVie() > 0) {
                monstre.attaquer(joueur);
            }
        }

        if (joueur.getPointsDeVie() <= 0) {
            System.out.println("\n\nVous êtes mort ! Fin de la partie.");
            System.exit(0); // Arrêter le jeu
        } else {
            System.out.println("\n\nVous avez vaincu le monstre !");
        }
    }

    // Generer les items
    private static void gererItem(char initiale) {
        TypeItem type = trouverTypeParInitiale(initiale);
        if (type != null) {
            Item item = genererItemAleatoire(type);
            joueur.ajouterItem(item);
            System.out.println("Vous avez collecté un item : " + item);
        } else {
            System.out.println("Aucun item associé à cette initiale.");
        }
    }
    
    private static TypeItem trouverTypeParInitiale(char initiale) {
        for (TypeItem type : TypeItem.values()) {
            if (type.getInitiale() == initiale) {
                return type;
            }
        }
        return null;
    }
    
    private static Item genererItemAleatoire(TypeItem type) {
        List<String> nomsPossibles = PreparationJeu.getNomsItemsParType(type);
        
        if (nomsPossibles.isEmpty()) {
            // Aucun item disponible pour ce type
            return null;
        }

        String nom = nomsPossibles.get(random.nextInt(nomsPossibles.size()));

        // Génération de l'item avec des effets aléatoires
        Effet effet = Effet.values()[random.nextInt(Effet.values().length)];
        int valeur = random.nextInt(10) + 1;
        Item item = new Item(nom, type, effet, valeur, true);
        
        return item;
    }
    
 // ======================================================================================
    
    // 2- CONSULTER L'INVENTAIRE
    private static void consulterInventaire() {
        System.out.println("\n"+ TIRETS +"Inventaire"+ TIRETS);
        joueur.afficherInventaire();
    }
    
 // ======================================================================================
    
    // 3- AFFICHER LA LISTE DES RECETTES ET CRAFTER
    private static void afficherTableDeCraft() {
        System.out.println("\n=== Table de Craft ===");

        int index = 1;
        for (Map.Entry<List<String>, String> entry : tableDeCraft.entrySet()) {
            List<String> ingredients = entry.getKey();
            String resultat = entry.getValue();
            System.out.println(index + ". " + ingredients.get(0) + " + " + ingredients.get(1) + " => " + resultat);
            index++;
        }
    }
    
 // ======================================================================================
    
    // 4- ENREGISTRER L'ETAT DE LA MAP DANS LE FICHIER
    private static void actualiserLaMap() {
    	terrain.enregistrerMap("terrain.txt", joueur);
    }

 // ======================================================================================
    
    // 5- UTILISER UN ITEM
    private static void utiliserItem() {
        System.out.println("\n"+ TIRETS +"Utiliser un item"+ TIRETS);
        // Affichage de la liste des items avec une numérotation
        joueur.afficherInventaire();
        System.out.println("(-1)- Annuler");
        System.out.print("Choix : ");
        
        int index = scanner.nextInt();
        scanner.nextLine();
        
        index --; // Car j'ai créé une liste avec les éléments de l'inventaire.
        if (index >= 0 && index < joueur.getInventaire().size()) {
            Item item = joueur.getInventaire().get(index);
            joueur.utiliserItem(item.getNom());
        } else if (index != -1) {
            System.out.println("Index invalide.");
        }
    }
    
// ======================================================================================
    
    // 6- AFFICHER LES DETAILS SUR LE JOUEUR
    private static void afficherProfilJoueur() {
    	System.out.println(joueur.toString());
    }
}
