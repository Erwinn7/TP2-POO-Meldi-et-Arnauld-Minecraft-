package composants;

import java.net.Socket;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jeu.PreparationJeu;
import jeu.ServeurPvP;

public class Joueur extends Entite {

    private List<Item> inventaire;
    private Random random;

    // Direction
    public final static String HAUT = "HAUT";
    public final static String BAS = "BAS";
    public final static String GAUCHE = "GAUCHE";
    public final static String DROITE = "DROITE";

    public Joueur(String nom, int pointsDeVie, int pointsDattaque, int pointsDeDefense, int axe_x, int axe_y,
            Item main1, Item main2) {
        super(nom, pointsDeVie, pointsDattaque, pointsDeDefense, axe_x, axe_y, main1, main2);
        this.inventaire = new ArrayList<>();
    }

    public Joueur(String nom) {
        this(nom, 100, 10, 10, 1, 1, null, null);
    }

    public Joueur(String nom, int pointsDeVie, int pointsDattaque, int pointsDeDefense, int axe_x, int axe_y) {
        this(nom, pointsDeVie, pointsDattaque, pointsDeDefense, axe_x, axe_y, null, null);
    }

    public List<Item> getInventaire() {
        return inventaire;
    }

    public void setInventaire(List<Item> inventaire) {
        this.inventaire = inventaire;
    }

    public boolean possedeItem(String nomItem) {
        for (Item item : inventaire) {
            if (item.getNom().equalsIgnoreCase(nomItem)) {
                return true;
            }
        }
        return false;
    }

    public void deplacer(String direction, PreparationJeu preparationJeu) {
        // Obtenir les dimensions actuelles du terrain
        int largeur = preparationJeu.getLargeur();
        int hauteur = preparationJeu.getHauteur();
        List<List<Character>> terrain = preparationJeu.getTerrain();

        switch (direction.toUpperCase()) {
            case "HAUT":
                if (axe_x == 0) {
                    // Ajouter une nouvelle ligne en haut
                    List<Character> nouvelleLigne = preparationJeu.genererNouvelleLigne(largeur);
                    terrain.add(0, nouvelleLigne);
                    hauteur++; // Mettre à jour la hauteur
                } else {
                    axe_x--;
                }
                break;

            case "BAS":
                if (axe_x == hauteur - 1) {
                    // Ajouter une nouvelle ligne en bas
                    List<Character> nouvelleLigne = preparationJeu.genererNouvelleLigne(largeur);
                    terrain.add(nouvelleLigne);
                    hauteur++; // Mettre à jour la hauteur
                }
                axe_x++;
                break;

            case "GAUCHE":
                if (axe_y == 0) {
                    // Ajouter une nouvelle colonne à gauche
                    for (List<Character> ligne : terrain) {
                        ligne.add(0, preparationJeu.genererNouveauCaractere());
                    }
                    largeur++; // Mettre à jour la largeur
                } else {
                    axe_y--;
                }
                break;

            case "DROITE":
                if (axe_y == largeur - 1) {
                    // Ajouter une nouvelle colonne à droite
                    for (List<Character> ligne : terrain) {
                        ligne.add(preparationJeu.genererNouveauCaractere());
                    }
                    largeur++; // Mettre à jour la largeur
                }
                axe_y++;
                break;

            default:
                System.out.println("Direction invalide !");
                return;
        }

        System.out.println(nom + " s'est déplacé vers le " + direction.toLowerCase() +
                ". Nouvelle position : (" + axe_x + ", " + axe_y + ")");

        preparationJeu.setLargeur(largeur);
        preparationJeu.setHauteur(hauteur);
    }

    public void ajouterItem(Item item) {
        inventaire.add(item);
        System.out.println(item.getNom() + " a été ajouté à votre inventaire.");
    }

    public void afficherInventaire() {
        System.out.println("Inventaire de " + nom + " :");
        int index = 1;
        for (Item item : inventaire) {
            System.out.println("(" + index + ")- " + item);
            index++;
        }
        if (index == 1)
            System.out.println("(Votre inventaire est vide.)");
    }

    public Item trouverItemDansInventaire(String nomItem) {
        for (Item item : inventaire) {
            if (item.getNom().equalsIgnoreCase(nomItem)) {
                return item;
            }
        }
        return null;
    }

    public void utiliserItem(String nomItem) {
        Item ajouter = null;
        for (Iterator<Item> iterator = inventaire.iterator(); iterator.hasNext();) {
            Item item = iterator.next();
            if (item.getNom().equalsIgnoreCase(nomItem)) {
                switch (item.getType()) {
                    // Logique pour les armes
                    case ARMES:
                        if (mainPrincipale == null) {
                            mainPrincipale = item;
                            appliquerEffets(item, true);
                            System.out.println(nom + " a équipé l'arme dans la main principale.");
                        } else {
                            System.out.println(nom + " a déjà une arme équipée. Remplacement en cours...");
                            retirerEffets(mainPrincipale);

                            ajouter = mainPrincipale;

                            mainPrincipale = item;
                            appliquerEffets(item, true);

                            System.out.println(nom + " a remplacé l'arme dans la main principale.");
                        }
                        iterator.remove(); // Retirer l'item utilisé de l'inventaire
                        break;

                    // Logique pour les armures
                    case ARMURES:
                        if (mainSecondaire == null) {
                            // Si aucune armure dans la main secondaire, on équipe directement
                            mainSecondaire = item;
                            appliquerEffets(item, true);
                            System.out.println(nom + " a équipé l'armure dans la main secondaire.");
                        } else {
                            // Si une armure est déjà équipée dans la main secondaire, on la remplace
                            System.out.println(nom + " a déjà une armure équipée. Remplacement en cours...");
                            retirerEffets(mainSecondaire);

                            ajouter = mainSecondaire;

                            mainSecondaire = item;
                            appliquerEffets(item, true);
                            System.out.println(nom + " a remplacé l'armure dans la main secondaire.");
                        }
                        iterator.remove(); // Retirer l'item utilisé de l'inventaire
                        break;

                    case ALIMENTS:
                        iterator.remove();
                        appliquerEffets(item, true);
                        System.out.println(nom + " a mangé " + item.getNom() + " et a gagné " + item.getValeur()
                                + " points de vie.");
                        break;

                    case POTIONS:
                        System.out.println(nom + " utilise la potion " + item.getNom());
                        iterator.remove();
                        appliquerEffets(item, true);
                        break;

                    case RESSOURCES:
                        System.out.println(nom + " utilise la ressource " + item.getNom());
                        iterator.remove();
                        appliquerEffets(item, true);
                        break;

                    default:
                        System.out.println("Type inconnu ou action non applicable.");
                        break;
                }
            }
        }

        if (ajouter != null && (mainPrincipale != null || mainSecondaire != null)) {
            inventaire.add(ajouter);
            return;
        } else if (ajouter == null && mainPrincipale == null && mainSecondaire == null)
            System.out.println("Vous ne possédez pas cet item.");
    }

    private void appliquerEffets(Item item, boolean appliquer) {
        // Ajouter (1) ou retirer (-1) les effets
        int facteur = appliquer ? 1 : -1;
        switch (item.getEffet()) {
            case AUGMENTE_PV:
                pointsDeVie += facteur * item.getValeur();
                break;
            case DIMINUE_PV:
                pointsDeVie -= facteur * item.getValeur();
                break;
            case AUGMENTE_PA:
                pointsDattaque += facteur * item.getValeur();
                break;
            case DIMINUE_PA:
                pointsDattaque -= facteur * item.getValeur();
                break;
            case AUGMENTE_PD:
                pointsDeDefense += facteur * item.getValeur();
                break;
            case DIMINUE_PD:
                pointsDeDefense -= facteur * item.getValeur();
                break;
        }
    }

    private void retirerEffets(Item item) {
        appliquerEffets(item, false);
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

    public void crafter(Item item1, Item item2) {

        Map<List<String>, String> tableDeCraft = PreparationJeu.getTableDeCraft();

        // Chercher une combinaison correspondant aux deux items
        for (Map.Entry<List<String>, String> entry : tableDeCraft.entrySet()) {
            List<String> ingredients = entry.getKey();
            String resultatNom = entry.getValue();

            // Vérifier si les deux items correspondent à une recette
            if ((ingredients.contains(item1.getNom()) && ingredients.contains(item2.getNom()))
                    && !item1.getNom().equals(item2.getNom())) {

                // Supprimer les items utilisés de l'inventaire
                inventaire.remove(item1);
                inventaire.remove(item2);

                // Créer le nouvel item
                int nouvelleValeur = (item1.getValeur() + item2.getValeur()) / 2;
                Effet effet = item1.getEffet();
                Item nouvelItem = new Item(resultatNom, item1.getType(), effet, nouvelleValeur,
                        item2.getEstChangeable());

                inventaire.add(nouvelItem);

                return;
            }
        }

        System.out.println("Impossible de crafter avec ces items. Vérifiez la table de craft.");
    }

    public void seConnecter() {
        try (Socket socket = new Socket("localhost", ServeurPvP.SERVER_PORT)) {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(this.nom);
            oos.close();
            socket.close();
            // BufferedReader in = new BufferedReader(new
            // InputStreamReader(socket.getInputStream()));
            // System.out.println(in.readLine()); // Message initial du serveur

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
