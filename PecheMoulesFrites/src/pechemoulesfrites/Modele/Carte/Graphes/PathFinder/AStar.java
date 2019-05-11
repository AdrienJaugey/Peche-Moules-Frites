package pechemoulesfrites.Modele.Carte.Graphes.PathFinder;

import java.util.ArrayList;
import pechemoulesfrites.Gestionnaire;

public class AStar implements IPathFinder{
    private static AStar instance;
    
    boolean Mark[];
    ArrayList<Integer> aTraiter;
    private final int[] distance;
    private final int[] predecesseur;
    
    private AStar(){
        Mark = new boolean[Gestionnaire.getGraphe().getNbSommet()];
        aTraiter = new ArrayList<>();
        distance = new int[Gestionnaire.getGraphe().getNbSommet()];
        predecesseur = new int[Gestionnaire.getGraphe().getNbSommet()];
    }
    
    public static AStar get(){
        if(instance == null){
            instance = new AStar();
        }
        return instance;
    }
    
    @Override
    public int[] executer(int depart, int arrivee){
        initialisation();
        distance[depart] = 0;
        aTraiter.add(depart);
        
        int a = depart;
        while(!aTraiter.isEmpty() && (a != -1)){ //Tant qu'il existe un sommet non marqué, on choisit celui de distance min
           
            for(int b =0;b<Gestionnaire.getGraphe().getNbSommet();b++){ //Pour chaque sommet, on effectue le relâchement de celui-ci avec a
                relachement(a,b);
            }
            //Si on est arrivé
            if(a == arrivee) break;
            aTraiter.remove(new Integer(a));
            Mark[a] = true;
            ajouterVoisins(a);
            a = getSommetSuivant(depart, arrivee); //On récupère de nouveau le sommet non marqué de distance min
        }
        return predecesseur;
    }
    
    /**
     * Permet distance'initialiser les tableaux Mark, distance et predecesseur de Dijkstra
     * @param d le tableau distance'entier contenant la distance depuis le point de départ
     * @param pred le tableau contenant le prédecesseur de chaque point
     */
    private void initialisation(){
        for(int i = 0; i < Gestionnaire.getGraphe().getNbSommet(); i++){
            distance[i] = Integer.MAX_VALUE; //On initialise distance à une valeur assez grande
            predecesseur[i] = -1; //On initilise predecesseur à -1
            Mark[i] = false;
        }
        while(!aTraiter.isEmpty()){
            aTraiter.remove(0);
        }
    }
    
     /**
     * Permet de procéder au relâchement dans Dijkstra
     * @param a un sommet
     * @param b un sommet
     */
    private void relachement(int a, int b){
        int poids = Gestionnaire.getGraphe().getMatrice(a,b);
        //Si la distance de b est strictement supérieure à la distance de a plus celle de a à b
        if(((distance[b]) > (distance[a] + poids)) && (poids != 0)){ 
            distance[b] = distance[a] + poids; //On met à jour la distance de b
            predecesseur[b] = a; //On définit a comme prédecesseur de b
        }
    }
    
    /**
     * Permet de récupérer l'heuristique distance'un sommet (i.e. sa distance à "vol distance'oiseau" par rapport à l'arrivée) avec un effet pour casser les lignes droites
     * @param sommet le sommet dont on veut l'heuristique
     * @param arrivee le sommet distance'arrivée
     * @return l'heuristique du point
     */
    private double getHeuristic(int sommet,int depart, int arrivee){
        double h = (Math.abs(Gestionnaire.getGraphe().getCase(sommet).getColonne() - Gestionnaire.getGraphe().getCase(arrivee).getColonne()) + Math.abs(Gestionnaire.getGraphe().getCase(sommet).getLigne() - Gestionnaire.getGraphe().getCase(arrivee).getLigne()));
        int dx1 = Gestionnaire.getGraphe().getCase(sommet).getColonne() - Gestionnaire.getGraphe().getCase(arrivee).getColonne();
        int dy1 = Gestionnaire.getGraphe().getCase(sommet).getLigne() - Gestionnaire.getGraphe().getCase(arrivee).getLigne();
        int dx2 = Gestionnaire.getGraphe().getCase(depart).getColonne() - Gestionnaire.getGraphe().getCase(arrivee).getColonne();
        int dy2 = Gestionnaire.getGraphe().getCase(depart).getLigne() - Gestionnaire.getGraphe().getCase(arrivee).getLigne();
        int cross = Math.abs(dx1*dy2 - dx2*dy1);
        h += cross*0.001;
        return h;
    }
    
    /**
     * Permet de récupérer le sommet de aTraiter de coût minimal pour AStar
     * @param depart le sommet de départ
     * @param arrivee le sommet distance'arrivée
     * @return le sommet à traiter
     */
    private int getSommetSuivant(int depart, int arrivee){
        double dMin = Double.MAX_VALUE; //On initialise la distance minimale à un grand nombre
        int indMin = -1; //On initialise le sommet ayant la distance minimale à -1 
        for(Integer i : aTraiter){ //Pour tout les sommets
            if(!Mark[i]){ //Si celui-ci n'est pas marqué
                if(distance[i] + getHeuristic(i,depart, arrivee) < dMin){ //Si son coût est strictement inférieure à la plus petite pour l'instant trouvée
                    dMin = distance[i] + getHeuristic(i,depart, arrivee); //On met à jour la distance minimale
                    indMin = i; //On met à jour l'indice du sommet ayant la distance minimale
                }
            }
        }
        return indMin;
    }
    
    /**
     * Méthode utilisé dans AStar pour ajouter les voisins possibles distance'un point
     * @param a le point dont on cherche les voisins
     */
    private void ajouterVoisins(int a){
        for(int b = 0; b < Gestionnaire.getGraphe().getNbSommet();b++){
            if(!Mark[b]){
                int poids = Gestionnaire.getGraphe().getMatrice(a, b);
                if(poids != 0){
                    aTraiter.add(b);
                }
            }
        }
    }

    @Override
    public int[] getLastPath() {
        return predecesseur;
    }
}
