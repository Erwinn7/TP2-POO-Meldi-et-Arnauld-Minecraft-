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

public class PreparationJeu {

    private int largeur;
    private int hauteur;
    private List<List<Character>> terrain;
    private final Random random;
    
    private static final Map<List<String>, String> tableDeCraft = new HashMap<>();


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
            	// Une case vide est représentée par le caractère '.'
                ligne.add('.');
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


    // Générer aléatoirement des éléments sur le terrain 
    public void genererElements() {
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                int choix = random.nextInt(100);
                if (choix < 10) {
                    terrain.get(i).set(j, 'M'); // 'M' pour les monstres
                } else if (choix < 20) { // 'P' pour les potions
                    terrain.get(i).set(j, 'P');
                }
                // Sinon, la case reste vide '.'
            }
        }
    }
    
    public List<Character> genererNouvelleLigne(int largeur) {
        List<Character> ligne = new ArrayList<>();
        for (int i = 0; i < largeur; i++) {
            ligne.add(genererNouvelElement());
        }
        return ligne;
    }
    
    public Character genererNouvelElement() {
        // Définir les types possibles (vide, monstre, potion, etc.)
        char[] elements = {'.', 'M', 'P'};
        return elements[random.nextInt(elements.length)];
    }

    public void enregistrerMap(String cheminFichier, Joueur joueur) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier))) {
            for (int i = 0; i < terrain.size(); i++) {
                for (int j = 0; j < terrain.get(i).size(); j++) {
                    if (i == joueur.getAxe_x() && j == joueur.getAxe_y()) {
                        // Le joueur est identifié par 'J'
                        writer.write('J');
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


    // Écrire l'état du terrain dans un fichier texte
    public void ecrireTerrainDansFichier(String nomFichier) {
        try (FileWriter writer = new FileWriter(nomFichier)) {
            for (List<Character> ligne : terrain) {
                for (Character c : ligne) {
                    writer.write(c); // Écrire chaque caractère
                }
                writer.write("\n"); // Nouvelle ligne après chaque ligne de la grille
            }
            System.out.println("Terrain sauvegardé dans le fichier : " + nomFichier);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
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

