package pechemoulesfrites.Modele.Personnage.IA;

import java.util.ArrayList;
import pechemoulesfrites.Gestionnaire;
import pechemoulesfrites.Modele.Carte.Graphes.PathFinder.Enum_PathFinder;
import static pechemoulesfrites.Modele.Carte.Graphes.PathFinder.Enum_PathFinder.AStar;
import static pechemoulesfrites.Modele.Carte.Graphes.PathFinder.Enum_PathFinder.DijkstraSaut;
import pechemoulesfrites.Modele.Enum.Enum_Objet;
import static pechemoulesfrites.Modele.Enum.Enum_Objet.Frites;
import static pechemoulesfrites.Modele.Enum.Enum_Objet.Moules;
import pechemoulesfrites.Modele.Objet.Moules;
import pechemoulesfrites.Modele.Objet.Objet;
import pechemoulesfrites.Modele.Personnage.Personnage;

public class IA_leatoire extends IA{
    private final double CoeffDistance;
    private final double CoeffScore;
    private final double CoeffAdversaireProche;
    private final double coeff4;

    /**
     * Constructeur d'une IA_leatoire
     * @param CoeffDistance le coeff qui jouera sur la note de la moule au niveau de la distance
     * @param CoeffScore le coeff qui jouera sur la note de la moule au niveau du score de cette dernière
     * @param CoeffAdversaireProche le coeff qui jouera sur la note de la moule au niveau de la proximité d'un adversaire
     * @param c4 coeff inutile pour l'instant
     */    
    public IA_leatoire(double CoeffDistance, double CoeffScore, double CoeffAdversaireProche, double c4) {
        super();
        this.CoeffDistance = CoeffDistance;
        this.CoeffScore = CoeffScore;
        this.CoeffAdversaireProche = CoeffAdversaireProche;
        this.coeff4 = c4;
    }

    @Override
    protected Objet getObjetSuivant(Enum_Objet objetCible, ArrayList<Objet> sauf) {
        Objet res;
        if(objetCible == Moules){
            res = RechercheMoules();
        } else {
            res = getObjetProche(Frites, sauf);
        }
        return res;
    }
    
     private Moules RechercheMoules(){
        Moules res = null;
        double NoteMax = Double.MIN_VALUE;
        
        for(Objet o : Gestionnaire.get().getListeObjet()){ //On stocke dans une liste de moules tous les objets à distanceMin du Personnage
            if (o.getType() == Moules){   
                int[] chemin = Gestionnaire.getGraphe().calculateBestPath(getPerso().hasFrites(), getPerso().getCase(), o.getCase());
                int distance = Gestionnaire.getGraphe().poidsChemin(chemin,getPerso().hasFrites(), getPerso().getCase(), o.getCase());
                double note = Notation((Moules)o);
                if(note >= NoteMax && distance != Integer.MAX_VALUE){
                    NoteMax = note;
                    
                    res = (Moules)o;
                }
            }                
        }
        
        /*System.out.println("---------");
        System.out.println("Coeff Grosse "+CoeffScore+" Coeff Dist "+CoeffDistance);
        for(Objet o : Gestionnaire.get().getListeObjet()){ //On stock dans une liste de moules tous les objets à distanceMin du Personnage
            if (o.getType() == Moules)
            {   
                System.out.println(((Moules)o).getQuantite()+" - "+Notation((Moules) o));
            }                
        }*/
        
        return res;
    }
    
     
    
    
    private double Notation(Moules m){
       
       int[]chemin;
       int distance;  
       double CritDistance = 0;
       double CritScore;
       double CritAdversaireProche;
       double NoteMoules;
               
       chemin = Gestionnaire.getGraphe().calculateBestPath(getPerso().hasFrites(), getPerso().getCase(),  m.getCase()); //On récupère le chemin potentiel
       distance = Gestionnaire.getGraphe().poidsChemin(chemin,getPerso().hasFrites(), getPerso().getCase(), m.getCase()); //On calcule le poids du chemin
       
       if(distance != 0)
        CritDistance = (1.0/distance);
       
       CritScore = m.getQuantite()/getMouleMax();
       CritAdversaireProche= this.DistanceAdversaireProche(m, distance);
       
       NoteMoules = (CritDistance*this.CoeffDistance) + (CritScore*this.CoeffScore) - (CritAdversaireProche*this.CoeffAdversaireProche);
       
       return NoteMoules;
       
    } 
    
    
    private double DistanceAdversaireProche(Moules m, int distancePerso){
        double ps = 0;
        int[]chemin;
        int distanceMin = distancePerso;
        int distance;  

        ArrayList<Personnage> adversaires = Gestionnaire.get().getListePersonnage();
        adversaires.remove(getPerso());

        for(Personnage p : adversaires ){
            chemin = Gestionnaire.getGraphe().calculateBestPath(getPerso().hasFrites(), getPerso().getCase(),  m.getCase()); //On récupère le chemin potentiel
            distance = Gestionnaire.getGraphe().poidsChemin(chemin,getPerso().hasFrites(), p.getCase(), m.getCase()); //On calcule le poids du chemin
            
            if (distance < distanceMin){ //Si la distance est inférieure à la distance minimale trouvée précedemment   
                distanceMin = distance; //On met à jour la distance minimale
                if(distance != 0) 
                    ps = 1/distance;
            }
        }
        return ps;
    }

    private double getMouleMax(){
        double res = 0;
        ArrayList<Objet> moules = Gestionnaire.get().getListeObjet();
        moules.removeIf((o) -> o.getType() == Frites);
        for(Objet m : moules){
            double score = ((Moules) m).getQuantite();
            if(score > res) res = score;
        }
        return res;
    }

    @Override
    public String toString() {
        return "IA_leatoire{" + "CoeffDistance=" + CoeffDistance + ", CoeffScore=" + CoeffScore + ", CoeffAdversaireProche=" + CoeffAdversaireProche + ", coeff4=" + coeff4 + '}';
    }
    
    
}
