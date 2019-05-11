/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pechemoulesfrites.Modele.Objet;

import pechemoulesfrites.Modele.Carte.Case;
import static pechemoulesfrites.Modele.Enum.Enum_Objet.Frites;

/**
 *
 * @author ajaug
 */
public class Frites extends Objet{

    public Frites(Case c) {
        super(Frites, c);
    }
    
    @Override
    public String toString() {
        return "Frite "+getCase();
    }
    
}
