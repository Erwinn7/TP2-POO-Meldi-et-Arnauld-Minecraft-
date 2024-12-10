package jeu;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import composants.Joueur;
import composants.Monstre;
import composants.TypeItem;

public class PreparationJeu {

    private int largeur;
    private int hauteur;
    private List<List<Character>> terrain;
    private final Random random;
    
    public final static char BLOC = '_';
    public final static char MONSTRE = 'M';
    public final static char JOUEUR = 'J';
    
    private static final Map<List<String>, String> tableDeCraft = new HashMap<>();
    
    private static final Map<TypeItem, List<String>> itemsSupplementaires = Map.of(
    	    TypeItem.ARMES, Arrays.asList("Arc long", "Dague en acier"),
    	    TypeItem.ARMURES, Arrays.asList("Bouclier léger", "Plastron en cuir renforcé"),
    	    TypeItem.BLOCS, Arrays.asList("Marbre", "Granite"),
    	    TypeItem.ALIMENTS, Arrays.asList("Fraise", "Poisson fumé"),
    	    TypeItem.POTIONS, Arrays.asList("Potion de vitesse", "Potion de vision nocturne"),
    	    TypeItem.LIVRES, Arrays.asList("Tome ancien", "Manuel de survie"),
    	    TypeItem.RESSOURCES, Arrays.asList("Cristal magique", "Minerai de titane"),
    	    TypeItem.OUTILS, Arrays.asList("Pelle robuste", "Clé anglaise"),
    	    TypeItem.DECORATIONS, Arrays.asList("Table en bois", "Statue en pierre"),
    	    TypeItem.REDSTONE, Arrays.asList("Lampe de redstone", "Répéteur")
    );

    public PreparationJeu(int largeur, int hauteur) {
        if (largeur > 10)	this.largeur = largeur;
        else this.largeur = 10;
        
        if (hauteur > 10)	this.hauteur = hauteur;
        else this.hauteur = 10;
        
        this.terrain = new ArrayList<>();
        this.random = new Random();
        initialiserTerrain();
    }

    // Initialiser le terrain avec des cases vides
    private void initialiserTerrain() {
        for (int i = 0; i < hauteur; i++) {
            List<Character> ligne = new ArrayList<>();
            for (int j = 0; j < largeur; j++) {
            	// Une case vide est représentée par le caractère '_'
                ligne.add(BLOC);
            }
            terrain.add(ligne);
        }
    }
    
    // Getters et setters
    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public List<List<Character>> getTerrain() {
        return terrain;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }
    
    public Character getContenuCase(int ligne, int colonne) {
    	return terrain.get(ligne).get(colonne);
    }
    
    public void setContenuCase(int ligne, int colonne, char caractere) {
    	terrain.get(ligne).set(colonne, caractere);
    }

    // Générer aléatoirement des éléments sur le terrain 
    public void genererElements() {
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
            	terrain.get(i).set(j, genererNouveauCaractere());
            }
        }
    }
    
    public List<Character> genererNouvelleLigne(int largeur) {
        List<Character> ligne = new ArrayList<>();
        for (int i = 0; i < largeur; i++) {
            ligne.add(genererNouveauCaractere());
        }
        return ligne;
    }
    
    public Character genererNouveauCaractere() {
        char[] elements = {
        		BLOC,
        		TypeItem.POTIONS.getInitiale(),
        		BLOC, BLOC, BLOC,
        		TypeItem.ARMES.getInitiale(),
        		BLOC,BLOC, BLOC,
        		MONSTRE,
        		MONSTRE,
        		TypeItem.ALIMENTS.getInitiale(),
        		BLOC, BLOC, BLOC, BLOC,
        		TypeItem.ARMURES.getInitiale(),
        		BLOC, BLOC, 
        		MONSTRE,
        		TypeItem.BLOCS.getInitiale(),
        		BLOC, BLOC, 
        		TypeItem.BLOCS.getInitiale(),
        		BLOC, BLOC, 
        		TypeItem.ARMES.getInitiale(),
        		BLOC, BLOC, 
        		TypeItem.ALIMENTS.getInitiale(),
        		BLOC, BLOC, BLOC, BLOC, 
        		BLOC, BLOC, BLOC, 
        		TypeItem.BLOCS.getInitiale(),
        		};
        return elements[random.nextInt(elements.length)];
    }
    public static List<String> getNomsItemsParType(TypeItem type) {
        List<String> nomsItems = new ArrayList<>();

        // Ajouter les items provenant de la table de craft
        for (Map.Entry<List<String>, String> entry : tableDeCraft.entrySet()) {
            // Parcourir les matériaux (keys) et le produit (value)
            for (String materiau : entry.getKey()) {
                if (determinerTypeItem(materiau) == type) {
                    nomsItems.add(materiau);
                }
            }
            if (determinerTypeItem(entry.getValue()) == type) {
                nomsItems.add(entry.getValue());
            }
        }

        // Ajouter les items supplémentaires
        if (itemsSupplementaires.containsKey(type)) {
            nomsItems.addAll(itemsSupplementaires.get(type));
        }

        // Supprimer les doublons pour éviter les répétitions inutiles
        return nomsItems.stream().distinct().toList();
    }


    public static TypeItem determinerTypeItem(String nomItem) {
        if (nomItem.contains("Épée") || nomItem.contains("Hache") || nomItem.contains("Pioche") || nomItem.contains("Bouclier")) {
            return TypeItem.ARMES;
        } else if (nomItem.contains("Armure") || nomItem.contains("Plastron")) {
            return TypeItem.ARMURES;
        } else if (nomItem.contains("Bloc") || nomItem.contains("Marbre") || nomItem.contains("Granite")) {
            return TypeItem.BLOCS;
        } else if (nomItem.contains("Potion")) {
            return TypeItem.POTIONS;
        } else if (nomItem.contains("Pomme") || nomItem.contains("Pain") || nomItem.contains("Carotte") || nomItem.contains("Fraise") || nomItem.contains("Poisson")) {
            return TypeItem.ALIMENTS;
        } else if (nomItem.contains("Livre") || nomItem.contains("Tome") || nomItem.contains("Manuel")) {
            return TypeItem.LIVRES;
        } else if (nomItem.contains("Cristal") || nomItem.contains("Minerai")) {
            return TypeItem.RESSOURCES;
        } else if (nomItem.contains("Pelle") || nomItem.contains("Clé")) {
            return TypeItem.OUTILS;
        } else if (nomItem.contains("Table") || nomItem.contains("Statue")) {
            return TypeItem.DECORATIONS;
        } else if (nomItem.contains("Redstone") || nomItem.contains("Lampe") || nomItem.contains("Répéteur")) {
            return TypeItem.REDSTONE;
        } else {
            return TypeItem.RESSOURCES; // Par défaut
        }
    }


    public static List<String> getNomsItemsParTypeOld(TypeItem type) {
        switch (type) {
            case ARMES: return Arrays.asList("Épée", "Hache", "Arc");
            case ARMURES: return Arrays.asList("Casque", "Plastron", "Jambières", "Bottes");
            case BLOCS: return Arrays.asList("Pierre", "Bois", "Fer");
            case ALIMENTS: return Arrays.asList("Pomme", "Pain", "Carotte");
            case POTIONS: return Arrays.asList("Potion de soin", "Potion de force");
            default: return Arrays.asList("Item inconnu");
        }
    }
    
    public static Monstre genererMonstreAleatoire() {
        // Liste de noms de monstres
        List<String> nomsDeMonstres = Arrays.asList("Zombie", "Squelette", "Creeper", "Enderman", "Araignée");

        // Choisir un nom aléatoire
        String nom = nomsDeMonstres.get(new Random().nextInt(nomsDeMonstres.size()));

        // Générer des points de vie, d'attaque et de défense aléatoires
        int pointsDeVie = 20 + new Random().nextInt(30); // entre 20 et 50
        int pointsDattaque = 5 + new Random().nextInt(10); // entre 5 et 15
        int pointsDeDefense = 3 + new Random().nextInt(8); // entre 3 et 10

        // Générer une position aléatoire sur le terrain (optionnel)
        int positionX = new Random().nextInt(10); // Changez les limites en fonction de votre terrain
        int positionY = new Random().nextInt(10);

        // Créer et retourner un nouveau monstre
        return new Monstre(nom, positionX, positionY, pointsDeVie, pointsDattaque, pointsDeDefense);
    }

    
    public void enregistrerMap(String cheminFichier, Joueur joueur) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier))) {
            for (int i = 0; i < terrain.size(); i++) {
                for (int j = 0; j < terrain.get(i).size(); j++) {
                    if (i == joueur.getAxe_x() && j == joueur.getAxe_y()) {
                        // Le joueur est identifié par 'J'
                        writer.write(JOUEUR);
                    } else {
                        writer.write(terrain.get(i).get(j));
                    }
                }
                writer.newLine();
            }
            System.out.println("La carte a été enregistrée dans le fichier : " + cheminFichier);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'enregistrement de la carte : " + e.getMessage());
        }
    }

    // Afficher le terrain dans la console
    public void afficherTerrain() {
        for (List<Character> ligne : terrain) {
            for (Character c : ligne) {
                System.out.print(c);
            }
            System.out.println();
        }
    }
    
    static {
        // Initialisation des combinaisons de craft
        tableDeCraft.put(Arrays.asList("Lingot de fer", "Bâton en bois"), "Épée en fer");
        tableDeCraft.put(Arrays.asList("Lingot de fer", "Cuir"), "Armure en fer");
        tableDeCraft.put(Arrays.asList("Bloc de bois", "Bâton en bois"), "Hache en bois");
        tableDeCraft.put(Arrays.asList("Bloc de pierre", "Bâton en bois"), "Pioche en pierre");
        tableDeCraft.put(Arrays.asList("Bloc de cuivre", "Lingot de fer"), "Bouclier en cuivre");
        tableDeCraft.put(Arrays.asList("Potion basique", "Herbes médicinales"), "Potion de soin avancée");
        tableDeCraft.put(Arrays.asList("Bloc de diamant", "Lingot de fer"), "Épée en diamant");
        tableDeCraft.put(Arrays.asList("Bloc d'or", "Lingot de fer"), "Armure en or");
        tableDeCraft.put(Arrays.asList("Lingot d'argent", "Bloc de pierre"), "Hache en argent");
        tableDeCraft.put(Arrays.asList("Bloc de pierre", "Bloc de bois"), "Bâton renforcé");
    }

    public static Map<List<String>, String> getTableDeCraft() {
        return tableDeCraft;
    }
    
}

