/*
 * Personnage du jeu
 */
package pechemoulesfrites.IHM;


import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pechemoulesfrites.Modele.Personnage.Personnage;

/**
 *
 * @author Maxime
 */
public class IHM_Personnage extends Parent{
    private final Personnage perso; // Personnage associé à l'image a l'écran
    private final double deplacement; // Permet de savoir combien de pixel sur l'écran l'objet doit se déplacer
    private final IHM_Score score; // Affichage à l'écran qui donne le score du personnage
    private final ImageView image; // Image que l'on voit à l'écran
    private String cheminAccesImage; // Adresse du chemin permettant de récupérer l'image
    private int nombreMouvement; // le nombre de mouvement qu'effectue le personnage

    /**
     * Constructeur de la classe
     * @param p Personnage associer à l'image
     * @param s Le score du personnage
     * @param img Image du personnage
     * @param taille taille de l'image
     * @param x position x de l'image
     * @param y position y de l'image
     */
    public IHM_Personnage(Personnage p,IHM_Score s,String img,int taille, int x, int y){
        this.perso = p;
        this.deplacement = taille;
        this.score = s;
        this.image = new ImageView(new Image(img+"/b2.png"));
        cheminAccesImage = img;
        this.nombreMouvement = 0;
        image.setFitHeight(taille);
        image.setPreserveRatio(true);
        image.setX(x);
        image.setY(y);
        this.getChildren().add(this.score);
        this.getChildren().add(this.image);
    }
    /**
     * Permet de retourner un déplacement sur l'écran en fonction de la dernière action du personnage
     */
    public void deplacement(){
        switch(perso.getLastAction()){
            case AllerHaut:{
                allerHaut();
            } break;
            case AllerBas : {
                allerBas();
            }break;
            case AllerGauche : {
                allerGauche();
            } break;
            case AllerDroite : {
                allerDroite();
            } break; 
            case SauterHaut:{
                sauterHaut();
            } break;
            case SauterBas : {
                 sauterBas();
            }break;
            case SauterGauche : {
                sauterGauche();
            } break;
            case SauterDroite : {
                sauterDroite();
            }break;
        }
    }
    /**
     * Permet d'afficher l'écran le score et le bonus du personnage
     */
    public void affichage(){
        score.setContent(String.valueOf(perso.getIa().getType()),String.valueOf(perso.getScore()), textSaut());
    }
               
    /**
     * Permet de récupérer le personnage associé à l'image
     * @return l'attribut perso
     */
    public Personnage getPersonnage(){
        return this.perso;
    }
    /**
     * Permet de récupérer les informations du score du personnagee
     * @return l'attribut score
     */
    public IHM_Score getScore(){
        return this.score;
    }
    /**
     * Permet de rédéfinir le texte à afficher pour savoir si le bonus est disponible ou non
     * @return Disponible s'il a une frite et Indisponible s'il n'en a pas
     */
    private String textSaut(){
        String res;
        if (perso.hasFrites()) res = "Disponible";
        else res = "Indisponible";
        return res;
    }
    /**
     * Gère l'animation en fonction du nombre de mouvement pour que le personnage se déplace d'une case vers la gauche
     */
    private void allerGauche(){
        if(nombreMouvement == 6) {
            nombreMouvement =0;
        }
        else{ 
            image.setX(image.getX()-deplacement/6);
            image.setImage(new Image(cheminAccesImage+"/g"+String.valueOf(nombreMouvement%3)+".png"));
            nombreMouvement++;
        }

    }
    /**
     * Gère l'animation en fonction du nombre de mouvement pour que le personnage se déplace d'une case vers la droite
     */
    private void allerDroite(){
       if(nombreMouvement == 6){
            nombreMouvement =0;
        }
        else {
            image.setX(image.getX()+deplacement/6);
            image.setImage(new Image(cheminAccesImage+"/d"+String.valueOf(nombreMouvement%3)+".png"));
            nombreMouvement++;
        }

    }
    /**
     * Gère l'animation en fonction du nombre de mouvement pour que le personnage se déplace d'une case vers le haut
     */
    private void allerHaut(){
        if(nombreMouvement == 6){
            nombreMouvement =0;
        }
        else{
            image.setY(image.getY()-deplacement/6);
            image.setImage(new Image(cheminAccesImage+"/h"+String.valueOf(nombreMouvement%3)+".png"));
            nombreMouvement++;
        }

    }
    /**
     * Gère l'animation en fonction du nombre de mouvement pour que le personnage se déplace d'une case vers le bas
     */
    private void allerBas(){
        if(nombreMouvement == 6){
            nombreMouvement =0;
        }
        else{
            image.setY(image.getY()+deplacement/6);
            image.setImage(new Image(cheminAccesImage+"/b"+String.valueOf(nombreMouvement%3)+".png"));
            nombreMouvement++;
        }
    }
    /**
     * Gère l'animation en fonction du nombre de mouvement pour que le personnage saute sur la casse situé a 2 case de lui sur la gauche
     */
    private void sauterGauche(){
        if(nombreMouvement == 0)image.setImage(new Image(cheminAccesImage+"/g1.png"));
        if(nombreMouvement == 6){
            nombreMouvement =0;
            image.setImage(new Image(cheminAccesImage+"/g2.png"));
            affichage();
        }
        else{
            
            image.setX(image.getX()-deplacement/3);
            nombreMouvement++;
            if(nombreMouvement > 3){
            image.setY(image.getY()+deplacement/6);
            }else{
                image.setY(image.getY()-deplacement/6);
            }    
        }
    }
    /**
     * Gère l'animation en fonction du nombre de mouvement pour que le personnage saute sur la casse situé a 2 case de lui sur le haut
     */
    private void sauterHaut(){
        if(nombreMouvement == 0)image.setImage(new Image(cheminAccesImage+"/h1.png"));
        if(nombreMouvement == 6){
            nombreMouvement =0;
            image.setImage(new Image(cheminAccesImage+"/h2.png"));
            affichage();
        }
        else{
            image.setY(image.getY()-deplacement/3);
            nombreMouvement++;  
        }
    }
    /**
     * Gère l'animation en fonction du nombre de mouvement pour que le personnage saute sur la casse situé a 2 case de lui sur le bas
     */
    private void sauterBas(){
        if(nombreMouvement == 0)image.setImage(new Image(cheminAccesImage+"/b1.png"));
        if(nombreMouvement == 6){
            nombreMouvement =0;
            image.setImage(new Image(cheminAccesImage+"/b2.png"));
            affichage();
        }
        else{
            image.setY(image.getY()+deplacement/3);
            nombreMouvement++;
        }
    }
    /**
     * Gère l'animation en fonction du nombre de mouvement pour que le personnage saute sur la casse situé a 2 case de lui sur la droite
     */
    private void sauterDroite(){
        if(nombreMouvement == 0)image.setImage(new Image(cheminAccesImage+"/d1.png"));
        if(nombreMouvement == 6){
            nombreMouvement =0;
            image.setImage(new Image(cheminAccesImage+"/d2.png"));
            affichage();
        }
        else{
            image.setX(image.getX()+deplacement/3);
            nombreMouvement++;
            if(nombreMouvement > 3){
            image.setY(image.getY()+deplacement/6);
            }else{
                image.setY(image.getY()-deplacement/6);
            }    
        }
    }
}
