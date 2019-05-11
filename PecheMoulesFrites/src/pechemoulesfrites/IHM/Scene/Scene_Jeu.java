/*
 *Scène principal du jeu où les personnages jouent
 */
package pechemoulesfrites.IHM.Scene;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import pechemoulesfrites.Gestionnaire;
import pechemoulesfrites.IHM.IHM;
import pechemoulesfrites.IHM.IHM_Frite;
import pechemoulesfrites.IHM.IHM_Moules;
import pechemoulesfrites.IHM.IHM_Objet;
import pechemoulesfrites.IHM.IHM_Personnage;
import pechemoulesfrites.IHM.IHM_Score;
import pechemoulesfrites.IHM.IHM_Sol;
import pechemoulesfrites.IHM.TimerJeu;
import pechemoulesfrites.Modele.Carte.Carte;
import pechemoulesfrites.Modele.Enum.Enum_IA;
import pechemoulesfrites.Modele.Enum.Enum_Objet;
import pechemoulesfrites.Modele.Objet.Frites;
import pechemoulesfrites.Modele.Objet.Moules;
import pechemoulesfrites.Modele.Objet.Objet;
import pechemoulesfrites.Modele.Personnage.Personnage;

/**
 *
 * @author Maxime
 */
public class Scene_Jeu extends Scene implements IBouton{
    private final Group root; // Groupe principal qui contient les différents éléments IHM
    private final ArrayList<IHM_Personnage>listePersonnage; //Liste des personnages présents dans la jeu
    private final ArrayList<IHM_Objet> listeObjet; // Liste des objets présents dans le jeu
    private final int TAILLECASE; // Permet de définir la taille de la case
    private Timeline timer; //Boucle de jeu
    private Slider vitesse; // Slider qui permet de définir la vitesse de la boucle de jeu
    

    /**
     * Constructeur de la classe 
     * @param parent groupe qui contient des éléments IHM
     */
    public Scene_Jeu(Group parent, Enum_IA[] tab, boolean choixCarte) {
        super(parent,Color.web("1e1e1e"));
        this.root= parent;
        listePersonnage = new ArrayList();
        listeObjet = new ArrayList();
        IHM.setTailleFenetre(800, 1050);
        this.creerCarte(tab,choixCarte);
        this.TAILLECASE =calculCase();
        this.creerSol();
        this.creerPersonnage();
        this.creerObjet();   
        this.creerBouton();
        this.creerTimer();
        this.creerSliderVitesse();
        
        
    }
    /**
     * Permet de créer le timer de jeu
     */
    private void creerTimer(){        
        timer = new Timeline(new KeyFrame(Duration.millis(150),new TimerJeu(this)));
        timer.setCycleCount(Timeline.INDEFINITE);
    }
    /**
     * Permet de créer les différents personnages IHM
     */
    private void creerPersonnage(){
        
        for(int j =0;j<Gestionnaire.get().getListePersonnage().size();j++){ // Création des personnage en fonction de ce qui existe
            Personnage p = Gestionnaire.get().getListePersonnage().get(j);
            String image = "pechemoulesfrites/Ressources/perso"+String.valueOf(j+1);
            IHM_Score score = new IHM_Score(image);
            IHM_Personnage personnage = new IHM_Personnage(p,score,image,TAILLECASE,p.getColonne()*TAILLECASE+positionLargeur(),p.getLigne()*TAILLECASE+positionHauteur());
            listePersonnage.add(personnage);
            personnage.affichage();
            root.getChildren().add(personnage);
        }
        for (int i = 0; i< listePersonnage.size();i++){ // Positionnement des scores sur l'écran
            listePersonnage.get(i).getScore().setXY((int) IHM.getStage().getMinWidth()-250, i*150+150+25);
        }
    }
    /**
     * Permet de créer les différents objet IHM
     */
    private void creerObjet(){
        IHM_Objet objet;
         for(Objet o : Gestionnaire.get().getListeObjet()){ // Création des objets en fonction de ce qui existe
            if(o.getType() == Enum_Objet.Moules){
                objet = new IHM_Moules((Moules)o,TAILLECASE,o.getCase().getColonne()*TAILLECASE+positionLargeur(),o.getCase().getLigne()*TAILLECASE+positionHauteur());  
            }else{
                objet = new IHM_Frite((Frites)o,TAILLECASE,o.getCase().getColonne()*TAILLECASE+positionLargeur(),o.getCase().getLigne()*TAILLECASE+positionHauteur());
            }
            listeObjet.add(objet);
            root.getChildren().add(objet);
        };
    }
    /**
     * Permet de supprimer un objet de l'écran 
     * @param o l'objet à supprimer
     */
    public void supprimerObjet(IHM_Objet o){
        root.getChildren().remove(o);
    }
    /**
     * Permet de créer le sol du plateau
     */
    private void creerSol(){
        for(int i = 0;i<Gestionnaire.getCarte().getLargeur();i++){ // Création des cases à l'écran
            for (int j = 0;j<Gestionnaire.getCarte().getHauteur();j++){
            IHM_Sol sol = new IHM_Sol(Gestionnaire.getCarte().getCase(i, j).getType(),TAILLECASE,i*TAILLECASE+positionLargeur(),j*TAILLECASE+positionHauteur());
            root.getChildren().add(sol);
            }                      
        }
    }
    /**
     * Permet de récupérer la liste des personnages IHM
     * @return l'attribut listePersonnage
     */
    public ArrayList<IHM_Personnage> getListePersonnage(){
        return this.listePersonnage;
    }
    /**
     * Permet de récupérer la liste des objets IHM
     * @return l'attribut listeObjet
     */
    public ArrayList<IHM_Objet> getListeObjet(){
        return this.listeObjet;
    }
    /**
     * Permet de créer une carte en fonction du paramètre basique et d'y ajouter les IA sélectionner .
     * Si le paramètre basique est à true on crée une carte prédéfinie à partir d'un fichier texte, si ce fichier n'existe pas on créé une carte par défaut
     * Sinon si le paramètre est à false on crée une carte aléatoire
     * @param tab Les IA sélectionner dans le menu précédent pour les personnage
     * @param basique attribut qui permet de définir quel type de carte on construit
     */
    private void creerCarte(Enum_IA[] tab, boolean basique){
        Carte test;
        if(basique){
            try {
                Scanner scan = new Scanner(new File("carte.txt")); 
                int nbColonne=0;
                int nbLigne=0;
                while (scan.hasNext()) {
                    if(!scan.hasNextInt()){
                            nbColonne=nbColonne+1;
                    }
                        scan.next();
                }
                scan.close();
                Scanner scan2 = new Scanner(new File("carte.txt"));
                while (scan2.hasNextLine()) {
                    nbLigne++;
                    scan2.nextLine();
                }
                scan.close();
                nbColonne = nbColonne/nbLigne;
                test = new Carte(nbColonne,nbLigne);
                test.creerCartePredef(tab);
            } catch (FileNotFoundException ex) {
                System.err.println(ex.getLocalizedMessage());
                System.err.println("Utilisation de la carte par défaut");
                test = new Carte(23,19);
                test.creerCarteDefaut(tab);
                
            }
        }else{
            System.err.println("Utilisation d'une carte aléatoire");
            test = new Carte(23,19);
            test.creerCarteAlea(tab);
        }
    }
    /**
     * Permet le slider pour modifier  la vitesse
     */
    private void creerSliderVitesse(){
        vitesse = new Slider(1, 20, 2);
        vitesse.setOrientation(Orientation.VERTICAL);
        vitesse.setShowTickMarks(true);
        vitesse.setShowTickLabels(true);
        vitesse.setMajorTickUnit(1);
        vitesse.setTranslateY(60);
        vitesse.setTranslateX(IHM.getStage().getWidth()-40); 
        vitesse.setStyle("-fx-pref-height: 400px");
        timer.rateProperty().bind(vitesse.valueProperty());
        Text label = new Text("Vitesse");
        label.setTranslateY(40);
        label.setTranslateX(vitesse.getTranslateX()-15);
        label.setFill(Color.WHITE);
        label.setFont(Font.font(16));
        label.setRotate(90);
        root.getChildren().addAll(vitesse, label);  
    }
    
    /**
     * permet de crée les boutons
     */
    
    @Override
    public void creerBouton(){
        DropShadow shadow = new DropShadow(10,Color.WHITE); // Effet d'ombre que l'on verra quand on passera la souris sur le bouton
        Button jouer = new Button("Jouer");//Création du premier bouton jouer
        jouer.setStyle("-fx-pref-height: 50px; -fx-pref-width: 200px;-fx-base:#00ff00; -fx-font-size : 20;-fx-font-weight : bold; -fx-text-fill : white;"); // CSS pour redéfinir le l'appareence du bouton
        jouer.setTranslateX(IHM.getStage().getMinWidth()-250);
        jouer.setTranslateY(25);
        jouer.setOnAction(new EventHandler<ActionEvent>() { //Création des différents effet lorsque que l'on appuie sur le bouton :: ici on met le jeu en pause ou on le reprends s'il est en pause
            @Override public void handle(ActionEvent e) {
                if (timer.getStatus() == Status.RUNNING){
                    timer.pause();
                    jouer.setText("Reprendre");
                }else{
                    timer.play();
                    jouer.setText("Pause");
                }
                
            }
        });
        jouer.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() { //Permet de savoir si la souris est sur le bouton et met l'effet d'ombre
            @Override
            public void handle(MouseEvent event) {
                jouer.setEffect(shadow);
            }
        });
        jouer.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() { //Permet de savoir si la souris est en dehors du bouton et retire l'effet d'ombre
            @Override
            public void handle(MouseEvent event) {
                jouer.setEffect(null);
            }
        });
        Button arreter = new Button("Arrêter");//Création du second bouton arrêter
        arreter.setStyle("-fx-pref-height: 50px; -fx-pref-width: 200px;-fx-base:#ff0000; -fx-font-size : 20;-fx-font-weight : bold;");//CSS pour redéfinir l'apparence du bouton
        arreter.setTranslateX(IHM.getStage().getMinWidth()-250);
        arreter.setTranslateY(100);
        arreter.setOnAction(new EventHandler<ActionEvent>() {//Création des différents effet lorsque que l'on appuie sur le bouton : ici on arrete la partie
            @Override public void handle(ActionEvent e) {
                stop();
                IHM.getStage().setScene(new Scene_Score(new Group(),listePersonnage));   
            }
        });
        arreter.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {//Permet de savoir si la souris est sur le bouton et met l'effet d'ombre
            @Override
            public void handle(MouseEvent event) {
                arreter.setEffect(shadow);
            }
        });
        arreter.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {//Permet de savoir si la souris est en dehors du bouton et retire l'effet d'ombre
            @Override
            public void handle(MouseEvent event) {
                arreter.setEffect(null);
            }
        });
        root.getChildren().add(arreter);
        root.getChildren().add(jouer);
    }
    /**
     * Permet de calculer l'endroit ou doit commencer la création du sol de la carte par rapport au dessus de la fenetre
     * @return 
     */
    private int positionHauteur(){
        return  (int) (IHM.getStage().getMaxHeight()-TAILLECASE*(Gestionnaire.getCarte().getHauteur()))/3 ;
    }
    /**
     * Permet de calculer l'endroit ou doit commencer la création du sol de la carte par rapport au coté de la fenetre
     * @return la taille de la case
     */
    private int positionLargeur(){
        return  (int) (IHM.getStage().getMaxWidth()-250-TAILLECASE*Gestionnaire.getCarte().getLargeur())/2;
    }
    /**
     * Permet de calculer la taille que doit faire une case pour que les cases ne sortent pas de l'écran
     * @return la taille de la case
     */
    private int calculCase(){
        int hauteur =(int) ((int) IHM.getStage().getMinHeight()/(Gestionnaire.getCarte().getHauteur()*1.1)) ;
        int largeur = (int) ((int) IHM.getStage().getMinHeight()/(Gestionnaire.getCarte().getLargeur()*1.1)) ;
        if(hauteur > largeur) return largeur;
        else return hauteur;
    }
    /**
     * Permet d'arreter la boucle de jeu
     */
    public void stop(){
        timer.stop();
    }
}
