package composants;

/**
 * @param nom : Le nom de l'item (ex : "Épée en fer")
 * @param type : Le type de l'item (ex : "arme", "nourriture")
 * @param effet : L'effet de l'item défini par l'énumération <strong>Effet</strong>
 * @param valeur : la valeur de cet effet
 */
public class Item {
    private String nom;
    private TypeItem type;
    private Effet effet;
    private int valeur;
    private boolean estChangeable;

    public Item(String nom, TypeItem type, Effet effet, int valeur, boolean estChangeable) {
        this.nom = nom;
        this.type = type;
        this.effet = effet;
        this.valeur = valeur;
        this.estChangeable = estChangeable;
    }
    
    // Getters
    public String getNom() {
        return nom;
    }

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
    public TypeItem getType() {
        return type;
    }
    
	public void setType(TypeItem type) {
		this.type = type;
	}

	
    public Effet getEffet() {
        return effet;
    }

	public void setEffet(Effet effet) {
		this.effet = effet;
	}
	
	
    public int getValeur() {
        return valeur;
    }

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
    
    public boolean getEstChangeable() {
		return estChangeable;
	}

	public void setEstChangeable(boolean estChangeable) {
		this.estChangeable = estChangeable;
	}
	

    // Appliquer l'effet sur un joueur
    public void utiliserItem(Joueur joueur) {
        switch (effet) {
            case AUGMENTE_PV:
                joueur.setPointsDeVie(joueur.getPointsDeVie() + valeur);
                System.out.println("Item");
                System.out.println(nom + " a augmenté vos points de vie de " + valeur + ".");
                break;
            case DIMINUE_PV:
                joueur.setPointsDeVie(Math.max(0, joueur.getPointsDeVie() - valeur));
                System.out.println(nom + " a diminué vos points de vie de " + valeur + ".");
                break;
            case AUGMENTE_PA:
                joueur.setPointsDattaque(joueur.getPointsDattaque() + valeur);
                System.out.println(nom + " a augmenté vos points d'attaque de " + valeur + ".");
                break;
            case DIMINUE_PA:
                joueur.setPointsDattaque(Math.max(0, joueur.getPointsDattaque() - valeur));
                System.out.println(nom + " a diminué vos points d'attaque de " + valeur + ".");
                break;
            case AUGMENTE_PD:
                joueur.setPointsDeDefense(joueur.getPointsDeDefense() + valeur);
                System.out.println(nom + " a augmenté vos points de défense de " + valeur + ".");
                break;
            case DIMINUE_PD:
                joueur.setPointsDeDefense(Math.max(0, joueur.getPointsDeDefense() - valeur));
                System.out.println(nom + " a diminué vos points de défense de " + valeur + ".");
                break;
        }
    }
    

	@Override
    public String toString() {
        return "Item { " +
                "nom = '" + nom + '\'' +
                ", type = '" + type.getNom() + '\'' +
                ", effet = " + effet +
                ", valeur = " + valeur +
                ", changeable = "+ estChangeable +
                "}";
    }
}

