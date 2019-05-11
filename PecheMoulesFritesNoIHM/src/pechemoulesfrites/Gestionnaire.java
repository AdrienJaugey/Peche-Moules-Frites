package pechemoulesfrites;

import java.util.ArrayList;
import pechemoulesfrites.Modele.Carte.Carte;
import pechemoulesfrites.Modele.Carte.Graphes.Graphe;
import pechemoulesfrites.Modele.Objet.Objet;
import pechemoulesfrites.Modele.Personnage.Personnage;

/**
 *
 * @Mathieu Farrugia
 */
public class Gestionnaire {
    private static Gestionnaire INSTANCE;
    private static Carte carte;
    private static Graphe graphe;
    private final ArrayList<Personnage> ListePersonnage;
    private final ArrayList<Objet> ListeObjets;
    
	
    /**
     * Constructeur de Gestionnaire privé pour respecter le Design Pattern du Singleton 
     */
    private Gestionnaire(){								
        this.ListeObjets = new ArrayList();
        this.ListePersonnage = new ArrayList(); 
        carte = null;
    }
	
    /**
     * Permet de récupérer l'instance du Gestionnaire et de la créer si elle n'existe pas
     * @return l'instance du Gestionnaire 
     */
    public static Gestionnaire get(){			
        if (INSTANCE == null){ 
            INSTANCE = new Gestionnaire();
        }
        return INSTANCE;
    }
    
    /**
     * Permet de créer un graphe à partir d'une carte 
     * @param c la Carte à ajouter, avec laquelle sera "géneré" le graphe 
     */
    public static void addCarte(Carte c){
        carte = c;
        graphe = new Graphe(carte);
    }
    
    /**
     * Permet d'obtenir la carte de la partie en cours 
     * @return la carte du gestionnaire
     */
    public static Carte getCarte(){
        return carte;
    }
    
    /**
     * Permet d'obtenir le graphe de la partie en cours 
     * @return le graphe du gestionnaire
     */
    public static Graphe getGraphe(){
        return graphe;       
    }
	
    /**
     * Permet d'ajouter un personnage à la liste des personnages existants 
     * @param perso Personnage  à ajouter 
     */
    public void addPersonnage(Personnage perso){	
        this.ListePersonnage.add(perso);
    }
	
    /**
     * Permet de retourner la liste des peronnages existants
     * @return une liste de personnages 
     */
    public ArrayList<Personnage> getListePersonnage(){	
        return (ArrayList<Personnage>) ListePersonnage.clone();
    }
    
    /**
     * Permet de retourner la liste des objets existants
     * @return une liste d'objets
     */
    public  ArrayList<Objet> getListeObjet(){	
        return (ArrayList<Objet>) ListeObjets.clone();
    }
    
    /**
     * Permet de retirer un objet de la listes de objets existants
     * @param obj Objet à retirer
     */
    public void removeObjet (Objet obj){ 
        this.ListeObjets.remove(obj); 
    }
    
    /**
     * Permet d'ajouter un objet à la liste des objets existants
     * @param obj Objet à ajouter 
     */
    public void addObjet (Objet obj){ 
        this.ListeObjets.add(obj);
    }
    
    public void reset(){
        while(!ListeObjets.isEmpty()) ListeObjets.remove(0);
        while(!ListePersonnage.isEmpty()) ListePersonnage.remove(0);
    }
}
