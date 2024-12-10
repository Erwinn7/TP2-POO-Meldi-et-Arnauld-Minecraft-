package composants;

public enum TypeItem {
    // Catégories principales
	ARMES("ARMES", 'A'),
    OUTILS("OUTILS", 'O'), 
    ARMURES("ARMURES", 'R'), 
    BLOCS("BLOCS", 'B'),
    ALIMENTS("ALIMENTS", 'N'), 
    RESSOURCES("RESSOURCES", 'S'),     
    DECORATIONS("DECORATIONS", 'D'),     
    REDSTONE("REDSTONE", 'E'),    
    POTIONS("POTIONS", 'P'),   
    LIVRES("LIVRES", 'L');

	
    private final String nom;
    private final char initiale;

    // Constructeur
    TypeItem(String nom, char initiale) {
        this.nom = nom;
        this.initiale = initiale;
    }

    // Getter pour les initiales
    public char getInitiale() {
        return initiale;
    }

    // Getter pour récupérer le nom lisible de la catégorie
    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return nom;
    }
}
