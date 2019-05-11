package pechemoulesfrites.Modele.Objet;

import pechemoulesfrites.Modele.Enum.Enum_Objet;
import pechemoulesfrites.Modele.Carte.Case;


/**
 *
 * @author ajaug
 */
public abstract class Objet {
    private Case laCase;
    private Enum_Objet type;
    
    /**
     * Constructeur d'<code>Objet</code>
     * @param type le type de l'objet sous forme de <code>Enum_Objet</code>
     * @param c la <code>Case</code> de l'objet
     */
    public Objet(Enum_Objet type, Case c){
        this.type = type;
        laCase = c;
    }
    
    /**
     * Permet d'obtenir le type de l'objet
     * @return le type de l'objet sous forme de <code>Enum_Objet</code>
     */
    public Enum_Objet getType(){
        return type;
    }
    
    /**
     * Permet d'obtenir la <code>Case</code> de l'objet
     * @return la <code>Case</code> de l'objet
     */
    public Case getCase(){
        return laCase;
    }
    
    @Override
    public abstract String toString();
}
