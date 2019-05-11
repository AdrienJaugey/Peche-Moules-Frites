package pechemoulesfrites.Modele.Carte.Graphes;

import java.util.ArrayList;
import pechemoulesfrites.Gestionnaire;
import pechemoulesfrites.Modele.Carte.Carte;
import pechemoulesfrites.Modele.Carte.Case;
import pechemoulesfrites.Modele.Carte.Graphes.PathFinder.AStar;
import pechemoulesfrites.Modele.Carte.Graphes.PathFinder.Enum_PathFinder;
import static pechemoulesfrites.Modele.Enum.Enum_Case.Mur;
import static pechemoulesfrites.Modele.Enum.Enum_Case.Sol;
import static pechemoulesfrites.Modele.Enum.Enum_Objet.Frites;
import pechemoulesfrites.Modele.Objet.Objet;

public class Graphe {
    private final int matrice[][];
    
    private final int nbEtage;
    private final int largeur;
    private final int hauteur;
    private final int nbSommet;
    
    /**
     * Permet d'instancier le graphe en fonction de la carte passée en paramètre
     * @param laCarte la carte
     */
    public Graphe(Carte laCarte){
        this.hauteur = laCarte.getHauteur();
        this.largeur = laCarte.getLargeur();
        this.nbEtage = 2;
        this.nbSommet = nbEtage*this.largeur*this.hauteur;
        this.matrice = new int[this.nbSommet][this.nbSommet];
        remplir(laCarte);
    }

    /**
     * Permet d'obtenir le nombre d'étage dans le graphe
     * @return le nombre d'étage
     */
    public int getNbEtage() {
        return nbEtage;
    }

    /**
     * Permet d'obtenir la largeur de la grille représentée par le graphe
     * @return la largeur de la grille
     */
    public int getLargeur() {
        return largeur;
    }

    /**
     * Permet d'obtenir la hauteur de la grille représentée par le graphe
     * @return la hauteur de la grille
     */
    public int getHauteur() {
        return hauteur;
    }

    /**
     * Permet d'obtenir le nombre de sommet dans le graphe
     * @return le nombre de sommet
     */
    public int getNbSommet() {
        return nbSommet;
    }
    
    /**
     * Permet d'ajouter un arc entre le sommet 1 et le sommet 2
     * @param sommet1 le premier sommet
     * @param sommet2 le second sommet
     * @param poids le poids de l'arc
     */
    public void ajouterArc(int sommet1, int sommet2, int poids){
        this.matrice[sommet1][sommet2] = poids;
    }
    
    /**
     * Permet d'ajouter une arête entre le sommet 1 et le sommet 2
     * @param sommet1 le premier sommet
     * @param sommet2 le second sommet
     * @param poids le poids de l'arête
     */
    public void ajouterArete(int sommet1, int sommet2, int poids){
        this.ajouterArc(sommet1, sommet2, poids);
        this.ajouterArc(sommet2, sommet1, poids);
    }
    
    /**
     * Permet d'obtenir le sommet correspondant à l'étage, la colonne et la ligne choisis
     * @param etage l'étage choisi
     * @param colonne la colonne choisie
     * @param ligne la ligne choisie
     * @return le sommet correspondant
     */
    public int getSommet(int etage, int colonne, int ligne){
        return etage*(largeur*hauteur) + ligne*largeur + colonne;
    }
    
    /**
     * Permet d'obtenir le sommet correspondant à la case et à l'étage voulu
     * @param frite si le perso a une frite
     * @param laCase la case dont on veut le sommet
     * @return le sommet de la case à l'étage voulu
     */
    public int getSommet(boolean frite, Case laCase){
        int colonne = laCase.getColonne();
        int ligne = laCase.getLigne();
        int etage = frite?1:0;
        return getSommet(etage, colonne, ligne);
    }
    
    /**
     * Permet d'obtenir l'étage correspondant à un sommet
     * @param sommet le sommet dont on veut l'étage
     * @return l'étage du sommet
     */
    public int getEtage(int sommet){
        return sommet/nbSommet;
    }
    
    /**
     * Permet d'obtenir la colonne correspondante à un sommet
     * @param sommet le sommet dont on veut la colonne
     * @return la colonne du sommet
     */
    public int getColonne(int sommet){
        return (sommet%(largeur*hauteur))%largeur;
    }
    
    /**
     * Permet d'obtenir la ligne correspondante à un sommet
     * @param sommet le sommet dont on veut la ligne
     * @return la ligne du sommet
     */
    public int getLigne(int sommet){
        return (sommet%(largeur*hauteur))/largeur;
    }
    
    
    /**
     * Permet d'obtenir le poids de l'arc entre le sommet1 et le sommet2 à l'étage voulu
     * @param sommet1 le premier sommet
     * @param sommet2 le second sommet
     * @return le poids de l'arc entre sommet1 et sommet2 à l'étage précisé
     */
    public int getMatrice(int sommet1, int sommet2){
        return this.matrice[sommet1][sommet2];
    }
    
    private void remplir(Carte laCarte){
        //Remplissage avec mouvements basiques
        for(int ligne = 0;ligne < hauteur;ligne++){
            for(int colonne = 0; colonne < largeur; colonne++){
                //On va tester pour chaque case
                Case laCase = laCarte.getCase(colonne,ligne);
                //Si la case est un mur, on passe à la prochaine
                if(laCase.getType() == Mur) 
                    continue;
                int sommet1CaseEnCours = this.getSommet(0, colonne, ligne);
                int sommet2CaseEnCours = this.getSommet(1, colonne, ligne);
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
                    int sommet1CaseAdjacente = this.getSommet(0, uneCaseValide.getColonne(), uneCaseValide.getLigne());
                    int sommet2CaseAdjacente = this.getSommet(1, uneCaseValide.getColonne(), uneCaseValide.getLigne());
                    this.ajouterArc(sommet1CaseEnCours, sommet1CaseAdjacente, 1);
                    this.ajouterArc(sommet2CaseEnCours, sommet2CaseAdjacente, 1);
                }
            }
        }
        //Remplissage des sauts et du retour à l'étage 0
        int decalage = this.hauteur*this.largeur;
        for(int a = 0; a < decalage; a++){
            if(getCase(a).getType() != Mur) {
                this.ajouterArc(a+decalage, a, 1);
                for (int b = 0; b < decalage; b++){
                    if (a == b + 2 && getColonne(a) > 1) {
                        if(bonPattern(a, b)){
                            matrice[a + decalage][b] = 1;
                        }
                    } else if (a == b - 2 && getColonne(a) < this.largeur - 1) {
                        if(bonPattern(a, b)){
                            matrice[a + decalage][b] = 1;
                        }
                    } else if (a == b + 2 * this.largeur && getLigne(a) > 1) {
                        if(bonPattern(a, b)){
                            matrice[a + decalage][b] = 1;
                        }
                    } else if (a == b - 2 * this.largeur && getLigne(a) < this.hauteur - 1) {
                        if(bonPattern(a, b)){
                            matrice[a + decalage][b] = 1;
                        }
                    }
                }
            }
            
        }
        
        for(Objet frite :Gestionnaire.get().getListeObjet()){
            if(frite.getType() == Frites){
                matrice[getSommet(false,frite.getCase())][getSommet(true,frite.getCase())] = 1;
            }
        }
    }
    
    public void retirerPassageFrite(Case laCase){
        matrice[getSommet(false,laCase)][getSommet(true,laCase)] = 0;
    }
    
    /**
     * Permet de vérifier que les cases associées aux sommets a et b respectent le pattern de saut à savoir sol(a)-mur(centre)-sol(b
     * @param a le sommet de départ pour le saut
     * @param b le sommet d'arrivée pour le saut
     * @return true si un personnage pourrait sauter entre a et b
     */
    private boolean bonPattern(int a, int b){
        boolean aSol = getCase(a).getType() == Sol;
        boolean bSol = getCase(b).getType() == Sol;
        boolean centreMur = getCase((a + b)/2).getType() == Mur;
        return aSol && bSol && centreMur;
    }
    
    public Case getCase(int sommet){
        return Gestionnaire.getCarte().getCase(this.getColonne(sommet), this.getLigne(sommet));
    }
    
    /**
     * Permet d'obtenir le plus court chemin entre depart et arrivee
     * @param depart le sommet de départ
     * @param arrivee le sommet d'arrivée
     * @return le chemin à suivre
     */
    public int[] calculateBestPath(int depart, int arrivee){
        return getSucc(AStar.get().executer(depart, arrivee),arrivee);
    }
    
    public int[] calculateBestPath(boolean frite, Case depart, Case arrivee){
        int dep = getSommet(frite, depart);
        int arr = getSommet(false, arrivee);
        return calculateBestPath(dep, arr);
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
    public int poidsChemin(int[] succ,boolean frite, Case depart, Case arrivee){
        int sommetDepart = this.getSommet(frite, depart);
        int sommetArrivee = this.getSommet(false, arrivee);
        return this.poidsChemin(succ, sommetDepart, sommetArrivee);
    }

    @Override
    public String toString() {
        String res = "Graphe{" + "nbEtage=" + nbEtage + ", largeur=" + largeur + ", hauteur=" + hauteur + '}';
        for(int a = 0; a<nbSommet; a++){
            res += "\n";
            for(int b = 0; b < nbSommet; b++){
                res += matrice[a][b] + "\t";
            }
        }
        return res;
    }
    
}