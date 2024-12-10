package composants;


public abstract class Entite {
	protected int axe_x;
	protected int axe_y;
    protected String nom;
    protected int pointsDeVie;
    protected int pointsDattaque;
    protected int pointsDeDefense;

    protected Item mainPrincipale;
    protected Item mainSecondaire;

    public Entite(String nom, int pointsDeVie, int pointsDattaque, int pointsDeDefense, int axe_x, int axe_y) {
    	this.axe_x = axe_x;
    	this.axe_y = axe_y;
        this.nom = nom;
        this.pointsDeVie = pointsDeVie;
        this.pointsDattaque = pointsDattaque;
        this.pointsDeDefense = pointsDeDefense;
    }
    
    public Entite(String nom, int pointsDeVie, int pointsDattaque, int pointsDeDefense, int axe_x, int axe_y, Item mainPrincipale, Item mainSecondaire) {
    	this(nom, pointsDeVie, pointsDattaque, pointsDeDefense, axe_x, axe_y);
    	this.mainPrincipale = mainPrincipale;
    	this.mainSecondaire = mainSecondaire;
    }

    public void attaquer(Entite cible) {
        int degats = Math.max(0, pointsDattaque - cible.pointsDeDefense);
        cible.pointsDeVie -= degats;
        System.out.println(nom + " attaque " + cible.nom + " et inflige " + degats + " dégâts !");
    }

    public boolean estEnVie() {
        return pointsDeVie > 0;
    }
    

    public int getPointsDeVie() {
    	return this.pointsDeVie;
    }
    
    public int getPointsDattaque() {
    	return this.pointsDeVie;
    }
    
    public int getPointsDeDefense() {
    	return this.pointsDeVie;
    }

    public void setPointsDeVie(int pointsDeVie) {
        this.pointsDeVie = Math.max(0, pointsDeVie);
    }

    public void setPointsDattaque(int pointsAttaque) {
        this.pointsDattaque = Math.max(0, pointsAttaque);
    }

    public void setPointsDeDefense(int pointsDefense) {
        this.pointsDeDefense = Math.max(0, pointsDefense);
    }

    
    public int getAxe_x() {
		return axe_x;
	}

	public void setAxe_x(int axe_x) {
		this.axe_x = axe_x;
	}

	public int getAxe_y() {
		return axe_y;
	}

	public void setAxe_y(int axe_y) {
		this.axe_y = axe_y;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Item getMainPrincipale() {
		return mainPrincipale;
	}

	public void setMainPrincipale(Item mainPrincipale) {
		this.mainPrincipale = mainPrincipale;
	}

	public Item getMainSecondaire() {
		return mainSecondaire;
	}

	public void setMainSecondaire(Item mainSecondaire) {
		this.mainSecondaire = mainSecondaire;
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Entité : [")
	      .append("Nom = ").append(nom)
	      .append(", Position = (").append(axe_x).append(", ").append(axe_y).append(")")
	      .append(", PV = ").append(pointsDeVie)
	      .append(", PA = ").append(pointsDattaque)
	      .append(", PD = ").append(pointsDeDefense)
	      .append("]");

	    sb.append("\nÉquipement : ");
	    sb.append("\n  Main Principale : ");
	    sb.append(mainPrincipale != null ? mainPrincipale : "Aucun");
	    sb.append("\n  Main Secondaire : ");
	    sb.append(mainSecondaire != null ? mainSecondaire : "Aucun");

	    return sb.toString();
	}

}
