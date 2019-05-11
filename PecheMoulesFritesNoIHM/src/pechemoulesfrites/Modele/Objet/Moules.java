/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pechemoulesfrites.Modele.Objet;

import pechemoulesfrites.Modele.Carte.Case;
import static pechemoulesfrites.Modele.Enum.Enum_Objet.Moules;

/**
 *
 * @author ajaug
 */
public class Moules extends Objet {
    private int quantite;
    
    /**
     * Constructeur de Moules
     * @param quantite nombre de moules présentes au même endroit, int compris entre 1 et 100
     * @param laCase la Case de ces Moules
     */
    public Moules(int quantite, Case laCase) {
        super(Moules, laCase);
        if(quantite > 0 && quantite <= 100){
            this.quantite = quantite;
        } else {
            if(quantite <= 0){
                this.quantite = 1;
            } else {
                this.quantite = 100;
            }
        }
    }
    
    /**
     * Permet de savoir combien il y a de moules à cet endroit
     * @return le nombre de Moules
     */
    public int getQuantite(){
        return quantite;
    }
    
    @Override
    public String toString() {
        return "Moules Score=" + quantite + " " + getCase();
    }
    
}
