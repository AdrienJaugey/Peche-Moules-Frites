package pechemoulesfrites.Modele.Personnage.IA;

import java.util.ArrayList;
import pechemoulesfrites.Gestionnaire;
import static pechemoulesfrites.Modele.Enum.Enum_IA.IA_pprentie;
import pechemoulesfrites.Modele.Enum.Enum_Objet;
import pechemoulesfrites.Modele.Objet.Objet;
import pechemoulesfrites.Modele.Personnage.Personnage;

public class IA_pprentie extends IA{
    
    public IA_pprentie(Personnage perso) {
        super(IA_pprentie, perso);
    }   
    
    @Override
    protected Objet getObjetSuivant(Enum_Objet objetCible, ArrayList<Objet> sauf){
        Objet res = null;
        int[] chemin;
        int distanceMin = Integer.MAX_VALUE-1, distance;
        ArrayList<Objet> liste = Gestionnaire.get().getListeObjet();
        liste.removeAll(sauf);
        if(liste.size() == 1 && liste.get(0).getType() == objetCible){
            res = liste.get(0);
        } else if(liste.size()>1){
            for(Objet o : liste){ //On cherche la moule la plus proche
                if (o.getType() == objetCible){
                   distance = Math.abs(getPerso().getColonne()-o.getCase().getColonne()) + Math.abs(getPerso().getLigne()-o.getCase().getLigne());
                   if (distance <= distanceMin+1) { //Si la distance est inférieure à la distance minimale trouvée précedemment
                       chemin = Gestionnaire.getGraphe().calculateBestPath(getPerso().hasFrites(),getPerso().getCase(), o.getCase()); //On récupère le chemin potentiel
                       distance = Gestionnaire.getGraphe().poidsChemin(chemin, getPerso().hasFrites(), getPerso().getCase(), o.getCase()); //On calcule le poids du chemin 
                       if(distance < distanceMin){//On met à jour la distance minimale
                           distanceMin = distance;
                           res = o; //On prévoit de renvoyer cette Moules
                       } 
                   }
               }
            }
       }
       return res;
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
}