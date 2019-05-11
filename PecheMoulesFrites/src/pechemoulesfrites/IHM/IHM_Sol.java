/*
 * Sol du jeu
 */
package pechemoulesfrites.IHM;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pechemoulesfrites.Modele.Enum.Enum_Case;
import static pechemoulesfrites.Modele.Enum.Enum_Case.Mur;



/**
 *
 * @author Maxime
 */
public class IHM_Sol extends Parent{
    /**
     * Constructeur de la classe
     * @param e le type de la case
     * @param taille la taille de la case
     * @param x la position X de la case
     * @param y la position Y de la case
     */
    public IHM_Sol(Enum_Case e ,int taille, int x, int y){
        String adresse = "";
        switch(e){ // recupère l'image mur ou sol en fonction du type
            case Mur:{
                adresse = "pechemoulesfrites/Ressources/mur.png";
            }
            break;
            case Sol :{
                adresse = "pechemoulesfrites/Ressources/sol.png";
            }    
            break;
        }
        ImageView laCase = new ImageView(new Image(adresse));
        laCase.setFitHeight(taille);
        laCase.setPreserveRatio(true);
        
        laCase.setX(x);
        laCase.setY(y);
        this.getChildren().add(laCase);
                
    }

    
}
