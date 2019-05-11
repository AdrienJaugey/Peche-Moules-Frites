package pechemoulesfrites.Modele.Carte.Graphes.PathFinder;

import pechemoulesfrites.Gestionnaire;
import static pechemoulesfrites.Modele.Enum.Enum_Case.Mur;
import static pechemoulesfrites.Modele.Enum.Enum_Case.Sol;
import static pechemoulesfrites.Modele.Enum.Enum_Objet.Frites;

public class DijkstraSaut implements IPathFinder{
    private static DijkstraSaut instance;
    
    private final int[][] matriceSaut;
    private final int[][] predecesseur;
    private final int[] lastPath;
    private final int[][] distance;
    private final boolean[] traité;
    
    private DijkstraSaut(){
        int nbSommet = Gestionnaire.getGraphe().getNbSommet();
        matriceSaut = new int[Gestionnaire.getGraphe().getNbSommet()][Gestionnaire.getGraphe().getNbSommet()];
        creerGrapheSaut();
        predecesseur = new int[2][nbSommet];
        distance = new int[2][nbSommet];
        lastPath = new int[nbSommet];
        traité = new boolean[nbSommet];
    }
    
    /**
     * Permet d'obtenir l'instance de la classe DijkstraSaut
     * @return l'objet DijkstraSaut
     */
    public static DijkstraSaut get(){
        if(instance == null){
            instance = new DijkstraSaut();
        }
        return instance;
    }
    
    /**
     * Permet de créer la matrice du graphe qui autorise le saut
     */
    private void creerGrapheSaut(){
        //Recopie de la matrice de base
        for(int a = 0; a < Gestionnaire.getGraphe().getNbSommet(); a++){
            for(int b = 0; b < Gestionnaire.getGraphe().getNbSommet(); b++){
                matriceSaut[a][b] = Mclass(a,b);
            }
            
        }
        //Ajout des arcs pour les cases desquelles on peut sauter
        for(int a = 0; a<Gestionnaire.getGraphe().getNbSommet();a++){
            for (int b = 0; b < Gestionnaire.getGraphe().getNbSommet(); b++) {
                
                if (a == b + 2 && Gestionnaire.getGraphe().getColonne(a) > 1) {
                    if(bonPattern(a, b)){
                        matriceSaut[a][b] = 1;
                    }
                } else if (a == b - 2 && Gestionnaire.getGraphe().getColonne(a) < Gestionnaire.getGraphe().getLargeur() - 1) {
                    if(bonPattern(a, b)){
                        matriceSaut[a][b] = 1;
                    }
                } else if (a == b + 2 * Gestionnaire.getGraphe().getLargeur() && Gestionnaire.getGraphe().getLigne(a) > 1) {
                    if(bonPattern(a, b)){
                        matriceSaut[a][b] = 1;
                    }
                } else if (a == b - 2 * Gestionnaire.getGraphe().getLargeur() && Gestionnaire.getGraphe().getLigne(a) < Gestionnaire.getGraphe().getHauteur() - 1) {
                    if(bonPattern(a, b)){
                        matriceSaut[a][b] = 1;
                    }
                }
            }
        }
    }
    
    /**
     * Permet de vérifier que les cases associées aux sommets a et b respectent le pattern de saut à savoir sol(a)-mur(centre)-sol(b
     * @param a le sommet de départ pour le saut
     * @param b le sommet d'arrivée pour le saut
     * @return true si un personnage pourrait sauter entre a et b
     */
    private boolean bonPattern(int a, int b){
        boolean aSol = Gestionnaire.getGraphe().getCase(a).getType() == Sol;
        boolean bSol = Gestionnaire.getGraphe().getCase(b).getType() == Sol;
        boolean centreMur = Gestionnaire.getGraphe().getCase((a+b)/2).getType() == Mur;
        return aSol && bSol && centreMur;
    }

    @Override
    public int[] executer(int depart, int arrivee){
        //initialisation
        initialisation();
        d1(depart, 0);
        d2(depart, 0);
        int a = depart;
        while(a != -1){ //Tant qu'il existe un sommet non marqué, on choisit celui de distance min
            traité[a] = true; //Ce sommet est desormais marqué
            for(int b = 0;b<Gestionnaire.getGraphe().getNbSommet();b++){ //Pour chaque sommet, on effectue le relâchement de celui-ci avec a
                relachement(a,b);
            }
            a = getMinNonMarque(); //On récupère de nouveau le sommet non marqué de distance min
        }
        construireLastPath(arrivee);
        return lastPath;
    }
    
    /**
     * Permet distance'initialiser les tableaux traité, distance et predecesseur
     */
    private void initialisation(){
        for(int i = 0; i < Gestionnaire.getGraphe().getNbSommet(); i++){
            traité[i] = false; //On initialise Mark à faux
            d1(i, Integer.MAX_VALUE); //On initialise distance à une valeur assez grande
            d2(i, Integer.MAX_VALUE);
            lastPath[i] = -1;
            p1(i, -1); //On initilise predecesseur à -1
            p2(i, -1);
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
                if(d1(i) < dMin){ //Si sa distance est strictement inférieure à la plus petite pour l'instant trouvée
                    dMin = d1(i); //On met à jour la distance minimale
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
        //Sans sauter
        if(d1(b) > d1(a) + Mclass(a, b) && Mclass(a, b) != 0){
            d1(b, d1(a) + Mclass(a, b));
            p1(b, a);
        }
        //En sautant
        if(d2(b) > d1(a) + Msaut(a, b) && Msaut(a, b) != 0){
            d2(b, d1(a) + Msaut(a, b));
            p2(b, a);
        }
        //Si c'était plus rapide sans sauter
        if(d2(b) > d2(a) + Mclass(a, b) && Mclass(a, b) != 0){
            d2(b, d2(a) + Mclass(a, b));
            p2(b, a);
        }
        
        if(Gestionnaire.getGraphe().getCase(b).getObjet() != null){
            if(Gestionnaire.getGraphe().getCase(b).getObjet().getType() == Frites){
                d1(b, d2(b));
            }
        }
    }
    
    
    private void construireLastPath(int arrivee){
        int sommet = arrivee;
        int compteur = 0;
        while(sommet != -1){
            if(d1(sommet) < d2(sommet)){
                lastPath[sommet] = p1(sommet);
            } else {
                lastPath[sommet] = p2(sommet);
            }
            sommet = lastPath[sommet];
            compteur++;
        }
    }
    
    
    
    private int d1(int sommet){
        return distance[0][sommet];
    }
    
    private void d1(int sommet, int valeur){
        distance[0][sommet] = valeur;
    }
    
    private int d2(int sommet){
        return distance[1][sommet];
    }
    
    private void d2(int sommet, int valeur){
        distance[1][sommet] = valeur;
    }
    
    private int Mclass(int a, int b){
        return Gestionnaire.getGraphe().getMatrice(a, b);
    }
    
    private int Msaut(int a, int b){
        return matriceSaut[a][b];
    }
    
    private int p1(int sommet){
        return predecesseur[0][sommet];
    }
    
    private void p1(int sommet, int valeur){
        predecesseur[0][sommet] = valeur;
    }
    
    private int p2(int sommet){
        return predecesseur[1][sommet];
    }
    
    private void p2(int sommet, int valeur){
        predecesseur[1][sommet] = valeur;
    }

    @Override
    public int[] getLastPath() {
        return lastPath;
    }
}