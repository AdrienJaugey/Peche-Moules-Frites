/*
 * Boucle de jeu
 */
package pechemoulesfrites.IHM;


import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import pechemoulesfrites.Gestionnaire;
import pechemoulesfrites.IHM.Scene.Scene_Jeu;
import pechemoulesfrites.IHM.Scene.Scene_Score;
import static pechemoulesfrites.Modele.Enum.Enum_Action.*;
import pechemoulesfrites.Modele.Personnage.Personnage;

/**
 *
 * @author Maxime
 */
public class TimerJeu implements EventHandler{

    
    private Scene_Jeu scene; // Scene de jeu
    private int joueur; // Permet de savoir quel joueur doit jouer
    private int tourPerso;
    /**
     * Constructeur de la classe
     * @param sceneJ la scene principal où l'on joue
     */
    public TimerJeu(Scene_Jeu sceneJ){
        scene = sceneJ;
        joueur = -1;
        tourPerso =7;
    }
    /**
     * Fonction appelée régulièrement qui permet au jeu de jouer
     * @param event 
     */
    @Override
    public void handle(Event event) {
        if(!tourSuivant()){ // Si c'est toujours aux tour du personnage on regarde ce qu'il doit faire
            tourPerso++;
                gestionPerso(scene.getListePersonnage().get(joueur));
        }else{ // Sinon on change de personnage et on recupère l'action à effectué
            tourPerso=0;
            joueur++;
            if(joueur >= Gestionnaire.get().getListePersonnage().size()){
                joueur = 0;
            }
           scene.getListePersonnage().get(joueur).getPersonnage().getIa().action();
            
        }    
    }
    /**
     * Permet de connaitre la dernière action de l'IA
     * @param p le personnage qu'on désire connaitre l'action
     */
    private void action(Personnage p){
        p.getIa().action();
    }
    /**
     * Permet de joueur à l'écran les actions du personnage
     * @param perso le personnage qui doit joueur l'action
     */
    public void gestionPerso(IHM_Personnage perso){
    switch (perso.getPersonnage().getLastAction()) {
            case Ramasser: // Si le personnage ramasse il fini son tour et on supprime l'objet de l'écran
                perso.affichage();
                tourPerso =7;
                for(IHM_Objet o : scene.getListeObjet()){
                    if(!Gestionnaire.get().getListeObjet().contains(o.getObjet()))
                        scene.supprimerObjet(o);    
                }
                break;
            case Attendre: // si le personnage attend il fini son tour
                tourPerso=7;
                break;
            default: // Sinon on continue les déplacement du personnage
                perso.deplacement();
                break;
        }
        finPartie();
    }
    
    /**
     * Permet de savoir quand la partie est terminée 
     * La partie est terminée quand les personnages n'ont plus rien à faire et qu'il sont tous dans l'état attendre
     */
    public void finPartie(){
        boolean res = Gestionnaire.get().getListePersonnage().get(0).getLastAction() == Attendre;
        
        if(res){
            for(Personnage p : Gestionnaire.get().getListePersonnage()){
                res = res && (p.getLastAction() == Attendre);
            }
            if(res){
                scene.stop();
                IHM.getStage().setScene(new Scene_Score(new Group(),scene.getListePersonnage()));
            }
        }
    }
    /**
     * Permet de savoir si le personnage a terminer son tour ou non
     * @return vrai s'il son tour est fini et faux dans le cas contraire
     */
    private boolean tourSuivant(){
            return tourPerso == 7;
    }
}
