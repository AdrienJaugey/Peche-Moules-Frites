package pechemoulesfrites.Modele.Personnage.IA;

import java.util.ArrayList;
import pechemoulesfrites.Gestionnaire;
import pechemoulesfrites.Modele.Carte.Case;
import pechemoulesfrites.Modele.Enum.Enum_Action;
import static pechemoulesfrites.Modele.Enum.Enum_Action.*;
import pechemoulesfrites.Modele.Enum.Enum_IA;
import pechemoulesfrites.Modele.Enum.Enum_Objet;
import static pechemoulesfrites.Modele.Enum.Enum_Objet.Moules;
import pechemoulesfrites.Modele.Objet.Objet;
import pechemoulesfrites.Modele.Personnage.Personnage;

public abstract class IA {
    //Le type de l'IA
    Enum_IA type;
    //Le personnage associé à l'IA
    private final Personnage perso;
    //La case que cible l'IA
    private Case cible;
    //Le chemin que doit suivre l'IA
    private int[] chemin;
    //Le sommet actuel du personnage
    private int sommetPerso;
    //Liste des objets à surveiller
    private ArrayList<Objet> aSurveiller;
    
    /**
     * Constructeur d'IA
     * @param type le type de l'IA (son nom)
     * @param perso le personnage qu'elle dirige
     */
    public IA(Enum_IA type, Personnage perso){
        this.type = type;
        this.perso = perso;
        aSurveiller = new ArrayList<>();
        cible = null;
        chemin = null;
    }
    
    /**
     * Permet de savoir quelle IA est celle-ci
     * @return le type d'IA
     */
    public Enum_IA getType() {
        return type;
    }
    
    /**
     * Permet d'obtenir le personnage associé à l'IA
     * @return le personnage de l'IA
     */
    protected Personnage getPerso(){
        return this.perso;
    }

    /**
     * Permet de mettre à jour le sommet du personnage
     */
    private void updateSommetPerso() {
        this.sommetPerso = Gestionnaire.getGraphe().getSommet(perso.hasFrites(), perso.getCase());
    }
    
    /**
     * Fait agir le personnage
     */
    public void action(){
        this.updateSommetPerso();
        this.perso.action(getAction());
    }
    
    /**
     * Permet de récupérer l'Enum_Action nécessaire pour passer d'une case à la suivante
     * @return l'Enum_Action à faire pour se déplacer sur la case suivante
     */
    protected Enum_Action nextMove(){
        Enum_Action action = Attendre; //De base le personnage attendra
        int sommetCible = chemin[sommetPerso]; //On récupère le sommet sur lequel le personnage doit se rendre
        int decalage = Gestionnaire.getGraphe().getHauteur()*Gestionnaire.getGraphe().getLargeur();
        if(sommetCible != -1){
            sommetCible %= decalage;  
            if (sommetPerso == sommetCible){
                action = Ramasser;                                         
            } else if (sommetPerso % decalage == sommetCible - 1) { //S'il se trouve à gauche du sommet visé
                action = AllerDroite;
            } else if (sommetPerso % decalage == sommetCible - 2) { //S'il se trouve 2 cases à gauche du sommet visé
                action = SauterDroite;
            } else if (sommetPerso % decalage == sommetCible + 1) { //Idem s'il est à droite
                action = AllerGauche;
            } else if (sommetPerso % decalage == sommetCible + 2) { //S'il se trouve 2 cases à droite du sommet visé
                action = SauterGauche;
            } else if (sommetPerso % decalage == sommetCible - Gestionnaire.getCarte().getLargeur()) { //S'il est en haut
                action = AllerBas;
            } else if (sommetPerso % decalage == sommetCible - 2 * Gestionnaire.getCarte().getLargeur()) { //S'il est 2 cases en haut
                action = SauterBas;
            } else if (sommetPerso % decalage == sommetCible + Gestionnaire.getCarte().getLargeur()) { //S'il est en bas
                action = AllerHaut;
            } else if (sommetPerso % decalage == sommetCible + 2 * Gestionnaire.getCarte().getLargeur()) { //S'il est 2 cases en bas
                action = SauterHaut;
            }
        }
        return action; //On renvoit l'action
    }
    
    /**
     * Permet de définir l'action qui va être réalisée
     * @return l'action à réaliser
     */
    private Enum_Action getAction() {
        Enum_Action action = Attendre;
        
        if(!surveillance()){
            reset();
        }
        
        //On vérifie si personne n'est déjà sur la cible
        if(cible != null){
            if(perso.getCase() != cible){
                Objet obj = cible.getObjet();
                if(cible.getPerso().size() > 0){
                    reset();
                    if(isThereObjet(Moules, obj))
                        allerVers(getObjetSuivant(Moules, obj));
                }
            }
        }
        
        //On essaye de définir une cible s'il y en a
        if(cible == null){
            if(isThereObjet(Moules)){
                allerVers(getObjetSuivant(Moules));
            }
        }
        
        //Si on a déjà ou que l'on vient de définir une cible, on agit
        if(cible != null) {
            //Si l'on est pas sur la cible
            if(cible != perso.getCase()){
                action = nextMove();
            } else {
                reset();
                action = Ramasser;
            }
        }
        return action;
    }
    
    /**
     * Permet de réinitialiser le chemin, la cible et la liste des objets à surveiller
     */
    private void reset(){
        chemin = null;
        cible = null;
        aSurveiller = new ArrayList<>();
    }
    
    /**
     * Permet de savoir si un objet est présent sur la carte
     * @param objetAChercher l'objet que l'on cherche
     * @param sauf les objets à passer
     * @return true si un objet correspondant à été trouvé
     */
    protected boolean isThereObjet(Enum_Objet objetAChercher, ArrayList<Objet> sauf){
        ArrayList<Objet> liste = Gestionnaire.get().getListeObjet();
        liste.removeAll(sauf);
        liste.removeIf((o)->o.getType() != objetAChercher);
        liste.removeIf((o)->Gestionnaire.getGraphe().poidsChemin(Gestionnaire.getGraphe().calculateBestPath(perso.hasFrites(), perso.getCase(), o.getCase()), perso.hasFrites(), perso.getCase(), o.getCase()) == Integer.MAX_VALUE);
        return liste.size()>0;
    }
    
    /**
     * Permet de savoir si un objet est présent sur la carte
     * @param objetAChercher l'objet que l'on cherche
     * @param sauf l'objet à passer
     * @return true si un objet correspondant à été trouvé
     */
    protected boolean isThereObjet(Enum_Objet objetAChercher, Objet sauf){
        ArrayList<Objet> liste = new ArrayList<>();
        liste.add(sauf);
        return isThereObjet(objetAChercher,liste);
    }
    
    /**
     * Permet de savoir si un objet est présent sur la carte
     * @param objetAChercher l'objet que l'on cherche
     * @return true si un objet correspondant à été trouvé
     */
    protected boolean isThereObjet(Enum_Objet objetAChercher){
        return isThereObjet(objetAChercher, new ArrayList<>());
    }
    
    /**
     * Permet de définir le chemin nécessaire pour se rendre à l'objet
     * @param objetCible l'objet vers lequel aller
     */
    private void allerVers(Objet objetCible){
        if(objetCible != null){
            cible = objetCible.getCase();
            chemin = Gestionnaire.getGraphe().calculateBestPath(perso.hasFrites(),  perso.getCase(), cible);
            
            //Renouvellement de la liste des objets à surveiller
            int decalage = Gestionnaire.getGraphe().getHauteur()*Gestionnaire.getGraphe().getLargeur();
            int sommetCourant = sommetPerso;
            aSurveiller = new ArrayList<>();
            while(sommetCourant != -1){
                if(sommetCourant == chemin[sommetCourant]%decalage){
                    aSurveiller.add(Gestionnaire.getGraphe().getCase(sommetCourant).getObjet());
                }
                sommetCourant = chemin[sommetCourant];
            }
        }
    }
    
    /**
     * Permet de savoir si tous les objets à surveiller sont tous présents
     * @return true s'ils sont tous là
     */
    private boolean surveillance(){
        boolean res = true;
        for(Objet o : aSurveiller){
            res = res && Gestionnaire.get().getListeObjet().contains(o);
        }
        return res;
    }
    
    /**
     * Retire un objet de la liste de ceux à surveiller
     * @param obj l'objet à ne plus surveiller
     */
    public void retirerSurveillance(Objet obj){
        aSurveiller.remove(obj);
    }
    
    /**
     * Permet de récupérer l'objet le plus proche
     * @param objetCible l'objet à chercher
     * @return l'objet le plus proche
     */
    private Objet getObjetSuivant(Enum_Objet objetCible){
        return IA.this.getObjetSuivant(objetCible, new ArrayList<>());
    }    
    
    /**
     * Permet de récupérer l'objet le plus proche
     * @param objetCible l'objet à chercher
     * @param sauf l'objet à éviter
     * @return l'objet le plus proche
     */
    private Objet getObjetSuivant(Enum_Objet objetCible, Objet sauf){
        ArrayList<Objet> liste = new ArrayList<>();
        liste.add(sauf);
        return getObjetSuivant(objetCible, liste);
    }
    
    /**
     * Permet de récupérer l'objet le plus proche
     * @param objetCible l'objet à chercher
     * @param sauf les objets à éviter
     * @return l'objet le plus proche
     */
    protected abstract Objet getObjetSuivant(Enum_Objet objetCible, ArrayList<Objet> sauf);
    
    /**
     * Permet de récupérer les Frites les plus proches
     * @param objetCible le type d'objet que l'on cherche
     * @param sauf la liste des objets à éviter
     * @return les Frites les plus proches
     */
    protected Objet getObjetProche(Enum_Objet objetCible, ArrayList<Objet> sauf) {
        Objet res = null;
        int distanceMin = Integer.MAX_VALUE;
        
        ArrayList<Objet> liste = Gestionnaire.get().getListeObjet();
        liste.removeAll(sauf);
        liste.removeIf((o) -> o.getType() != objetCible);
        
        for(Objet o : liste){
            int chemin[] = Gestionnaire.getGraphe().calculateBestPath(perso.hasFrites(), getPerso().getCase(), o.getCase());
            int distance = Gestionnaire.getGraphe().poidsChemin(chemin, perso.hasFrites(), getPerso().getCase(), o.getCase());
            if(distance < distanceMin){
                res = o;
                distanceMin = distance;
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return type + " " + perso.getCase();
    }
    
}
