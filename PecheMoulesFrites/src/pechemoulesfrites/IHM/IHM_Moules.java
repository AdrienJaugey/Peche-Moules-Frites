/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pechemoulesfrites.IHM;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import pechemoulesfrites.Modele.Objet.Moules;
import pechemoulesfrites.Modele.Objet.Objet;

/**
 *
 * @author md155007
 */
public class IHM_Moules extends IHM_Objet{
    private Moules moule;// objet moule associée à l'image
    /**
     * Constructeur de la classe objet qui fournis l'adresse de l'image et qui permet d'affichher le nombre de moule que l'image contient
     * @param object
     * @param taille
     * @param x
     * @param y 
     */
    public IHM_Moules(Moules object, int taille, int x, int y) {
        super(object, taille, x, y, "pechemoulesfrites/Ressources/moule.png");
        moule = object;
        Text score = new Text(String.valueOf(moule.getQuantite()));
        score.setX(x+taille/2.5);
        score.setY(y+taille/2);
        score.setFont(Font.font(null, FontWeight.BOLD, taille/2));
        score.setFill(Color.BEIGE.brighter());
        score.toFront();
        this.getChildren().add(score);
        
    }
    /**
     * Getter qui renvoie un objet
     * @return la moule associée à l'image
     */
    @Override
    public Objet getObjet() {
        return this.moule;
    }
    
}
