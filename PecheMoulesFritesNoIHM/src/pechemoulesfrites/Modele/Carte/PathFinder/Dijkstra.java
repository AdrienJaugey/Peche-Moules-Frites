package pechemoulesfrites.Modele.Carte.PathFinder;

import pechemoulesfrites.Gestionnaire;

public class Dijkstra implements IPathFinder {
    private static Dijkstra instance;
    
    private final int[] predecesseur;
    private final int[] distance;
    private final boolean[] traité;
    
    private Dijkstra(){
        traité = new boolean[Gestionnaire.getGraphe().getNbSommet()];
        predecesseur = new int[Gestionnaire.getGraphe().getNbSommet()];
        distance = new int[Gestionnaire.getGraphe().getNbSommet()];
    }
    
    public static Dijkstra get(){
        if(instance == null){
            instance = new Dijkstra();
        }
        return instance;
    }
    
    /**
     * Permet de calculer le plus court chemin entre le sommet de départ et distance'arrivée
     * @param depart le sommet de départ
     * @param arrivee le sommet distance'arrivée
     * @return le tableau de précédent
     */
    @Override
    public int[] executer(int depart, int arrivee){
        //initialisation
        initialisation();
        distance[depart] = 0;

        int a = getMinNonMarque();
        while(a != -1){ //Tant qu'il existe un sommet non marqué, on choisit celui de distance min
            traité[a] = true; //Ce sommet est desormais marqué
            for(int b =0;b<Gestionnaire.getGraphe().getNbSommet();b++){ //Pour chaque sommet, on effectue le relâchement de celui-ci avec a
                relachement(a,b);
            }
            a = getMinNonMarque(); //On récupère de nouveau le sommet non marqué de distance min
        }
        
        return predecesseur;
    }
    
    /**
     * Permet distance'initialiser les tableaux traité, distance et predecesseur
     */
    private void initialisation(){
        for(int i = 0; i < Gestionnaire.getGraphe().getNbSommet(); i++){
            traité[i] = false; //On initialise Mark à faux
            distance[i] = Integer.MAX_VALUE; //On initialise distance à une valeur assez grande
            predecesseur[i] = -1; //On initilise predecesseur à -1
        }
    }
    
    /**
     * Récupère le sommet non marqué de distance minimum
     * @return le sommet non marqué minimum de distance
     */
    private int getMinNonMarque(){
        int dMin = Integer.MAX_VALUE; //On initialise la distance minimale à un grand nombre
        int indMin = -1; //On initialise le sommet ayant la distance minimale à -1 (ce qui équivaut
        for(int i = 0;i<Gestionnaire.getGraphe().getNbSommet();i++){ //Pour tout les sommets
            if(!traité[i]){ //Si celui-ci n'est pas marqué
                if(distance[i] < dMin){ //Si sa distance est strictement inférieure à la plus petite pour l'instant trouvée
                    dMin = distance[i]; //On met à jour la distance minimale
                    indMin = i; //On met à jour l'indice du sommet ayant la distance minimale
                }
            }
        }
        return indMin;
    }
    
    /**
     * Permet de procéder au relâchement
     * @param a un sommet
     * @param b un sommet
     */
    private void relachement(int a, int b){
        int distanceAB = Gestionnaire.getGraphe().getMatrice(a, b);
        //Si la distance de b est strictement supérieure à la distance de a plus celle de a à b
        if(((distance[b]) > (distance[a] + distanceAB)) && (distanceAB != 0)){ 
            distance[b] = distance[a] + distanceAB; //On met à jour la distance de b
            predecesseur[b] = a; //On définit a comme prédecesseur de b
        }
    }

    @Override
    public int[] getLastPath() {
        return predecesseur;
    }
}
