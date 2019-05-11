package pechemoulesfrites.Modele.Carte.Graphes;

import java.util.ArrayList;
import pechemoulesfrites.Gestionnaire;
import pechemoulesfrites.Modele.Carte.Carte;
import pechemoulesfrites.Modele.Carte.Case;
import pechemoulesfrites.Modele.Carte.Graphes.PathFinder.AStar;
import pechemoulesfrites.Modele.Carte.Graphes.PathFinder.Dijkstra;
import pechemoulesfrites.Modele.Carte.Graphes.PathFinder.DijkstraSaut;
import pechemoulesfrites.Modele.Carte.Graphes.PathFinder.Enum_PathFinder;
import pechemoulesfrites.Modele.Carte.Graphes.PathFinder.IPathFinder;
import static pechemoulesfrites.Modele.Enum.Enum_Case.*;

@Deprecated
public class GrapheV1{
    //La matrice du graphe
    private final int matrice[][];
    //Le nombre de sommet dans le graphe
    private final int nbSommet;
    //La largeur de la grille
    private final int largeur;
    //La hauteur de la grille
    private final int hauteur;

    /**
     * Constructeur du graphe
     * @param largeur la largeur de la grille
     * @param hauteur la hauteur de la grille
     */
    public GrapheV1(int largeur, int hauteur){
        nbSommet = largeur*hauteur;
        this.largeur = largeur;
        this.hauteur = hauteur;
        matrice = new int[nbSommet][nbSommet];
        init(0);//On initialise toutes les cases de la matrice à 0
    }

    /**
     * Constructeur du graphe à partir d'une carte
     * @param laCarte la carte
     */
    public GrapheV1(Carte laCarte){
        this.hauteur = laCarte.getHauteur();
        this.largeur = laCarte.getLargeur();
        nbSommet =  largeur*hauteur;
        matrice = new int[nbSommet][nbSommet];
        init(0);
        remplir(laCarte);
    }

    /**
     * Permet d'initialiser la matrice du graphe
     * @param init la valeur qui sert à remplir la matrice
     */
    private void init(int init){
        for(int i = 0;i<nbSommet;i++){
            for(int j = 0;j<nbSommet;j++){
                matrice[i][j] = init;
            }
        }
    }

    /**
     * Permet de créer le graphe de la carte
     * @param laCarte la carte dont on doit créer le graphe
     */
    private void remplir(Carte laCarte){
        for(int ligne = 0;ligne < hauteur;ligne++){
            for(int colonne = 0; colonne < largeur; colonne++){
                //On va tester pour chaque case
                Case laCase = laCarte.getCase(colonne,ligne);
                //Si la case est un mur, on passe à la prochaine
                if(laCase.getType() == Mur) 
                    continue;
                int sommetCaseEnCours = this.getSommet(ligne, colonne);

                ArrayList<Case> cases = new ArrayList<>();
                if(laCarte.checkLigne(ligne-1)){ //La case du haut si elle existe
                    Case c = laCarte.getCase(colonne, ligne-1);
                    if(c.getType() != Mur) //Si elle n'est pas un mur
                        cases.add(c); //On l'ajoute aux cases à 'relier'
                }
                if(laCarte.checkLigne(ligne+1)){ //La case du bas si elle existe
                    Case c = laCarte.getCase(colonne, ligne+1);
                    if(c.getType() != Mur)//Si elle n'est pas un mur
                        cases.add(c); //On l'ajoute aux cases à 'relier'
                }
                if(laCarte.checkColonne(colonne-1)){ //La case du gauche si elle existe
                    Case c = laCarte.getCase(colonne-1, ligne);
                    if(c.getType() != Mur)//Si elle n'est pas un mur
                        cases.add(c); //On l'ajoute aux cases à 'relier'
                }
                if(laCarte.checkColonne(colonne+1)){ //La case du droite si elle existe
                    Case c = laCarte.getCase(colonne+1, ligne);
                    if(c.getType() != Mur)//Si elle n'est pas un mur
                        cases.add(c); //On l'ajoute aux cases à 'relier'
                }

                //Pour chaque case à 'relier' ou 'valide', on ajoute dans le graphe un arc allant de la case de départ vers celle-ci
                for(Case uneCaseValide : cases){
                    int sommetCaseAdjacente = this.getSommet(uneCaseValide.getLigne(), uneCaseValide.getColonne());
                    this.ajouterArc(sommetCaseEnCours, sommetCaseAdjacente, 1);
                }
            }
        }
    }

    /**
     * Permet d'ajouter une arete entre deux sommets
     * @param s1 le premier sommet
     * @param s2 le second sommet
     * @param poids le poids de l'arete
     */
    public void ajouterArete(int s1, int s2, int poids){
        matrice[s1][s2] = matrice[s2][s1] = poids;
    }

    /**
     * Permet d'ajouter un arc entre deux sommets
     * @param s1 le premier sommet
     * @param s2 le deuxieme sommet
     * @param poids le poids de l'arc
     */
    public void ajouterArc(int s1, int s2, int poids){
        matrice[s1][s2] = poids;
    }

    /**
     * Renvoit la valeur de la matrice d'adjacence pour du point sommet1 vers s2
     * @param sommet1 le premier sommet
     * @param sommet2 le deuxième sommet
     * @return la valeur de la matrice d'adjacence en sommet1,s2
     */
    public int getMatrice(int sommet1, int sommet2){
        return matrice[sommet1][sommet2];
    }
    
    /**
     * Permet d'obtenir le plus court chemin entre depart et arrivee
     * @param algorithme l'algorithme à utiliser
     * @param depart le sommet de départ
     * @param arrivee le sommet d'arrivée
     * @return le chemin à suivre
     */
    public int[] calculateBestPath(Enum_PathFinder algorithme, int depart, int arrivee){
        IPathFinder algo = null;
        switch(algorithme){
            case Dijkstra :{
                algo = Dijkstra.get();
            }
            break;
            case DijkstraSaut : {
                algo = DijkstraSaut.get();
            }
            break;
            case AStar : {
                algo = AStar.get();
            }
            break;
            case AStarSaut : {
                algo = Dijkstra.get();
            }
            break;
            default : {
                algo = Dijkstra.get();
            }
        }
        return getSucc(algo.executer(depart, arrivee),arrivee);
    }
    
    public int[] calculateBestPath(Enum_PathFinder algorithme, Case depart, Case arrivee){
        int dep = getSommet(depart);
        int arr = getSommet(arrivee);
        return calculateBestPath(algorithme, dep, arr);
    }

    /**
     * Permet d'inverser le tableau pred en sortie de Dijkstra
     * @param pred le tableau de précédent
     * @param arrivee le numéro du sommet correspondant à l'arrivée
     * @return le tableau de succésseur
     */
    private int[] getSucc(int[] pred,int arrivee){
        int[] res = new int[nbSommet]; //On crée un tableau d'entier
        for(int i = 0;i<nbSommet;i++) res[i] = -1; //On initialise ce tableau à -1
        int courant = arrivee; //On définit le sommet courant à la valeur du sommet d'arrivée
        while(pred[courant] != -1){ //Tant que le sommet courant à un prédecesseur
            res[pred[courant]] = courant; //Le case d'indice le prédecesseur du point courant prend la valeur du point courant
            courant = pred[courant]; //On met à jour le point courant à son prédecesseur
        }
        return res;
    }

    /**
     * Permet d'obtenir le poids d'un chemin
     * @param Succ le tableau de succésseur
     * @param depart le sommet de départ
     * @param arrivee le sommet d'arrivée
     * @return le poids du chemin
     */
    public int poidsChemin(int[] Succ, int depart, int arrivee){
        int res = 0; //On initialise le poids à 0
        int courant = depart; //On définit le sommet courant au sommet de départ
        while(Succ[courant] != -1){ //Tant que le sommet courant a un succésseur
            res ++; //Le poids s'incrémente du poids de l'arc du sommet courant vers celui qui suit
            courant = Succ[courant]; //On met à jour le sommet courant à son succésseur
        }
        if(courant != arrivee){
            res = Integer.MAX_VALUE;
        }
        return res;
    }

    /**
     * Permet d'obtenir le poids d'un chemin en passant en paramètre une case
     * @param succ le tableau de successeur
     * @param depart la case de départ
     * @param arrivee la case d'arrivée
     * @return le poids du chemin
     */
    public int poidsChemin(int[] succ, Case depart, Case arrivee){
        int sommetDepart = this.getSommet(depart);
        int sommetArrivee = this.getSommet(arrivee);
        return this.poidsChemin(succ, sommetDepart, sommetArrivee);
    }

    @Override
    public String toString(){
        String res = "Graphe ("+nbSommet+" sommets) :\n";
        for(int i = 0; i<nbSommet;i++){
           for(int j = 0; j<nbSommet;j++){
                res += matrice[i][j] + "\t";
            }
            res += "\n";
        }

        return res;
    }

    /**
     * Permet d'obtenir le numéro d'un sommet
     * @param ligne la ligne de la case
     * @param colonne la colonne de la case
     * @return le numéro du sommet dans le graphe
     */
    public int getSommet(int ligne, int colonne){
        return ligne*largeur + colonne;
    }

    /**
     * Permet d'obtenir le numéro d'un sommet
     * @param laCase la case dont on veut le numéro
     * @return le numéro dans le graphe
     */
    public int getSommet(Case laCase){
        return getSommet(laCase.getLigne(), laCase.getColonne());
    }

    public Case getCase(int sommet){
        return Gestionnaire.getCarte().getCase(getColonne(sommet),getLigne(sommet));
    }

    /**
     * Permet d'obtenir la ligne correspondant à un sommet du graphe
     * @param sommet le sommet du graphe
     * @return la ligne de ce sommet
     */
    public int getLigne(int sommet){
        return (sommet/largeur);
    }

    /**
     * Permet d'obtenir la colonne d'un sommet du graphe
     * @param sommet le sommet du graphe
     * @return la colonne de ce sommet
     */
    public int getColonne(int sommet){
        return (sommet%largeur);
    }
    
    public int getNbSommet(){
        return nbSommet;
    }
    
    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }
    
    @Override
    public GrapheV1 clone(){
        GrapheV1 res = new GrapheV1(largeur, hauteur);
        for(int a = 0;a<nbSommet; a++){
            for(int b = 0; b<nbSommet; b++){
                res.ajouterArc(a, b, matrice[a][b]);
            }
        }
        return res;
    }
    
}