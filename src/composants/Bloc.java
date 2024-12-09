package composants;

public class Bloc {

    private String type;
    private boolean estSolide;
    private boolean estCassable;
    private boolean estConsommable;

    public Bloc(String type, boolean estSolide, boolean estCassable, boolean estConsommable) {
        this.type = type;
        this.estCassable = estCassable;
        this.estConsommable = estConsommable;
        this.estSolide = estSolide;
    }

    public String getType() {
        return type;
    }

    public boolean isestCassable() {
        return estCassable;
    }

    public boolean isestConsommable() {
        return estConsommable;
    }

    public boolean getEstSolide() {
        return estSolide;
    }

    @Override
    public String toString() {
        return "Bloc [type=" + type + ", estCassable=" + estCassable +
                ", estConsommable=" + estConsommable + ", estSolide=" + estSolide + "]";
    }
}
