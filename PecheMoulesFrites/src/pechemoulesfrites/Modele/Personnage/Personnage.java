/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pechemoulesfrites.Modele.Personnage;

import pechemoulesfrites.Gestionnaire;
import pechemoulesfrites.Modele.Carte.Case;
import pechemoulesfrites.Modele.Enum.Enum_Action;
import static pechemoulesfrites.Modele.Enum.Enum_Action.*;
import pechemoulesfrites.Modele.Enum.Enum_IA;
import static pechemoulesfrites.Modele.Enum.Enum_Objet.Frites;
import pechemoulesfrites.Modele.Objet.Moules;
import pechemoulesfrites.Modele.Objet.Objet;
import pechemoulesfrites.Modele.Personnage.IA.Fabrique_IA;
import pechemoulesfrites.Modele.Personnage.IA.IA;

/**s
 *
 * @author ajaug
 */
public class Personnage {
    private Case laCase;
    private boolean saut;
    private int score;
    private Enum_Action derniereAction;
    private final IA ia;

    
    /**
     * Constructeur de la classe Personnage
     * @param ia est L'IA qu'on souhaite attribuer au personnage
     * @param c  est la case où le personnage sera placé lors de sa création
     */
    public Personnage(Enum_IA ia, Case c) {
        this.laCase = c;
        this.saut = false;
        this.score = 0;
        derniereAction = JeFaisRien;
        this.ia = Fabrique_IA.cree(ia, this);
    }

    @Override
    public String toString() {
        return getCase()+" M=" + score + " F="+saut;
    }
    /**
     * Permet de retourner l'ordonnée de la case où se trouve le Personnage
     * @return un entier correspondant à l'ordonnée d'une case
     */
    public int getColonne(){
        return laCase.getColonne();
    }
    
    /**
     * Permet de retourner l'abcisse de la case où se trouve le Personnage
     * @return un entier correspondant à l'abcisse d'une case
     */
    public int getLigne(){
        return laCase.getLigne();
    }
    
    /**
     * Permet de retourner l'IA du Personnage
     * @return un type enumératif correspondant à une IA 
     */
    public IA getIa() {
        return ia;
    }
    
    /**
     * Permet de retourner la case où se trouve le Personnage
     * @return un objet Case
     */
    public Case getCase() {
        return laCase;
    }
    
    /**
     * Permet de modifier la case sur laquelle se trouve le Personnage
     * @param laCase la nouvelle position du Personnage
     */
    public void setCase(Case laCase) {
        this.laCase = laCase;
    }
    /**
     * Permet de retourner le score du Personnage
     * @return un entier
     */
    public int getScore(){
        return score;
    }
    /**
     * Permet de dire au Personnage qu'il n'est plus capabe de sauter
     */
    public void saute(){
        saut = false;
    }
    /**
     * Permet de dire au Personnage qu'il est capable de sauter
     * @return un booléen 
     */
    public boolean hasFrites(){
        return saut;
    }
    /**
     * Permet au Personnage d'effectuer une action 
     * @param action, Enum_Action à réaliser
     */
    public void action(Enum_Action action){
        Gestionnaire.getCarte().actionPerso(this, action);
    }
    /**
     * Permet de retourner la dernière action réalisée par le Personnage 
     * @return une Enum_Action
     */
    public Enum_Action getLastAction(){
        return this.derniereAction;
    }
    /**
     * Permet de mettre à jour la dernière action réalisée par le Personnage
     * @param action  Enum_Action qui remplace l'ancienne "dernière actioné
     */
    public void setLastAction(Enum_Action action){
        this.derniereAction = action;
    }
    
    /**
     * Permet de ramasser l'objet qui se trouve sur la même case et de le traiter
     * @param unObjet la frite ou la moule à ramasser 
     */
    public void gerer(Objet unObjet){
        if(unObjet != null){
            if(unObjet.getType() == Frites){
                saut = true;
                ia.retirerSurveillance(unObjet);
            } else {
                score += ((Moules) unObjet).getQuantite();
            }
        }
    }
    
    
    
}
