/*
 * Scène du menu principal pour sélectionner les IA
 */
package pechemoulesfrites.IHM.Scene;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pechemoulesfrites.IHM.IHM;
import pechemoulesfrites.Modele.Enum.Enum_IA;
/**
 *
 * @author Maxime
 */
public class Scene_Menu extends Scene implements IBouton{
    private final Group root; // Groupe principal qui contient les différents éléments IHM
    private ComboBox cbPerso1,cbPerso2,cbPerso3,cbPerso4; //Les différente comboBox des personnage
    private CheckBox ckCarte; // La checkbox qui permet de savoir si on utilise une carte aléatoire
    public Scene_Menu(Group parent) {
        super(parent, Color.web("1e1e1e"));
        root = parent;
        cbPerso1 = new ComboBox();
        cbPerso2 = new ComboBox();
        cbPerso3 = new ComboBox();
        cbPerso4 = new ComboBox();
        ckCarte = new CheckBox();
        IHM.setTailleFenetre(500, 500);
        Afficher();
        creerBouton();
    }
    
    public void Afficher(){
        
        Text carteAlea = new Text("Utilisation carte aléatoire");
        carteAlea.setFont(Font.font(12));
        carteAlea.setStroke(Color.WHITE);
        Enum_IA[] EnumIA = Enum_IA.values();
        for(Enum_IA e : EnumIA){
            cbPerso1.getItems().add(e);
            cbPerso2.getItems().add(e);
            cbPerso3.getItems().add(e);
            cbPerso4.getItems().add(e);
        }
        //Permet de préselectionner des IA différentes
        cbPerso1.setValue(EnumIA[0]);
        cbPerso2.setValue(EnumIA[1]);
        cbPerso3.setValue(EnumIA[2]);
        cbPerso4.setValue(EnumIA[0]);
        ckCarte.setTranslateX(310);
        ckCarte.setTranslateY(358);
        carteAlea.setTranslateX(170);
        carteAlea.setTranslateY(370);
        
        //Permet d'afficher les image des différents personnage
        ImageView imgPerso1 = new ImageView(new Image("pechemoulesfrites/Ressources/perso1/b2.png"));
        imgPerso1.setFitHeight(80);
        imgPerso1.setPreserveRatio(true);
        ImageView imgPerso2 = new ImageView(new Image("pechemoulesfrites/Ressources/perso2/b2.png"));
        imgPerso2.setFitHeight(80);
        imgPerso2.setPreserveRatio(true);
        ImageView imgPerso3 = new ImageView(new Image("pechemoulesfrites/Ressources/perso3/b2.png"));
        imgPerso3.setFitHeight(80);
        imgPerso3.setPreserveRatio(true);
        ImageView imgPerso4 = new ImageView(new Image("pechemoulesfrites/Ressources/perso4/b2.png"));
        imgPerso4.setFitHeight(80);
        imgPerso4.setPreserveRatio(true);

        //Permet de gérer l'affichage dans une grille pour l'écran
        GridPane grid = new GridPane();
        grid.setVgap(14);
        grid.setHgap(40);
        grid.setPadding(new Insets(30, 30, 30, 30));
        grid.add(cbPerso1, 1, 1);
        grid.add(imgPerso1, 1, 0);
        grid.add(cbPerso2, 1, 7);
        grid.add(imgPerso2, 1, 6);
        grid.add(cbPerso3, 5, 1);
        grid.add(imgPerso3, 5, 0);
        grid.add(cbPerso4, 5, 7);
        grid.add(imgPerso4, 5, 6);
        
        root.getChildren().addAll(grid,ckCarte,carteAlea);
    }
    /**
     * Permet de créer le bouton sur l'écran qui permettra de passser à la suite du programme
     */
    @Override
    public void creerBouton(){
        Button suite = new Button ("Suite");
    suite.setStyle(("-fx-pref-height: 50px; -fx-pref-width: 200px;-fx-base:#F0C300; -fx-font-size : 20;-fx-font-weight : bold; -fx-text-fill : white;"));
    suite.setTranslateX(140);
    suite.setTranslateY(400);
    suite.setOnAction(new EventHandler<ActionEvent>() {//Création des différents effet lorsque que l'on appuie sur le bouton : ici passer a la scène jeu
        @Override public void handle(ActionEvent e) {
            if(cbPerso1.getValue() != null){
                Enum_IA[] IASelectionner = { (Enum_IA) cbPerso1.getValue(), (Enum_IA) cbPerso2.getValue(), (Enum_IA) cbPerso3.getValue(), (Enum_IA) cbPerso4.getValue()};
                
                IHM.getStage().setScene(new Scene_Jeu(new Group(), IASelectionner, !ckCarte.isSelected()));
            }
        }
    });
    root.getChildren().add(suite);
    }
    
}
