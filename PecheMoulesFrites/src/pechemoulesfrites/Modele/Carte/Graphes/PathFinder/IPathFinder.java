package pechemoulesfrites.Modele.Carte.Graphes.PathFinder;

public interface IPathFinder {

    /**
     * Permet de calculer le plus court chemin entre le sommet de départ et distance'arrivée
     * @param depart le sommet de départ
     * @param arrivee le sommet distance'arrivée
     * @return le tableau de précédent
     */
    int[] executer(int depart, int arrivee);
    
    int[] getLastPath();
    
}
