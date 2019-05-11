package pechemoulesfrites.Modele.Personnage.IA;

import java.util.ArrayList;
import pechemoulesfrites.Gestionnaire;
import static pechemoulesfrites.Modele.Enum.Enum_IA.IA_ffamee;
import pechemoulesfrites.Modele.Enum.Enum_Objet;
import static pechemoulesfrites.Modele.Enum.Enum_Objet.Frites;
import static pechemoulesfrites.Modele.Enum.Enum_Objet.Moules;
import pechemoulesfrites.Modele.Objet.Moules;
import pechemoulesfrites.Modele.Objet.Objet;
import pechemoulesfrites.Modele.Personnage.Personnage;

public class IA_ffamee extends IA{

    public IA_ffamee(Personnage perso) {
        super(IA_ffamee, perso);
    }

    @Override
    protected Objet getObjetSuivant(Enum_Objet objetCible, ArrayList<Objet> sauf) {
        Objet res = null;
        if(objetCible == Moules && isThereObjet(Moules, sauf)){
            res = GrosseMoules(sauf);
        } else if(objetCible == Frites && isThereObjet(Frites, sauf)){
            res = getObjetProche(Frites, sauf);
        }
        return res;
    }
    
    /**
     * Permet d'obtenir la Moule que l'on va cibler
     * @param sauf la liste des objets à ne pas cibler
     * @return la Moule à cibler
     */
    private Moules GrosseMoules(ArrayList<Objet> sauf){
        Moules res;
        int rayon = 0;
        
        ArrayList<Moules> listeMoules = new ArrayList<>();
        
        RechercheMoulesProche(listeMoules,rayon, sauf);
        while(listeMoules.size()<3){
            if((rayon++) >= 100){
                break;
            }
            RechercheMoulesProche(listeMoules, rayon, sauf);
        }
        
        if(listeMoules.size() > 1){
            res = getMouleMax(listeMoules);
        }else if(listeMoules.size() == 1){
            res = listeMoules.get(0);
        } else {
            res = (Moules) getObjetProche(Moules, sauf);
        }
        
       return res;
    }
    
    /**
     * Permet de rechercher les Moules dans un rayon donné
     * @param listeMoules la liste de Moules à remplir
     * @param rayon le rayon dans lequel chercher
     * @param sauf la liste des Objets à éviter
     */
    private void RechercheMoulesProche(ArrayList<Moules> listeMoules, int rayon, ArrayList<Objet> sauf){
        int[]chemin;
        int distance;
        
        //Création de la liste filtrée dans laquelle chercher
        ArrayList<Objet> liste = Gestionnaire.get().getListeObjet();
        liste.removeAll(sauf);
        
        for(Objet o : liste){ //On stock dans une liste de moules tous les objets à distanceMin du Personnage
            if (o.getType() == Moules)
            {   
                //Calcul de la distance de la moule
                chemin = Gestionnaire.getGraphe().calculateBestPath(getPerso().hasFrites(), getPerso().getCase(), o.getCase());
                distance = Gestionnaire.getGraphe().poidsChemin(chemin, getPerso().hasFrites(), getPerso().getCase(), o.getCase());
                
                if (distance <= rayon) { //Si la distance est inférieure au rayon de recherche
                    if(!listeMoules.contains((Moules)o))
                        listeMoules.add((Moules)o); //On ajoute la moule à la liste
                }                
                    
           }
        
        }
    } 
    
    /**
     * Permet de récupérer la Moule la plus grosse parmis une liste
     * @param liste la liste de Moule
     * @return la Moule la plus Grosse
     */
    private Moules getMouleMax(ArrayList<Moules> liste){
       Moules res = liste.get(0);
       for(Moules m : liste){ // On choisit la moules avec la plus grosse quantité
            if (m.getQuantite() >= res.getQuantite()){
                res = m;
            }
       }
       return res; 
        
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
}
