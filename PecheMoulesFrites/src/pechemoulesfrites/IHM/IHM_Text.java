/*
 * Score du personnage
 */
package pechemoulesfrites.IHM;

import javafx.scene.Parent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pechemoulesfrites.Modele.Enum.Enum_Objet;

/**
 *
 * @author Maxime
 */
public class IHM_Text extends Parent{
    private Enum_Objet type; // L'Enumération qui correspond au type de l'objet
    private final Text text; // Le textee qui s'affiche sur l'écran
    private final int x,y;
    /**
     * Constructeur de la classe
     * @param o type de l'objet à construire
     * @param posX la position X de l'objet
     * @param posY la position Y de l'objet
     */
    public IHM_Text(Enum_Objet o, int posX, int posY){
        x= posX;
        y = posY;
        type = o;
        text = new Text(""); // Affiche le nombre de moule a l'écran
        text.setFont(Font.font(20));
        text.setX(10);
        text.setY(10);

        this.getChildren().add(text);
        this.setTranslateX(x);
        this.setTranslateY(y);
    }
    /**
     * Constructeur de texte sans le paramètre objet
     * @param posX
     * @param posY 
     */
    public IHM_Text(int posX, int posY){
        x= posX;
        y = posY;
        text = new Text(""); // Affiche le nombre de moule a l'écran
        text.setFont(Font.font(20));
        text.setX(10);
        text.setY(10);

        this.getChildren().add(text);
        this.setTranslateX(x);
        this.setTranslateY(y);
    }
    /**
     * Permet de mettre un texte dans l'attribut qui s'affiche à l'écran
     * @param texte le texte quee l'on veux mettre
     */
    public void setText(String texte){
        if(type == null){
            text.setText(texte);
        }else
        switch(type){
            case Frites: text.setText("Saut : "+texte);break;
            case Moules: text.setText("Score : "+texte);break;
        }
    }
}
