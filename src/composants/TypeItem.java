package composants;

public enum TypeItem {
    // Catégories principales
    ARMES("ARMES"),
    OUTILS("OUTILS"), 
    ARMURES("ARMURES"), 
    BLOCS("BLOCS"),
    ALIMENTS("ALIMENTS"), 
    RESSOURCES("RESSOURCES"),     
    DECORATIONS("DECORATIONS"),     
    REDSTONE("REDSTONE"),    
    POTIONS("POTIONS"),   
    LIVRES("LIVRES"),
    OBJETS_SPECIAUX("OBJETS SPECIAUX"); 

	
    private final String nom;

    // Constructeur
    TypeItem(String nom) {
        this.nom = nom;
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

