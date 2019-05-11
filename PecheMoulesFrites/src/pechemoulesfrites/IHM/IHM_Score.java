/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pechemoulesfrites.IHM;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pechemoulesfrites.Modele.Enum.Enum_Objet;

/**
 *
 * @author Maxime
 */
public class IHM_Score extends Parent{
    private final IHM_Text[] score; //Tableau qui contient les scores des moules et des frites
    private final ImageView personnage; // recupère l'image du personnage associé aux score
    /**
     * Constructeur de la classe score
     * @param img image du personnage associé au score
     */
    public IHM_Score(String img){
        Rectangle fond = new Rectangle();
        fond.setWidth(200);
        fond.setHeight(125);
        fond.setFill(Color.ORANGE);
        personnage = new ImageView(new Image(img+"/b2.png"));
        personnage.setFitHeight(35);
        personnage.setPreserveRatio(true);
        personnage.setX(10);
        personnage.setY(5);
        score = new IHM_Text[3];
        ajouterElement();
        this.getChildren().add(fond);
        for(IHM_Text texte : score){
            this.getChildren().add(texte);
        }
        this.getChildren().add(personnage);
    }
    /**
     * Ajoute les éléments nécessaire pour les informations du score du personnage
     */
    private void ajouterElement(){
        score[0] = new IHM_Text(40,20);
        score[1] = new IHM_Text(Enum_Objet.Moules,5,70);
        score[2] = new IHM_Text(Enum_Objet.Frites,5,100);  

    }
    /**
     * Remplis le texte des objets dans le tableau
     * @param IA IA du personnage concerné
     * @param moule le score du personnage
     * @param frite la bonus du personnage
     */
    public void setContent(String IA,String moule, String frite){
        score[0].setText(IA);
        score[1].setText(moule);
        score[2].setText(frite);
    }
    /**
     * Permet de définir la position X et Y de l'objet
     * @param posX la position X
     * @param posY la position Y
     */
    public void setXY(int posX, int posY){
        this.setTranslateX(posX);
        this.setTranslateY(posY);
    }
    
    
}
