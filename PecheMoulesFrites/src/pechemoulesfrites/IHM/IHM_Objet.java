/*
 * Objet IHM du jeu
 */
package pechemoulesfrites.IHM;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import pechemoulesfrites.Modele.Objet.Moules;
import pechemoulesfrites.Modele.Objet.Objet;

/**
 *
 * @author Maxime
 */
public abstract class IHM_Objet extends Parent{
    protected ImageView image; //Image vu a l'écran doit être remplacer par une vrai image
        
    /**
     * Constructeur de la classe
     * @param object l'objet associer à l'image
     * @param o le type de l'objet
     * @param taille le taille de l'image à l'écran
     * @param x la position x sur l'écran
     * @param y la position y sur l'écran
     */    
    public IHM_Objet(Objet object,int taille, int x, int y, String adresse){
        image = new ImageView(new Image(adresse));
        image.setFitHeight(taille);
        image.setPreserveRatio(true);
        image.setX(x);
        image.setY(y);
        this.getChildren().addAll(image);
        
    }
    /**
     * Permet de récupérer l'objet associer à l'image
     * @return l'attribut objet
     */ 
    public abstract Objet getObjet();

}
