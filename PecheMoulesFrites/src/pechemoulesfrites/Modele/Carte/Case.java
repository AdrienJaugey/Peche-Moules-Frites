package pechemoulesfrites.Modele.Carte;

import java.util.ArrayList;
import pechemoulesfrites.Modele.Enum.Enum_Case;
import pechemoulesfrites.Modele.Objet.Objet;
import pechemoulesfrites.Modele.Personnage.Personnage;

/**
 *
 * @author ajaug
 */
public class Case {
    private final int colonne;
    private final int ligne;
    private final Enum_Case type;
    private Objet objet;
    private ArrayList<Personnage> personnages;
    
    /**
     * Constructeur de <code>Case</code>
     * @param x la colonne de la <code>Case</code>
     * @param y la ligne de la <code>Case</code>
     * @param type le type de la <code>Case</code> au format <code>Enum_Case</code>
     */
    public Case(int x, int y, Enum_Case type) {
        this.colonne = x;
        this.ligne = y;
        this.type = type;
        this.objet = null;
        this.personnages = new ArrayList<>();
    }
    
    /**
     * Permet d'obtenir le type de la <code>Case</code>
     * @return le type de la <code>Case</code>
     */
    public Enum_Case getType() {
        return type;
    }

    /**
     * Permet d'obtenir la colonne de la <code>Case</code>
     * @return 
     */
    public int getColonne() {
        return colonne;
    }
    
    /**
     * Permet d'obtenir la ligne de la <code>Case</code>
     * @return la ligne
     */
    public int getLigne() {
        return ligne;
    }
    
    /**
     * Permet d'ajouter un <code>Personnage</code> à la carte
     * @param p le <code>Personnage</code> à ajouter
     */
    public void addPerso(Personnage p){
        personnages.add(p);
    }
    
    /**
     * Permet de retirer un <code>Personnage</code> de la <code>Carte</code>
     * @param p le <code>Personnage</code> à retirer
     */
    public void removePerso(Personnage p){
        personnages.remove(p);
    }
    
    /**
     * Permet d'obtenir la liste des <code>Personnages</code> se trouvant sur cette <code>Case</code>.
     * @return 
     */
    public ArrayList<Personnage> getPerso(){
        return this.personnages;
    }
    
    /**
     * Permet de définir l'objet de la <code>Case</code>
     * @param o l'objet à définir
     */
    public void setObjet(Objet o){
        this.objet = o;
    }
    
    /**
     * Permet de connaître l'objet de la <code>Case</code>
     * @return l'objet de la <code>Case</code>
     */
    public Objet getObjet(){
        return objet;
    }
    
    /**
     * Renvoit l'objet de la case et le supprime
     */
    public void removeObjet(){
        objet = null;
    }
    
    @Override
    public String toString() {
        return "(" + colonne + "," + ligne + ")";
    }
    
    
}
