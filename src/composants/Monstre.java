package composants;

public class Monstre extends Entite {
    public Monstre(String nom, int axe_x, int axe_y, int pointsDeVie, int pointsDattaque, int pointsDeDefense) {
        super(nom, pointsDeVie, pointsDattaque, pointsDeDefense, axe_x, axe_y);
    }

    @Override
    public String toString() {
        return "Monstre [Nom = " + nom + ", Position = (" + axe_x + ", " + axe_y + "), PV = " 
               + pointsDeVie + ", PA = " + pointsDattaque + ", PD = " + pointsDeDefense + "]";
    }
}
