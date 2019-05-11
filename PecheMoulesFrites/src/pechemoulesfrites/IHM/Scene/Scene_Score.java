/*
 * Scène final du jeu permettant d'afficher le score final de la partie
 */
package pechemoulesfrites.IHM.Scene;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import pechemoulesfrites.IHM.IHM;
import pechemoulesfrites.IHM.IHM_Personnage;



/**
 *
 * @author Maxime
 */
public class Scene_Score extends Scene implements IBouton{
    private Group root;// Groupe principal qui contient les différents éléments IHM
    private final IHM_Personnage[] tabPersonnage; //Liste des personnages présents dans la jeu
    /**
     * Constructeur de la classe scene score 
     * @param parent liste qui possède tout les objet affiché a l'écran
     * @param liste la liste des personnages qui ont joué
     */
    public Scene_Score(Group parent, ArrayList<IHM_Personnage> liste) {
        super(parent,Color.web("1e1e1e"));
        root = parent;
        tabPersonnage = new IHM_Personnage[liste.size()];
        IHM.setTailleFenetre(750, 400);
        remplirTableau(liste);
        triBulle();
        affichageEcran();
        creerBouton();
        ajouterImage();
    }
    /**
     * Permet de trié le tableau des scores pour afficher le gagnant en premier et le perdant en dernier
     */
    public void triBulle(){
        int longueur=tabPersonnage.length;
        boolean inversion;
        IHM_Personnage tampon ;
        
        do
            {
            inversion=false;

            for(int i=0;i<longueur-1;i++)
                {
                if(tabPersonnage[i].getPersonnage().getScore()<tabPersonnage[i+1].getPersonnage().getScore())
                    {
                    tampon = tabPersonnage[i];
                    tabPersonnage[i] = tabPersonnage[i+1];
                    tabPersonnage[i+1] = tampon;
                    inversion=true;
                    }
                }
             }
        while(inversion);
    }
    /**
     * Permet d'initialisé le tableau avec les valeurs de la liste des personnage
     * @param liste 
     */
    private void remplirTableau(ArrayList<IHM_Personnage> liste){
        for (int i =0;i<liste.size();i++){
            tabPersonnage[i] = liste.get(i);
        }
    }
    /**
     * Permet d'afficher a l'écran le tableau trié
     */
    private void affichageEcran(){
        for(int i=0;i<tabPersonnage.length;i++){
            tabPersonnage[i].getScore().setXY(150, i*150+30);
            root.getChildren().add(tabPersonnage[i].getScore());
        }

    }
    /**
     * Permet de crée un bouton sur l'écran
     */
    @Override
    public void creerBouton(){
        Button quitter = new Button("Quitter");//Cr"ation du second bouton arrêter
        quitter.setStyle("-fx-pref-height: 50px; -fx-pref-width: 200px;-fx-base:#F0C300; -fx-font-size : 20;-fx-font-weight : bold; -fx-text-fill : white;");//CSS pour redéfinir l'apparence du bouton
        quitter.setTranslateX(100);
        quitter.setTranslateY(650);
        quitter.setOnAction(new EventHandler<ActionEvent>() {//Création des différents effet lorsque que l'on appuie sur le bouton
            @Override public void handle(ActionEvent e) {
                IHM.arreter();
            }
        });

        root.getChildren().addAll(quitter);
    }
    /**
     * Permet d'ajouter les images nécessaire à l'écran, les trophée en fonction du nombre de personnage
     */
    private void ajouterImage(){
       if(tabPersonnage.length >=1){
       ImageView premier = new ImageView(new Image("pechemoulesfrites/Ressources/premier.png"));
       premier.setTranslateX(20);
       premier.setTranslateY(30);
       premier.setFitHeight(100);
       premier.setPreserveRatio(true);
       root.getChildren().add(premier);
       }
       if(tabPersonnage.length >=2){
       ImageView deuxieme = new ImageView(new Image("pechemoulesfrites/Ressources/deuxieme.png"));
       deuxieme.setTranslateX(35);
       deuxieme.setTranslateY(180);
       deuxieme.setFitHeight(100);
       deuxieme.setPreserveRatio(true);
       root.getChildren().add(deuxieme);
       }
       if(tabPersonnage.length >=3){
       ImageView troisieme = new ImageView(new Image("pechemoulesfrites/Ressources/troisieme.png"));
       troisieme.setTranslateX(30);
       troisieme.setTranslateY(330);
       troisieme.setFitHeight(100);
       troisieme.setPreserveRatio(true);
       root.getChildren().add(troisieme);
       }
       
    }
}
