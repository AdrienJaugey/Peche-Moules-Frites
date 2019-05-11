/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pechemoulesfrites.IHM.Scene;


import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import pechemoulesfrites.IHM.IHM;
import static pechemoulesfrites.IHM.IHM.setTailleFenetre;
/**
 *
 * @author Nicolas
 */
public class Scene_splashScreen extends Scene {
private Group root; // Groupe principal qui contient les différents éléments IHM
    public Scene_splashScreen(Group parent) {
        super(parent);
        setTailleFenetre(200, 423);
        root = parent;
        Afficher();
         
    } 
    
    
    public void Afficher() {
        //Affichage de l'image de fond du spalsh art
        ImageView Fond = new ImageView(new Image("pechemoulesfrites/Ressources/logo.png"));
        root.getChildren().add(Fond);
        PauseTransition delay= new PauseTransition(Duration.seconds(3));
        delay.play();//exécution du délai 
        delay.setOnFinished(event->IHM.getStage().setScene(new Scene_Menu(new Group()))); //Action entreprise lors de la fin du délai
         
    }

    

    
}
        