/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pechemoulesfrites.IHM;

import pechemoulesfrites.Modele.Objet.Frites;
import pechemoulesfrites.Modele.Objet.Objet;

/**
 *
 * @author Maxime
 */
public class IHM_Frite extends IHM_Objet {
    private Frites frite ; // frite associée à l'image
    /**
     * Contructeur de la classe frite qui permet de récupérer l'adresse de l'image
     * @param object
     * @param taille
     * @param x
     * @param y 
     */
    public IHM_Frite(Objet object, int taille, int x, int y) {
        super(object, taille, x, y, "pechemoulesfrites/Ressources/frite.png");
        frite = (Frites) object;
    }
    /**
     * Getter qui renvoie un objet
     * @return la frite associé a l'image
     */
    @Override
    public Objet getObjet() {
        return this.frite;
    }
    
}
