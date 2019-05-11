package pechemoulesfrites.Modele.Objet;

import pechemoulesfrites.Modele.Carte.Case;
import pechemoulesfrites.Modele.Enum.Enum_Objet;


/**
 *
 * @author ajaug
 */
public abstract class Objet {
    private final Case laCase;
    private final Enum_Objet type;
    
    /**
     * Constructeur d'Objet
     * @param type le type de l'objet sous forme de Enum_Objet
     * @param c la Case de l'objet
     */
    public Objet(Enum_Objet type, Case c){
        this.type = type;
        this.laCase = c;
    }
    
    /**
     * Permet d'obtenir le type de l'objet
     * @return le type de l'objet sous forme de Enum_Objet
     */
    public Enum_Objet getType(){
        return type;
    }
    
    /**
     * Permet d'obtenir la Case de l'objet
     * @return la Case de l'objet
     */
    public Case getCase(){
        return laCase;
    }
    
    @Override
    public abstract String toString();
}
