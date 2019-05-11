/**
 * Moteur du jeeu
 */
package pechemoulesfrites.IHM;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;
import pechemoulesfrites.IHM.Scene.Scene_Menu;
import pechemoulesfrites.IHM.Scene.Scene_splashScreen;

;

/**
 *
 * @author Maxime Dufant
 */
public class IHM extends Application{
    private static Stage mystage; // Fenêtre principale
    
    @Override
    public void start(Stage stage){
        mystage = stage;
        //Mise en forme de la fenêtre       
        mystage.setTitle("Pêche Moules-Frites");
        mystage.setResizable(false);
        //Creation de la première scene
        Scene_splashScreen scene = new Scene_splashScreen(new Group());
        //Scene_Menu scene = new Scene_Menu(new Group());
        mystage.setScene(scene);
        mystage.show(); 
    }    
    //Permet de démarrer le jeu
    public static void demarrer(){
        launch();
    }
    public static void arreter(){
        mystage.close();
    }
    //Permet de récupérer la fenêtre
    public static Stage getStage(){
        return mystage;
    }  
    /**
     * Permet de redimensionner la fenetre et de la recentrer sur l'écran
     * @param hauteur nouvelle hauteur de la fenetre
     * @param largeur nouvelle largeur de la fenetre
     */
    public static void setTailleFenetre(int hauteur, int largeur){
        mystage.setMinWidth(largeur);
        mystage.setMinHeight(hauteur);
        mystage.setMaxWidth(largeur);
        mystage.setMaxHeight(hauteur);
        mystage.centerOnScreen();
    }
    
}
